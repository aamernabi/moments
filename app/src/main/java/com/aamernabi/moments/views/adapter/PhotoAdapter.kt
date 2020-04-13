/*
 * Copyright 2020 aamernabi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aamernabi.moments.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.PhotoItemBinding
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.aamernabi.moments.utils.OnItemClickListener

class PhotoAdapter(
    private val onItemClickListener: OnItemClickListener
) : PagedListAdapter<Photo, PhotoAdapter.ViewHolder>(PhotoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): ViewHolder {
        val bindings = DataBindingUtil.inflate<PhotoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.photo_item,
            parent,
            false
        )

        return ViewHolder(bindings)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = getItem(position) ?: return
        holder.bind(photo, View.OnClickListener {
            onItemClickListener.onItemClick(position)
        })
    }

    class ViewHolder(
        private val bindings: PhotoItemBinding
    ) : RecyclerView.ViewHolder(bindings.root) {
        fun bind(item: Photo, clickListener: View.OnClickListener) {
            bindings.apply {
                photo = item
                listener = clickListener
                executePendingBindings()
            }
        }
    }
}
