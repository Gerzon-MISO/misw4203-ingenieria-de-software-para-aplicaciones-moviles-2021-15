package com.example.vinyls.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.BandItemBinding
import com.example.vinyls.models.Band
import com.example.vinyls.ui.fragments.ArtistsFragment
import com.squareup.picasso.Picasso


class MusiciansAdapter : RecyclerView.Adapter<MusiciansAdapter.BandsViewHolder>() {

    var navController: NavController? = null
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

    override fun onBindViewHolder(holder: MusiciansAdapter.BandsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.bands = bands[position]
            Picasso.get()
                .load(it.bands?.image)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .into(it.artistImageView)
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