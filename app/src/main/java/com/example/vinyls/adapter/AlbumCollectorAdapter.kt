package com.example.vinyls.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.ui.fragments.CollectorAlbumItemFragment
import org.json.JSONArray
import android.os.Bundle
import android.widget.ImageView
import com.android.volley.VolleyError
import com.example.vinyls.models.Album
import com.example.vinyls.network.AlbumNwServiceAdapter
import com.squareup.picasso.Picasso
import org.json.JSONObject


class AlbumCollectorAdapter(private val dataSet: JSONArray?) :
        RecyclerView.Adapter<AlbumCollectorAdapter.ViewHolder>() {

    private lateinit var albumNetwork: AlbumNwServiceAdapter

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleAlbum: TextView = view.findViewById(R.id.titleAlbum)
        val imageView: ImageView = view.findViewById(R.id.albumImageView)
        val artistName: TextView = view.findViewById(R.id.artistName)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.fragment_collector_item, viewGroup, false)
        albumNetwork = AlbumNwServiceAdapter(viewGroup.context)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if(dataSet !== null){
            val current =  dataSet.get(position) as JSONObject
            val fragmentAlbum = CollectorAlbumItemFragment()
            val bundle = Bundle()
            bundle.putInt("AlbumId", current.get("id") as Int)
            fragmentAlbum.arguments = bundle

            val albumGet: (Album)-> Unit = {
                viewHolder.titleAlbum.text = it.name
                viewHolder.artistName.text = it.getArtistsString()
                Picasso.get()
                    .load(it.cover)
                    .placeholder(R.drawable.noimg)
                    .error(R.drawable.noimg)
                    .into(viewHolder.imageView)
            }
            val onError: (VolleyError) -> Unit = {
                Log.d("error","Volley Error")
            }
            albumNetwork.getAlbum(albumGet,onError, current.get("id") as Int)

            viewHolder.titleAlbum.text = ""
            viewHolder.artistName.text = ""
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return dataSet?.length() ?: 0

    }

}
