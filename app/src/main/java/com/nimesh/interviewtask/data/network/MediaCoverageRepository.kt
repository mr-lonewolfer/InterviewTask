package com.nimesh.interviewtask.data.network

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nimesh.interviewtask.data.model.remote.MediaCoveragesItem
import com.nimesh.interviewtask.paging.MediaCoveragePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose: class provides a way to retrieve media coverages asynchronously using
 * pagination, encapsulating the logic for fetching data from a remote service
 */
class MediaCoverageRepository @Inject constructor(
    private val context: Context,
    private val mediaCoverageServices: MediaCoverageServices
) {
    fun getMediaCoverages(): Flow<PagingData<MediaCoveragesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MediaCoveragePagingSource(context, mediaCoverageServices) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}