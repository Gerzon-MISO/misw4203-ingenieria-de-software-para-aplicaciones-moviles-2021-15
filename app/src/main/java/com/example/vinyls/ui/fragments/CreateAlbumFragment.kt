package com.example.vinyls.ui.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.PointF
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinyls.R
import com.example.vinyls.models.Album
import com.example.vinyls.models.Track
import com.example.vinyls.viewmodels.AlbumsViewModel
import com.example.vinyls.viewmodels.TrackViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation
import kotlinx.android.synthetic.main.fragment_create_album.*
import kotlinx.android.synthetic.main.fragment_create_track.*
import org.w3c.dom.Text


class CreateAlbumFragment : Fragment() {
    private lateinit var viewModel: AlbumsViewModel
    private lateinit var viewAlbum: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_create_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewAlbum = view

        createGenreSpinner()
        createLabelSpinner()

        this.setOnClickListener()
    }

    private fun createGenreSpinner()
    {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.genres_array,
            R.layout.spinner_intem
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_intem)
            // Apply the adapter to the spinner
            spinner1.adapter = adapter
        }
    }

    private fun createLabelSpinner()
    {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.labels_array,
            R.layout.spinner_intem
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_intem)
            // Apply the adapter to the spinner
            spinner2.adapter = adapter
        }
    }
    private fun setOnClickListener() {
        albumSaveButton?.setOnClickListener {
            var name = albumNameEditText.text.toString()
            var cover = coverEditText.text.toString()
            var description = descEditText.text.toString()
            var genreTV:TextView = spinner1.selectedView as TextView
            var labelTV:TextView = spinner2.selectedView as TextView
            var genre = genreTV.text.toString()
            var recordLabel = labelTV.text.toString()
            var releaseDate = dateEditText.text.toString()

            if (TextUtils.isEmpty(name)) {
                albumNameEditText.error = "El nombre es requerido!"
            }
            if (TextUtils.isEmpty(cover)) {
                coverEditText.error = "El url de la portada es requerida!"
            }
            if (TextUtils.isEmpty(description)) {
                descEditText.error = "La descripción es requerida!"
            }
            if (TextUtils.isEmpty(genre)) {
                descEditText.error = "El genero es requerido!"
            }
            if (TextUtils.isEmpty(recordLabel)) {
                descEditText.error = "El label es requerido!"
            }
            if (TextUtils.isEmpty(releaseDate)) {
                descEditText.error = "La fecha es requerida!"
            }
            var argsArray:ArrayList<String> = arrayListOf(name,cover,description,genre,recordLabel,releaseDate)
            if (this.formIsValid(argsArray)) {
                var album = Album(
                    name = name,
                    cover = cover,
                    recordLabel = recordLabel,
                    releaseDate = releaseDate,
                    genre = genre,
                    description = description
                )
                sendData(album)
                val action = CreateAlbumFragmentDirections.actionCreateAlbumFragmentToNavigationAlbums(forceRefresh = true)
                viewAlbum.findNavController().navigate(action)
            }
        }

        dateEditText?.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            dateEditText.setText(selectedDate)
        })
        val identifier:String = "datePicker"
        activity?.supportFragmentManager?.let { newFragment.show(it,identifier) }
    }

    private fun formIsValid(array:ArrayList<String>): Boolean {
        for (elem in array)
        {
            if (TextUtils.isEmpty(elem))
            {
                return false
            }
        }
        return true
    }

    private fun sendData(album: Album) {
        viewModel = ViewModelProvider(
            this,
            AlbumsViewModel.Factory(requireActivity().application,forceRefresh = false)).get(AlbumsViewModel::class.java)
        viewModel.pushData(album)
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