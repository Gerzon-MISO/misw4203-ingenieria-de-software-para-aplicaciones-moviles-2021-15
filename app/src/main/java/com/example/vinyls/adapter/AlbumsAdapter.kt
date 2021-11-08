package com.example.vinyls.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinyls.R
import com.example.vinyls.databinding.AlbumItemBinding
import com.example.vinyls.models.Album
import com.example.vinyls.ui.fragments.AlbumsFragmentDirections
import com.squareup.picasso.Picasso


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>(){

    var navController: NavController? = null
    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumsViewHolder.LAYOUT,
            parent,
            false)
        return AlbumsViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.albums = albums[position]
            Picasso.get()
                .load(it.albums?.cover)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.noimg)
                .into(it.imageView)
        }

        holder.viewDataBinding.root.setOnClickListener {
            val action = AlbumsFragmentDirections.actionAlbumsFragmentToAlbumDetailFragment(albums[position].albumId)
            println(action)
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumsViewHolder(val viewDataBinding: AlbumItemBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
}