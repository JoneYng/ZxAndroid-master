package com.hzsoft.module.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.hzsoft.lib.base.mvvm.viewmodel.BaseRefreshViewModel
import com.zx.lib.domain.entity.Demo
import com.zx.lib.net.DataRepository
import com.zx.lib.net.DataRepositorySource
import com.zx.lib.net.dto.Resource
import com.zx.lib.net.local.entity.UserTestRoom
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Describe:
 * <p></p>
 *
 * @author zhou
 * @Date 2020/12/1
 */
class MainHomeViewModel(state: SavedStateHandle) : BaseRefreshViewModel<Demo>() {

    val savedStateHandle = state

    private val dataRepositoryRepository: DataRepositorySource = DataRepository()

    private val recipesLiveDataPrivate = MutableLiveData<Resource<List<Demo>>>()
    val recipesLiveData: LiveData<Resource<List<Demo>>> get() = recipesLiveDataPrivate

    private val userTestRoomLiveDataPrivate = MutableLiveData<Resource<List<UserTestRoom>>>()
    val userTestRoomLiveData: LiveData<Resource<List<UserTestRoom>>> get() = userTestRoomLiveDataPrivate

    private fun getRecipes() {
        viewModelScope.launch {
            dataRepositoryRepository.requestRecipes().collect {
                recipesLiveDataPrivate.value = it
                postStopRefreshEvent()
            }
        }
    }

    override fun refreshData() {
        getRecipes()
    }

    override fun loadMore() {

    }

}
