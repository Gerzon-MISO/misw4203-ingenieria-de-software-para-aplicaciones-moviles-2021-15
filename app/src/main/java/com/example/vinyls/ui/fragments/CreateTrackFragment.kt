package com.example.vinyls.ui.fragments

import android.graphics.PointF
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinyls.R
import com.example.vinyls.models.Album
import com.example.vinyls.models.Track
import com.example.vinyls.viewmodels.TrackViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation
import kotlinx.android.synthetic.main.fragment_create_track.*


class CreateTrackFragment : Fragment() {

    private lateinit var viewModel: TrackViewModel
    private lateinit var album: Album
    private lateinit var viewAlbum: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: CreateTrackFragmentArgs by navArgs()
        album = args.album
        return inflater.inflate(R.layout.fragment_create_track, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewAlbum = view
        this.setOnClickListener()
        Picasso
            .get()
            .load(album.cover)
            .placeholder(R.drawable.noimg)
            .error(R.drawable.noimg)
            .transform(
                VignetteFilterTransformation(
                    albumImageView.context,
                    PointF(0.5f, 0.5f),
                    floatArrayOf(0.0f, 0.0f, 0.0f),
                    0.0f,
                    0.5f
                )
            )
            .into(albumImageView)
        albumTitleTextView.text = album.name
    }

    private fun setOnClickListener() {
        saveButton.setOnClickListener {
            val trackName = trackNameEditText.text.toString()
            val durationMinutes = minutesDurationEditText.text.toString()
            val durationSeconds = secondsDurationEditText.text.toString()

            if (TextUtils.isEmpty(trackName)) {
                trackNameEditText.error = "El nombre es requerido!"
            }
            if (TextUtils.isEmpty(durationMinutes)) {
                minutesDurationEditText.error = "Los minutos son requeridos!"
            }
            if (TextUtils.isEmpty(durationSeconds)) {
                secondsDurationEditText.error = "Los segundos son requeridos!"
            }

            if (this.formIsValid(trackName, durationMinutes, durationSeconds)) {
                val duration = "$durationMinutes:$durationSeconds"
                val track = Track(0, trackName, duration)
                sendData(track)
                val action = CreateTrackFragmentDirections.actionCreateTrackFragmentToAlbumDetailFragment(album.albumId)
                viewAlbum.findNavController().navigate(action)
            }
        }
    }

    private fun formIsValid(trackName: String, durationMinutes: String, durationSeconds: String): Boolean {
        if (TextUtils.isEmpty(trackName) || TextUtils.isEmpty(durationMinutes) || TextUtils.isEmpty(durationSeconds)){
            return false
        }
        return true
    }

    private fun sendData(track: Track) {
        viewModel = ViewModelProvider(
            this,
            TrackViewModel.Factory(activity!!.application, album.albumId, track)).get(TrackViewModel::class.java)
        viewModel.eventNetworkError.observe(viewLifecycleOwner, {
            isNetworkError -> if (isNetworkError) onNetworkError()
        })
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}