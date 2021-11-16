package com.example.vinyls.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.adapter.AlbumAdapter
import com.example.vinyls.adapter.AlbumArtistsAdapter
import com.example.vinyls.adapter.AlbumTracksAdapter
import com.example.vinyls.databinding.FragmentAlbumDetailBinding
import com.example.vinyls.models.Album
import com.example.vinyls.viewmodels.AlbumViewModel
import com.example.vinyls.R


class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var artistsRecyclerView: RecyclerView
    private lateinit var trackRecyclerView: RecyclerView

    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumAdapter()

        val createTrackButton : AppCompatButton = view.findViewById(R.id.agregar_cancion_but)
        createTrackButton.setOnClickListener {
            val fragmentCreateTrack = CreateTrackFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragmentCreateTrack)?.commit()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumRecyclerView

        artistsRecyclerView = binding.albumArtistsRv
        artistsRecyclerView.layoutManager = GridLayoutManager(context,4)

        trackRecyclerView = binding.albumTracksRv
        trackRecyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args: AlbumDetailFragmentArgs by navArgs()
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        println(activity.actionBar?.title)

        viewModel = ViewModelProvider(this,AlbumViewModel.Factory(activity.application,args.albumId)).get(AlbumViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner,Observer<Album>
        {
            viewModelAdapter?.album = it
            artistsRecyclerView.adapter = AlbumArtistsAdapter(it.performers)
            trackRecyclerView.adapter = AlbumTracksAdapter(it.tracks)

        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}