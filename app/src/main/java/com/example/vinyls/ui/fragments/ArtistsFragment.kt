package com.example.vinyls.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.adapter.BandsAdapter
import com.example.vinyls.databinding.FragmentArtistsBinding
import com.example.vinyls.models.Album
import com.example.vinyls.models.Band
import com.example.vinyls.viewmodels.AlbumsViewModel
import com.example.vinyls.viewmodels.BandsViewModel

class ArtistsFragment : Fragment() {
    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var viewModelAdapter: BandsAdapter?= null
    private lateinit var viewModel: BandsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = BandsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.bandsRv
        recyclerView.layoutManager = GridLayoutManager(view.context,2)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = "Artistas"
        viewModel = ViewModelProvider(this, BandsViewModel.Factory(activity.application)).get(BandsViewModel::class.java)
        viewModel.bands.observe(viewLifecycleOwner, Observer<List<Band>> {
            it.apply {
                viewModelAdapter!!.bands = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}