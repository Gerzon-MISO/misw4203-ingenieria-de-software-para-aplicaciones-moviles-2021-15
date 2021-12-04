package com.example.vinyls.ui.fragments

import AlbumCollectorAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.vinyls.R
import com.example.vinyls.models.Collector
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_collector_detail.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class CollectorDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var collector: Collector? = null
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        collector = arguments?.getParcelable<Collector>("Collector")!!
        return inflater.inflate(R.layout.fragment_collector_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textView = view.findViewById(R.id.collectorTitleTextView)
        textView.text = collector?.name ?: "Undefined"
        Picasso.get()
                .load(collector?.email)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .into(imageView)
        albumsRv.layoutManager = GridLayoutManager(view.context,1)
        albumsRv.adapter = AlbumCollectorAdapter(collector?.collectorAlbums)
    }
}