package com.example.vinyls.adapter

import android.graphics.PointF
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.AlbumArtistListItemsBinding
import com.example.vinyls.databinding.AlbumDetailItemBinding
import com.example.vinyls.databinding.AlbumItemBinding
import com.example.vinyls.models.Album
import com.example.vinyls.ui.fragments.AlbumsFragmentDirections
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation
import org.json.JSONArray


class AlbumArtistsAdapter(artist_list: JSONArray):RecyclerView.Adapter<AlbumArtistsAdapter.AlbumArtistViewHolder>() {

    var artist_list:JSONArray

    init {
        this.artist_list = artist_list
    }

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
            it.artistNameTextView.text = artist_list.getJSONObject(position).get("name").toString()

            Picasso.get()
                .load(artist_list.getJSONObject(position).get("image").toString())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .into(it.artistImageView)
        }


    }

    override fun getItemCount(): Int {
        return artist_list.length()
    }


    class AlbumArtistViewHolder(val viewDataBinding: AlbumArtistListItemsBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_artist_list_items
        }

    }
}

