package com.example.vinyls.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.AlbumTrackListItemsBinding
import org.json.JSONArray


class AlbumTracksAdapter(track_list: JSONArray):RecyclerView.Adapter<AlbumTracksAdapter.AlbumTrackViewHolder>() {

    private var trackList:JSONArray = track_list

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
            it.NameSongTextView.text = trackList.getJSONObject(position).get("name").toString()
            it.durationTextView.text = trackList.getJSONObject(position).get("duration").toString()
        }


    }

    override fun getItemCount(): Int {
        return trackList.length()
    }

    class AlbumTrackViewHolder(val viewDataBinding: AlbumTrackListItemsBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_track_list_items
        }

    }
}

