package com.example.vinyls.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.MusicianItemBinding
import com.example.vinyls.models.Musician
import com.example.vinyls.ui.fragments.ArtistsFragmentDirections
import com.squareup.picasso.Picasso


class MusiciansAdapter : RecyclerView.Adapter<MusiciansAdapter.MusiciansViewHolder>() {

    var musicians :List<Musician> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusiciansViewHolder {
        val withDataBinding: MusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusiciansViewHolder.LAYOUT,
            parent,
            false)
        return MusiciansViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusiciansViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.musicians = musicians[position]
            it.artistNameTextView.text = it.musicians?.name
            Picasso.get()
                .load(it.musicians?.image)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .into(it.artistImageView)
        }

        holder.viewDataBinding.root.setOnClickListener {
            val action = ArtistsFragmentDirections.actionNavigationArtistsToNavigationMusicianDetail(musicians[position].musicianId, false)
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return musicians.size
    }

    class MusiciansViewHolder(val viewDataBinding: MusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_item
        }
    }
}