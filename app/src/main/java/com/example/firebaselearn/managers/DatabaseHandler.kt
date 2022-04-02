package com.example.firebaselearn.managers


import com.example.firebaselearn.model.Post
import java.lang.Exception

interface DatabaseHandler {
    fun onSuccess(post: Post? = null, posts: ArrayList<Post> = ArrayList())
    fun onError()
}