package net.zidon.networkplugin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import net.zidon.networkplugin.model.*

class MainViewModel : ViewModel() {

    private val getMoreWorldSharedDataMutex = Mutex()
    private val refreshWorldSharedDataTopTenTagsMutex = Mutex()
    private val changeWorldSharedItemFavoriteStateMutex = Mutex()

    private var currentWorldSharedDataIndex: Long = -1

    var worldSharedDataToDisplayOnScreen by mutableStateOf<List<WorldSharedItem>>(emptyList())
        private set
    var worldSharedDataTopTenCategories by mutableStateOf<Map<Long, WorldSharedItemTagState>>(
        emptyMap()
    )
        private set
    var worldSharedDataIsLoading by mutableStateOf(false)
        private set
    var worldSharedDataNoMore by mutableStateOf(false)
        private set

    fun getMoreWorldSharedData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (worldSharedDataNoMore || !getMoreWorldSharedDataMutex.tryLock())
                return@launch

            worldSharedDataIsLoading = true
            try {
                val mutableList: MutableList<WorldSharedItem> = ArrayList()
                mutableList.addAll(worldSharedDataToDisplayOnScreen)
                // TODO: Test only
                val itemsJson: WorldSharedItemsJson =
                    HttpClient {
                        install(JsonFeature) {
                            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                                ignoreUnknownKeys = true // For further compatibility
                            })
                        }
                    }.post("https://test.freezeyou.net/worldSharedItems.json") {
                        contentType(ContentType.Application.Json)
                        body = WorldSharedItemsGetMoreWithToken("Test", currentWorldSharedDataIndex)
                    }
                mutableList.addAll(itemsJson.data)
                currentWorldSharedDataIndex = mutableList.lastOrNull()?.worldId ?: -1
                if (currentWorldSharedDataIndex == 0L) {
                    worldSharedDataNoMore = true
                }
                worldSharedDataToDisplayOnScreen = mutableList
            } catch (_: Exception) {
                // TODO: No network, service unavailable, etc.
            }
            worldSharedDataIsLoading = false

            getMoreWorldSharedDataMutex.unlock()
        }
    }

    fun refreshWorldSharedDataTopTenTags() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!refreshWorldSharedDataTopTenTagsMutex.tryLock())
                return@launch

            try {
                val categoriesMap: HashMap<Long, WorldSharedItemTagState> = HashMap()
                // TODO: Test only
                val tagsJson: WorldSharedItemTagsJson =
                    HttpClient {
                        install(JsonFeature) {
                            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                                ignoreUnknownKeys = true // For further compatibility
                            })
                        }
                    }.post("https://test.freezeyou.net/topTenTags.json")
                for (tag in tagsJson.data) {
                    categoriesMap[tag.tagId] = WorldSharedItemTagState(
                        tag,
                        worldSharedDataTopTenCategories[tag.tagId]?.checked ?: false
                    )
                }
                worldSharedDataTopTenCategories = categoriesMap
            } catch (_: Exception) {
                // TODO: No network, service unavailable, etc.
            }

            refreshWorldSharedDataTopTenTagsMutex.unlock()
        }
    }

    fun changeWorldSharedItemFavoriteState(worldSharedItem: WorldSharedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!changeWorldSharedItemFavoriteStateMutex.tryLock())
                return@launch
            worldSharedItem.favorite = !worldSharedItem.favorite
            // TODO: Sync remote data
            changeWorldSharedItemFavoriteStateMutex.unlock()
        }
    }

    fun changeWorldSharedDataTopTenTagCheckState(tagState: WorldSharedItemTagState) {
        tagState.checked = !tagState.checked
        // TODO: Filter
    }

}