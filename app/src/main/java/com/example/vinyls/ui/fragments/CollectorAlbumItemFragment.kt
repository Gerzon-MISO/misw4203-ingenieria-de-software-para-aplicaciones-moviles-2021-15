package com.example.vinyls.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.vinyls.adapter.AlbumAdapter
import com.example.vinyls.databinding.FragmentCollectorsBinding
import com.example.vinyls.viewmodels.AlbumViewModel
import kotlin.properties.Delegates


class CollectorAlbumItemFragment : Fragment() {

    private var _binding: FragmentCollectorsBinding? = null
    private val binding get() = _binding!!
    private var viewModelAdapter: AlbumAdapter?= null
    private lateinit var viewModel: AlbumViewModel
    private var albumId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCollectorsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumAdapter()
        albumId = arguments?.getString("AlbumId")?.toInt() ?: 0
        Log.d("test","hola")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application,100,true)).get(
            AlbumViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.album = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        Log.d("savedInstance", savedInstanceState.toString())
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}