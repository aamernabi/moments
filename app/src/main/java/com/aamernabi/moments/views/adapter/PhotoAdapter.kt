package com.aamernabi.moments.views.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.PhotoItemBinding
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.utils.OnItemClickListener

class PhotoAdapter(
    private val onItemClickListener: OnItemClickListener,
    private val photos: List<Photo> = ArrayList()
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): ViewHolder {
        val bindings = DataBindingUtil.inflate<PhotoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.photo_item,
            parent,
            false
        )

        return ViewHolder(bindings)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position], View.OnClickListener {
            onItemClickListener.onItemClick(position)
        })
    }

    class ViewHolder(private val bindings: PhotoItemBinding) : RecyclerView.ViewHolder(bindings.root) {
        fun bind(photo: Photo, listener: View.OnClickListener) {
            bindings.photo = photo
            bindings.listener = listener
        }
    }
}