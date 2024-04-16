package com.nimesh.interviewtask.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nimesh.interviewtask.data.model.remote.MediaCoveragesItem
import com.nimesh.interviewtask.data.network.MediaCoverageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose: Manages the data related to media coverages, including permission status and the actual coverages data.
 */
@HiltViewModel
class MediaCoverageViewModel @Inject constructor(
    private val repository: MediaCoverageRepository
) : ViewModel() {
    private var _permissionGranted = MutableStateFlow(false)
    val permissionGranted: StateFlow<Boolean> = _permissionGranted

    val _mediaCoverages = MutableStateFlow<PagingData<MediaCoveragesItem>?>(null)
    val mediaCoverages: StateFlow<PagingData<MediaCoveragesItem>?> = _mediaCoverages


    fun getMediaCoverages(){
        viewModelScope.launch {
            repository.getMediaCoverages().cachedIn(viewModelScope).collectLatest {
                _mediaCoverages.emit(it)
            }
        }
    }

    fun updatePermissionGrant(isPermissionGranted: Boolean) {
        viewModelScope.launch {
            _permissionGranted.emit(isPermissionGranted)
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}