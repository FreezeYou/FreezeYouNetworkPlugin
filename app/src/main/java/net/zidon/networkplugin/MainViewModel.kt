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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import net.zidon.networkplugin.model.*

class MainViewModel : ViewModel() {

    private val worldSharedDataLoadedDataMutex = Mutex()
    private val worldSharedDataLoadedData = ArrayList<WorldSharedItem>()
    private val worldSharedDataTopTenTagsCheckedMutex = Mutex()
    private val worldSharedDataTopTenTagsChecked = HashSet<Long>()

    private val worldSharedDataToDisplayOnScreenMutex = Mutex()
    var worldSharedDataToDisplayOnScreen by mutableStateOf<List<WorldSharedItem>>(emptyList())
        private set

    private val worldSharedDataTopTenTagsMutex = Mutex()
    var worldSharedDataTopTenTags by mutableStateOf<Map<Long, WorldSharedItemTagState>>(
        emptyMap()
    )
        private set
    var worldSharedDataIsLoading by mutableStateOf(false)
        private set
    var worldSharedDataNoMore by mutableStateOf(false)
        private set

    fun getMoreWorldSharedData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (worldSharedDataNoMore || !worldSharedDataToDisplayOnScreenMutex.tryLock()
                || !worldSharedDataLoadedDataMutex.tryLock()
            )
                return@launch

            worldSharedDataIsLoading = true
            try {
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
                        body = WorldSharedItemsGetMoreWithToken(
                            "Test",
                            getCurrentWorldSharedDataIndex()
                        )
                    }
                worldSharedDataLoadedData.addAll(itemsJson.data)
                filterWorldSharedDataToDisplayOnScreen()
                if (getCurrentWorldSharedDataIndex() == 0L) {
                    worldSharedDataNoMore = true
                }
            } catch (_: Exception) {
                // TODO: No network, service unavailable, etc.
                delay(1000) // Avoid the flood when the server is down.
            }
            worldSharedDataIsLoading = false

            worldSharedDataToDisplayOnScreenMutex.unlock()
            worldSharedDataLoadedDataMutex.unlock()
        }
    }

    fun refreshWorldSharedDataTopTenTags() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!worldSharedDataTopTenTagsMutex.tryLock()
                || !worldSharedDataTopTenTagsCheckedMutex.tryLock()
            )
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

                worldSharedDataTopTenTagsChecked.clear()
                for (tag in tagsJson.data) {
                    categoriesMap[tag.tagId] = WorldSharedItemTagState(
                        tag,
                        worldSharedDataTopTenTags[tag.tagId]?.checked ?: false
                    )
                    if (categoriesMap[tag.tagId]?.checked == true) {
                        addOrRemoveCheckedTag(tag.tagId, true)
                    }
                }
                worldSharedDataTopTenTags = categoriesMap
            } catch (_: Exception) {
                // TODO: No network, service unavailable, etc.
                delay(1000) // Avoid the flood when the server is down.
            }

            worldSharedDataTopTenTagsMutex.unlock()
            worldSharedDataTopTenTagsCheckedMutex.unlock()
        }
    }

    fun changeWorldSharedItemFavoriteState(worldSharedItem: WorldSharedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!worldSharedDataLoadedDataMutex.tryLock())
                return@launch
            worldSharedItem.favorite = !worldSharedItem.favorite
            // TODO: Sync remote data
            worldSharedDataLoadedDataMutex.unlock()
        }
    }

    fun changeWorldSharedDataTopTenTagCheckState(tagState: WorldSharedItemTagState) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!worldSharedDataTopTenTagsMutex.tryLock()
                || !worldSharedDataTopTenTagsCheckedMutex.tryLock()
                || !worldSharedDataLoadedDataMutex.tryLock()
                || !worldSharedDataToDisplayOnScreenMutex.tryLock()
            )
                return@launch

            tagState.checked = !tagState.checked
            addOrRemoveCheckedTag(tagState.tag.tagId, tagState.checked)

            filterWorldSharedDataToDisplayOnScreen()

            worldSharedDataTopTenTagsMutex.unlock()
            worldSharedDataTopTenTagsCheckedMutex.unlock()
            worldSharedDataLoadedDataMutex.unlock()
            worldSharedDataToDisplayOnScreenMutex.unlock()
        }
    }

    /**
     * Should lock worldSharedDataLoadedDataMutex first.
     */
    private fun getCurrentWorldSharedDataIndex(): Long {
        return worldSharedDataLoadedData.lastOrNull()?.worldId ?: -1
    }

    /**
     * Should lock worldSharedDataTopTenTagsMutex first.
     */
    private fun addOrRemoveCheckedTag(tagId: Long, add: Boolean) {
        if (add) {
            worldSharedDataTopTenTagsChecked.add(tagId)
        } else {
            worldSharedDataTopTenTagsChecked.remove(tagId)
        }
    }

    /**
     * Should lock worldSharedDataLoadedDataMutex, worldSharedDataTopTenCategoriesCheckedMutex,
     * worldSharedDataToDisplayOnScreenMutex first.
     */
    private fun filterWorldSharedDataToDisplayOnScreen() {
        val mutableList: MutableList<WorldSharedItem> = ArrayList()
        if (worldSharedDataTopTenTagsChecked.isEmpty()) {
            mutableList.addAll(worldSharedDataLoadedData)
        } else {
            worldSharedDataLoadedData.forEach { datum ->
                for (tagId in worldSharedDataTopTenTagsChecked) {
                    if (datum.tagIds.contains(tagId)) {
                        mutableList.add(datum)
                        break
                    }
                }
            }
        }
        worldSharedDataToDisplayOnScreen = mutableList
    }

}