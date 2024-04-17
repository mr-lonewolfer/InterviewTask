package com.nimesh.interviewtask.paging

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nimesh.interviewtask.data.model.remote.MediaCoveragesItem
import com.nimesh.interviewtask.data.network.MediaCoverageServices

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose: provides logic for loading data asynchronously and handling pagination,
 * and efficiently managing large datasets
 */
class MediaCoveragePagingSource(
    private val context: Context,
    private val apiService: MediaCoverageServices
) :
    PagingSource<Int, MediaCoveragesItem>() {

    override fun getRefreshKey(state: PagingState<Int, MediaCoveragesItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaCoveragesItem> {
        return try {
            val nextPageNumber = params.key ?: 0
            val response = apiService.getMediaCoveragesAsync(params.loadSize, nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}