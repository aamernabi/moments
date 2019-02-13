package com.aamernabi.moments.views.adapter

import android.support.v7.util.DiffUtil
import com.aamernabi.moments.datasource.remote.photos.Photo

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}