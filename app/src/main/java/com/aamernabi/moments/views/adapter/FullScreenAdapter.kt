package com.aamernabi.moments.views.adapter

import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.aamernabi.moments.R
import com.aamernabi.moments.datasource.remote.photos.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class FullScreenAdapter(
    private val photos: List<Photo>
) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = photos.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.full_screen_view, container, false)

        val imageView = view.findViewById<ImageView>(R.id.image_view)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

        progressBar.visibility = View.VISIBLE

        Glide.with(view)
            .load(photos[position].urls.regular)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout?)
    }

}