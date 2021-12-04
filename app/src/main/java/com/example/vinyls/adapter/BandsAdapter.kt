package com.example.vinyls.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.BandItemBinding
import com.example.vinyls.models.Band
import com.example.vinyls.ui.fragments.ArtistsFragmentDirections
import com.squareup.picasso.Picasso


class BandsAdapter : RecyclerView.Adapter<BandsAdapter.BandsViewHolder>() {

    var bands :List<Band> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandsViewHolder {
        val withDataBinding: BandItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            BandsViewHolder.LAYOUT,
            parent,
            false)
        return BandsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: BandsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.bands = bands[position]
            it.artistNameTextView.text = it.bands?.name
            Picasso.get()
                .load(it.bands?.image)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .into(it.artistImageView)
        }

        holder.viewDataBinding.root.setOnClickListener {
            val action = ArtistsFragmentDirections.actionNavigationArtistsToNavigationBandDetail(bands[position].bandId, false)
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return bands.size
    }

    class BandsViewHolder(val viewDataBinding: BandItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.band_item
        }
    }
}