package com.nimesh.interviewtask.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nimesh.interviewtask.Utils.loadImageFromUrl
import com.nimesh.interviewtask.data.model.remote.MediaCoveragesItem
import com.nimesh.interviewtask.databinding.ListItemMediaCoverageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
                val imageUrl = mediaCoverage.coverageURL
                    binding.ivMedia.loadImageFromUrl(context, imageUrl)

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