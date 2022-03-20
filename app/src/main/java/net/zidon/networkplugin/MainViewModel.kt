package net.zidon.networkplugin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import net.zidon.networkplugin.model.WorldSharedItem
import net.zidon.networkplugin.model.WorldSharedItemTag
import net.zidon.networkplugin.model.WorldSharedItemTagState

class MainViewModel : ViewModel() {
    private val mutex = Mutex()
    var worldSharedDataToDisplayOnScreen by mutableStateOf<List<WorldSharedItem>>(emptyList())
        private set
    var worldSharedDataTopTenCategories by mutableStateOf<List<WorldSharedItemTagState>>(emptyList())
        private set
    var worldSharedDataIsLoading by mutableStateOf(false)
        private set
    var worldSharedDataNoMore by mutableStateOf(false)
        private set

    fun getMoreWorldSharedData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!mutex.tryLock("getMoreWorldSharedData"))
                return@launch

            worldSharedDataIsLoading = true
            val mutableList: MutableList<WorldSharedItem> = ArrayList()
            mutableList.addAll(worldSharedDataToDisplayOnScreen)
            delay(200L)
            for (i in 1..2) {
                mutableList.add(
                    WorldSharedItem(
                        1, i.toString(), i.toString(), listOf(
                            WorldSharedItemTag(1, "Title", 100)
                        ),
                        i % 2 == 0, ""
                    )
                )
            }
            worldSharedDataIsLoading = false
            worldSharedDataToDisplayOnScreen = mutableList

            mutex.unlock("getMoreWorldSharedData")
        }
    }

    fun refreshWorldSharedDataTopTenTags() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!mutex.tryLock("getWorldSharedDataTopTenTags"))
                return@launch

            val mutableList: MutableList<WorldSharedItemTagState> = ArrayList()
            delay(200L)
            for (i in 10L downTo 1L) {
                mutableList.add(
                    WorldSharedItemTagState(
                        WorldSharedItemTag(
                            i,
                            i.toString(),
                            i * 1000
                        ),
                        i % 2 == 0L
                    )
                )
            }
            worldSharedDataTopTenCategories = mutableList

            mutex.unlock("getWorldSharedDataTopTenTags")
        }
    }

}