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
import com.example.vinyls.databinding.AlbumTrackListItemsBinding
import com.example.vinyls.databinding.AlbumDetailItemBinding
import com.example.vinyls.databinding.AlbumItemBinding
import com.example.vinyls.models.Album
import com.example.vinyls.ui.fragments.AlbumsFragmentDirections
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation
import org.json.JSONArray


class AlbumTracksAdapter(track_list: JSONArray):RecyclerView.Adapter<AlbumTracksAdapter.AlbumTrackViewHolder>() {

    var track_list:JSONArray

    init {
        this.track_list = track_list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTrackViewHolder {
        val withDataBinding: AlbumTrackListItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumTrackViewHolder.LAYOUT,
            parent,
            false)
        return AlbumTrackViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumTrackViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.itemNumberTextView.text = (position + 1).toString()
            it.NameSongTextView.text = track_list.getJSONObject(position).get("name").toString()
            it.durationTextView.text = track_list.getJSONObject(position).get("duration").toString()
        }


    }

    override fun getItemCount(): Int {
        return track_list.length()
    }


    class AlbumTrackViewHolder(val viewDataBinding: AlbumTrackListItemsBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_track_list_items
        }

    }
}

