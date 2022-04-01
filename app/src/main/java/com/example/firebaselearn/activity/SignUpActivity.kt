package com.example.firebaselearn.activity

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
import java.lang.Exception

class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.toString()
    private lateinit var et_fullname: EditText
    private lateinit var et_password: EditText
    private lateinit var et_email: EditText
    private lateinit var et_cpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initViews()
    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_cpassword = findViewById(R.id.et_cpassword)

        val b_signup = findViewById<Button>(R.id.b_signup)
        b_signup.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            firebaseSignUp(email, password)
        }

        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener {
            callSignInActivity(context)
        }
    }

    private fun firebaseSignUp(email: String, password: String) {
        showLoading(this)
        AuthManager.signUp(email, password, object : AuthHandler {
            override fun onSuccess() {
                callMainActivity(context)
                toast("Signed up successfully")
                dismissLoading()
            }

            override fun onError(exception: Exception?) {
                toast("Sign uo failed")
                Log.e(TAG, "onError: $exception")
                dismissLoading()
            }
        })
    }
}