package com.aamernabi.moments.views.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.aamernabi.moments.R
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.full_screen_view.view.*

class FullScreenAdapter : PagerAdapter() {

    private val photos: MutableList<Photo> = mutableListOf()

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = photos.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.full_screen_view, container, false)

        view.progress_bar.visibility = View.VISIBLE

        val selectedPhoto = photos[position]
        val analytics = FirebaseAnalytics.getInstance(view.image_view.context)
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, selectedPhoto.id)
            putInt("photo_width", selectedPhoto.width)
            putInt("photo_height", selectedPhoto.height)
            putString("photo_color", selectedPhoto.color)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        }
        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)


        Glide.with(view)
            .load(selectedPhoto.urls.regular)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    view.progress_bar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    view.progress_bar.visibility = View.GONE
                    return false
                }
            })
            .into(view.image_view)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout?)
    }

    fun addAll(list: List<Photo>) {
        photos.addAll(list)
        notifyDataSetChanged()
    }

}