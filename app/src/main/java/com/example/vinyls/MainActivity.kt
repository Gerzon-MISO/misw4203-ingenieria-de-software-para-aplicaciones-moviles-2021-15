package com.example.vinyls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response
import com.example.vinyls.broker.VolleyBroker
import com.example.vinyls.viewmodels.AlbumViewModel
import com.example.vinyls.viewmodels.AlbumsViewModel
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var volleyBroker:VolleyBroker
    lateinit var button:Button
    lateinit var button2:Button
    lateinit var viewModel1:AlbumsViewModel
    lateinit var viewModel2:AlbumViewModel
    var  resp:JSONArray?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
        viewModel1 = ViewModelProvider(this,AlbumsViewModel.Factory(this.application)).get(AlbumsViewModel::class.java)
        viewModel2 = ViewModelProvider(this,AlbumViewModel.Factory(this.application,101)).get(AlbumViewModel::class.java)

        button.setOnClickListener{
            viewModel1.albums.observe(this,{it.apply{ println(this)}})
        }
        button2.setOnClickListener{
            viewModel2.album.observe(this,{it.apply { println(this) }})
        }


}
}