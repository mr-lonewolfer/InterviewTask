package com.nimesh.interviewtask.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nimesh.interviewtask.data.model.MediaCoveragesItem
import com.nimesh.interviewtask.databinding.ListItemMediaCoverageBinding
import javax.inject.Inject

/**
 * Created by Nimesh Patel on 4/16/2024.
 * Purpose:
 */

class MediaCoverageAdapter @Inject constructor(private val context: Context) :
    PagingDataAdapter<MediaCoveragesItem, MediaCoverageAdapter.MediaCoverageViewHolder>(
        MediaCoverageDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaCoverageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMediaCoverageBinding.inflate(inflater, parent, false)
        val screenWidth = context.resources.displayMetrics.widthPixels
        val itemWidth = screenWidth / 3
        binding.root.layoutParams.width = itemWidth
        binding.root.layoutParams.height = itemWidth
        return MediaCoverageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaCoverageViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class MediaCoverageViewHolder(private val binding: ListItemMediaCoverageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaCoverage: MediaCoveragesItem) {
            binding.apply {
                val finalUrl = when (mediaCoverage.mediaType) {
                    2 -> mediaCoverage.coverageURL
                    0 -> mediaCoverage.backupDetails?.screenshotURL
                    else -> ""
                }
                Glide.with(binding.ivMedia.context).load(finalUrl).into(binding.ivMedia)
            }
        }
    }

    private class MediaCoverageDiffCallback : DiffUtil.ItemCallback<MediaCoveragesItem>() {
        override fun areItemsTheSame(
            oldItem: MediaCoveragesItem,
            newItem: MediaCoveragesItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MediaCoveragesItem,
            newItem: MediaCoveragesItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}