package com.nimesh.interviewtask.data.network

import com.nimesh.interviewtask.Utils.GlobalVariables
import com.nimesh.interviewtask.data.model.MediaCoverages
import com.nimesh.interviewtask.data.model.MediaCoveragesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.ZoneOffset

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */
interface MediaCoverageServices {
    @GET(GlobalVariables.API_MEDIA_COVERAGES)
    suspend fun getMediaCoveragesAsync(
        @Query(GlobalVariables.KEY_LIMIT) limit: Int,
        @Query(GlobalVariables.KEY_OFFSET) offset: Int,
    ): List<MediaCoveragesItem>
}