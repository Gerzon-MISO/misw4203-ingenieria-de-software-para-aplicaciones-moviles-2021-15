package com.example.vinyls.adapter

import android.graphics.PointF
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.AlbumDetailItemBinding
import com.example.vinyls.models.Album
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation


class AlbumAdapter:RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    var album :Album? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.album = album

        Picasso.get()
            .load(album?.cover)
            .placeholder(R.drawable.noimg)
            .error(R.drawable.noimg)
            .transform(
                VignetteFilterTransformation(
                    holder.viewDataBinding.albumImageView.context,
                    PointF(0.5f, 0.5f),
                    floatArrayOf(0.0f, 0.0f, 0.0f),
                    0.0f,
                    0.5f
                )
            )
            .into(holder.viewDataBinding.albumImageView)

    }

    override fun getItemCount(): Int {
        return 1
    }


    class AlbumViewHolder(val viewDataBinding: AlbumDetailItemBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_detail_item
        }

    }
}