package com.example.vinyls.adapter
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.AlbumDetailItemBinding
import com.example.vinyls.databinding.BandDetailItemBinding
import com.example.vinyls.models.Band
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation

class BandAdapter:RecyclerView.Adapter<BandAdapter.BandViewHolder>() {

    var navController: NavController? = null
    var band :Band? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BandViewHolder {
        val withDataBinding: BandDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            BandViewHolder.LAYOUT,
            parent,
            false)
        return BandViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: BandViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.band = band
            //it.descriptionTextView.text = it.band?.description.toString()
            Picasso.get()
                .load(band?.image)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .transform(
                    VignetteFilterTransformation(
                        holder.viewDataBinding.bandImageView.context,
                        PointF(0.5f, 0.5f),
                        floatArrayOf(0.0f, 0.0f, 0.0f),
                        0.0f,
                        0.5f
                    )
                )
                .into(holder.viewDataBinding.bandImageView)
        }
    }


    override fun getItemCount(): Int {
        return 1
    }


    class BandViewHolder(val viewDataBinding: BandDetailItemBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.band_detail_item
        }

    }
}