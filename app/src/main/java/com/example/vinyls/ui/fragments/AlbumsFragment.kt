package com.example.vinyls.ui.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.vinyls.R
import com.example.vinyls.adapter.AlbumsAdapter
import com.example.vinyls.databinding.FragmentAlbumsBinding
import com.example.vinyls.viewmodels.AlbumsViewModel
import kotlinx.android.synthetic.main.fragment_albums.view.*
import java.lang.Exception


class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var viewModelAdapter: AlbumsAdapter?= null
    private lateinit var viewModel: AlbumsViewModel
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumsAdapter()
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_albums)
        mSwipeRefreshLayout?.setOnRefreshListener {
            viewModel.forceRefreshDataFromNetwork()
            val handler = Handler()
            handler.postDelayed({
                if (mSwipeRefreshLayout!!.isRefreshing) {
                    mSwipeRefreshLayout?.isRefreshing = false
                }
            }, 1000)
        }
        view.agregarAlbumButt.setOnClickListener {
            val navCreateAlbum = AlbumsFragmentDirections.actionNavigationAlbumsToCreateAlbumFragment()
            view.findNavController().navigate(navCreateAlbum)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumsRv
        recyclerView.layoutManager = GridLayoutManager(view.context,2)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        var forceRefresh = false
        try {
            val args: AlbumsFragmentArgs by navArgs()

            if (args.forceRefresh)
            {
                forceRefresh = true
            }
        }
        catch (e:Exception)
        {
            forceRefresh = false
        }
        activity.actionBar?.title = getString(R.string.title_albums)
        viewModel = ViewModelProvider(this, AlbumsViewModel.Factory(activity.application,forceRefresh)).get(AlbumsViewModel::class.java)
        viewModel.albums.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.albums = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
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