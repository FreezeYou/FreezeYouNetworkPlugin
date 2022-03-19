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

class MainViewModel : ViewModel() {
    private val mutex = Mutex()
    var worldSharedDataToDisplayOnScreen by mutableStateOf<List<Int>>(emptyList())
        private set
    var worldSharedDataIsLoading by mutableStateOf(false)
        private set

    fun getMoreWorldSharedData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!mutex.tryLock("getMoreWorldSharedData"))
                return@launch

            worldSharedDataIsLoading = true
            val mutableList: MutableList<Int> = ArrayList()
            mutableList.addAll(worldSharedDataToDisplayOnScreen)
            delay(200L)
            for (i in 1..2) {
                mutableList.add(i)
            }
            worldSharedDataIsLoading = false
            worldSharedDataToDisplayOnScreen = mutableList

            mutex.unlock("getMoreWorldSharedData")
        }
    }

}