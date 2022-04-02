package com.example.firebaselearn.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import com.example.firebaselearn.R
import com.example.firebaselearn.managers.AuthManager

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initViews()
    }

    private fun initViews() {
        countDownTimer()
    }

    private fun countDownTimer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if (AuthManager.isSignedIn()) {
                    callMainActivity(context)
                } else {
                    callSignInActivity(context)
                }
            }
        }.start()
    }
}