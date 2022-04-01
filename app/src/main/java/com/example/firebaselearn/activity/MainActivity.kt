package com.example.firebaselearn.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaselearn.R
import com.example.firebaselearn.managers.AuthManager

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        val btnSignOut: Button = findViewById(R.id.btn_sign_out)
        btnSignOut.setOnClickListener {
            firebaseSignOut()
        }
    }

    private fun firebaseSignOut() {
        AuthManager.signOut()
        callSignInActivity(context)
    }
}