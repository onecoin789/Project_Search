package com.example.project_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project_search.data.KakaoImageData


class MainViewModel : ViewModel() {

    private val _itemList = MutableLiveData<Set<KakaoImageData>>(mutableSetOf())
    val itemDataList: LiveData<Set<KakaoImageData>> get() = _itemList

    private val _searchItemList: MutableLiveData<MutableList<KakaoImageData>> = MutableLiveData()
    val searchDataList: LiveData<MutableList<KakaoImageData>> get() = _searchItemList

    fun addData(dataList: MutableList<KakaoImageData>) {
        _searchItemList.value = dataList.toMutableList()
    }

    fun addLiked(imageData: KakaoImageData) {
        _itemList.value = _itemList.value?.toMutableSet()?.apply {
            add(imageData)
        } ?: mutableSetOf()
    }

    fun removeLiked(imageData: KakaoImageData) {
        _itemList.value = _itemList.value?.toMutableSet()?.apply {
            remove(imageData)
        } ?: mutableSetOf()
    }

    fun clearData() {
        _itemList.value = emptySet()
    }


}