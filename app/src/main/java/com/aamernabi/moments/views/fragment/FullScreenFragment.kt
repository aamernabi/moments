package com.aamernabi.moments.views.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aamernabi.moments.R
import com.aamernabi.moments.databinding.FragmentFullScreenBinding
import com.aamernabi.moments.di.Injectable
import com.aamernabi.moments.viewmodels.PhotosViewModel
import com.aamernabi.moments.views.MainActivity
import com.aamernabi.moments.views.adapter.FullScreenAdapter
import javax.inject.Inject

class FullScreenFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotosViewModel

    private var binding: FragmentFullScreenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFullScreenBinding.bind(view)
        this.binding = binding

        viewModel = ViewModelProvider(activity ?: error("activity null"), viewModelFactory)
            .get(PhotosViewModel::class.java)

        val photos = viewModel.photos.value ?: return
        binding?.viewPager?.adapter = FullScreenAdapter().apply { addAll(photos) }
        binding?.viewPager?.currentItem = viewModel.currentIndex

        attachPhotosObserver()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity?)?.hideToolbar()
    }

    private fun attachPhotosObserver() {
        viewModel.photos.observe(viewLifecycleOwner, Observer { photos ->
            (binding?.viewPager?.adapter as FullScreenAdapter?)?.addAll(photos ?: return@Observer)
        })
    }
}
