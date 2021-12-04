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
import com.example.vinyls.databinding.MusicianDetailItemBinding
import com.example.vinyls.models.Musician
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation

class MusicianAdapter:RecyclerView.Adapter<MusicianAdapter.MusicianViewHolder>() {

    var navController: NavController? = null
    var musician :Musician? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
        val withDataBinding: MusicianDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianViewHolder.LAYOUT,
            parent,
            false)
        return MusicianViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicianViewHolder, position: Int) {

        holder.viewDataBinding.also {
            it.musician = musician
            //it.descriptionTextView.text = it.band?.description.toString()
            Picasso.get()
                .load(musician?.image)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .transform(
                    VignetteFilterTransformation(
                        holder.viewDataBinding.musicianImageView.context,
                        PointF(0.5f, 0.5f),
                        floatArrayOf(0.0f, 0.0f, 0.0f),
                        0.0f,
                        0.5f
                    )
                )
                .into(holder.viewDataBinding.musicianImageView)
        }
    }


    override fun getItemCount(): Int {
        return 1
    }


    class MusicianViewHolder(val viewDataBinding: MusicianDetailItemBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_detail_item
        }

    }
}