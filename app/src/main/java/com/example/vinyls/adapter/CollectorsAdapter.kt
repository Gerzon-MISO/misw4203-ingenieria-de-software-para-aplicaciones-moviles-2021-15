package com.example.vinyls.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.CollectorItemBinding
import com.example.vinyls.models.Collector
import com.example.vinyls.ui.fragments.CollectorsFragmentDirections
import com.squareup.picasso.Picasso


class CollectorsAdapter : RecyclerView.Adapter<CollectorsAdapter.CollectorsViewHolder>(){

    var collectors :List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorsViewHolder {
        val withDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorsViewHolder.LAYOUT,
            parent,
            false)
        return CollectorsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collectors = collectors[position]
            Picasso.get()
                .load(it.collectors?.email)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .into(it.imageView)
        }

        holder.viewDataBinding.root.setOnClickListener {
            CollectorsFragmentDirections.actionCollectorsFragmentToCollectorDetailFragment()
        }
    }

    override fun getItemCount(): Int {
        return collectors.size
    }

    class CollectorsViewHolder(val viewDataBinding: CollectorItemBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }
}