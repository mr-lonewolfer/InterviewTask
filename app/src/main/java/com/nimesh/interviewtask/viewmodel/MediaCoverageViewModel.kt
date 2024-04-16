package com.nimesh.interviewtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nimesh.interviewtask.data.model.remote.MediaCoveragesItem
import com.nimesh.interviewtask.data.network.MediaCoverageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
@HiltViewModel
class MediaCoverageViewModel @Inject constructor(
    private val repository: MediaCoverageRepository
) : ViewModel() {
    fun getMediaCoverages(): Flow<PagingData<MediaCoveragesItem>> {
        return repository.getMediaCoverages().cachedIn(viewModelScope)
    }
}