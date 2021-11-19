package com.example.vinyls.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.vinyls.R
import com.example.vinyls.models.Track
import com.example.vinyls.viewmodels.TrackViewModel
import com.google.android.material.textfield.TextInputEditText

class CreateTrackFragment : Fragment() {

    private lateinit var viewModel: TrackViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_track, container, false)
        val saveButton: AppCompatButton = view.findViewById(R.id.saveButton)
        val trackNameEditText = view.findViewById(R.id.trackNameEditText) as TextInputEditText
        val secondsDurationEditText = view.findViewById(R.id.secondsDurationEditText) as TextInputEditText
        val minutesDurationEditText = view.findViewById(R.id.minutesDurationEditText) as TextInputEditText

        saveButton.setOnClickListener {
            val trackName = trackNameEditText.text.toString()
            val durationMinutes = secondsDurationEditText.text.toString()
            val durationSeconds = minutesDurationEditText.text.toString()
            val duration = "$durationMinutes:$durationSeconds"
            val track = Track(0, trackName, duration)
            this.sendData(track)
            //val fragmentDetailAlbum = AlbumDetailFragment()
            //val transaction = fragmentManager?.beginTransaction()
            //transaction?.replace(R.id.nav_host_fragment, fragmentDetailAlbum)?.commit()
        }
        return view
    }

    private fun sendData(track: Track) {
        val args: CreateTrackFragmentArgs by navArgs()
        viewModel = ViewModelProvider(
            this,
            TrackViewModel.Factory(activity!!.application, args.album.albumId, track)).get(TrackViewModel::class.java)
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