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
import com.example.vinyls.adapter.MusiciansAdapter
import com.example.vinyls.databinding.FragmentArtistsBinding
import com.example.vinyls.models.Band
import com.example.vinyls.models.Musician
import com.example.vinyls.viewmodels.BandsViewModel
import com.example.vinyls.viewmodels.MusiciansViewModel

class ArtistsFragment : Fragment() {
    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!


    private lateinit var bandsRecyclerView: RecyclerView
    private lateinit var musiciansRecyclerView: RecyclerView

    private var bandsViewModelAdapter: BandsAdapter?= null
    private lateinit var bandsViewModel: BandsViewModel
    private var musiciansViewModelAdapter: MusiciansAdapter?= null
    private lateinit var musiciansViewModel: MusiciansViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        val view = binding.root
        bandsViewModelAdapter = BandsAdapter()
        musiciansViewModelAdapter = MusiciansAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bandsRecyclerView = binding.bandsRv
        bandsRecyclerView.layoutManager = GridLayoutManager(context,2)
        bandsRecyclerView.adapter = bandsViewModelAdapter

        musiciansRecyclerView = binding.musiciansRv
        musiciansRecyclerView.layoutManager = LinearLayoutManager(context)
        musiciansRecyclerView.adapter = musiciansViewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = "Artistas"
        bandsViewModel = ViewModelProvider(this, BandsViewModel.Factory(activity.application)).get(BandsViewModel::class.java)
        bandsViewModel.bands.observe(viewLifecycleOwner, Observer<List<Band>> {
            it.apply {
                bandsViewModelAdapter!!.bands = this
            }
        })
        bandsViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
        musiciansViewModel = ViewModelProvider(this, MusiciansViewModel.Factory(activity.application)).get(MusiciansViewModel::class.java)
        musiciansViewModel.musicians.observe(viewLifecycleOwner, Observer<List<Musician>> {
            it.apply {
                musiciansViewModelAdapter!!.musicians = this
            }
        })
        musiciansViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    private fun onNetworkError() {
        if(!bandsViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            bandsViewModel.onNetworkErrorShown()
        }
        if(!musiciansViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            musiciansViewModel.onNetworkErrorShown()
        }
    }
}