package com.example.vinyls.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.AlbumArtistListItemsBinding
import com.squareup.picasso.Picasso
import org.json.JSONArray


class AlbumArtistsAdapter(artist_list: JSONArray):RecyclerView.Adapter<AlbumArtistsAdapter.AlbumArtistViewHolder>() {

    private var artistList: JSONArray = artist_list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumArtistViewHolder {
        val withDataBinding: AlbumArtistListItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumArtistViewHolder.LAYOUT,
            parent,
            false)
        return AlbumArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumArtistViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.artistNameTextView.text = artistList.getJSONObject(position).get("name").toString()

            Picasso.get()
                .load(artistList.getJSONObject(position).get("image").toString())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .into(it.artistImageView)
        }


    }

    override fun getItemCount(): Int {
        return artistList.length()
    }

    class AlbumArtistViewHolder(val viewDataBinding: AlbumArtistListItemsBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_artist_list_items
        }

    }
}

