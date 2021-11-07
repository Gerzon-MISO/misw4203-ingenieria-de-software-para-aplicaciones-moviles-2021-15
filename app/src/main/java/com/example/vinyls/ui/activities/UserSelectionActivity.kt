package com.example.vinyls.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.example.vinyls.R

class UserSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = requireNotNull(supportActionBar)
        actionBar.hide()
        setContentView(R.layout.activity_user_selection)
        val getButtonVisitor: AppCompatButton = findViewById(R.id.button3)
        getButtonVisitor.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val infoUser = Bundle()
            infoUser.putBoolean("isVisitor", true)
            intent.putExtras(infoUser)
            startActivity(intent)
        }
        val getButtonUser: AppCompatButton = findViewById(R.id.button4)
        getButtonUser.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val infoUser = Bundle()
            infoUser.putBoolean("isVisitor", false)
            intent.putExtras(infoUser)
            startActivity(intent)
        }
    }

}