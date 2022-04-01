package com.example.firebaselearn.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaselearn.R
import com.example.firebaselearn.managers.AuthHandler
import com.example.firebaselearn.managers.AuthManager
import com.example.firebaselearn.utils.Extensions.toast
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class SignInActivity : BaseActivity() {

    val TAG = SignInActivity::class.java.toString()
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        initViews()
    }

    private fun initViews() {
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        val b_signin = findViewById<Button>(R.id.b_signin)
        b_signin.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            firebaseSignIn(email, password)
        }
        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener { callSignUpActivity() }
    }

    private fun firebaseSignIn(email: String, password: String) {
        showLoading(this)
        AuthManager.signIn(email, password, object : AuthHandler {
            override fun onSuccess() {
                callMainActivity(context)
                toast("Signed in successfully")
                dismissLoading()
            }

            override fun onError(exception: Exception?) {
                toast("Sign in failed")
                Log.e(TAG, "onError: $exception")
                dismissLoading()
            }
        })
    }

    private fun callSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

}