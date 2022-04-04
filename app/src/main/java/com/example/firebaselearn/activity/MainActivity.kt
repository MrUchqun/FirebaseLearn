package com.example.firebaselearn.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaselearn.R
import com.example.firebaselearn.adapter.PostAdapter
import com.example.firebaselearn.managers.AuthManager
import com.example.firebaselearn.managers.DatabaseHandler
import com.example.firebaselearn.managers.DatabaseManager
import com.example.firebaselearn.model.Post
import com.example.firebaselearn.utils.Extensions.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PostAdapter(this)
        postAdapter = recyclerView.adapter as PostAdapter

        apiLoadPosts()

        val ivSignOut: ImageView = findViewById(R.id.iv_sign_out)
        ivSignOut.setOnClickListener {
            firebaseSignOut()
        }

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_create)
        fabAdd.setOnClickListener {
            callCreateActivity()
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                // Load all posts...
                apiLoadPosts()
            }
        }

    private fun apiLoadPosts() {
        showLoading(this)
        postAdapter.clearAll()
        DatabaseManager.apiLoadPosts(object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                refreshAdapter(posts)
                dismissLoading()
            }

            override fun onError() {
                dismissLoading()
            }
        })
    }

    fun apiDeletePost(post: Post) {
        DatabaseManager.apiDeletePost(post, object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                apiLoadPosts()
            }

            override fun onError() {
                toast("Deleting item failed")
                dismissLoading()
            }
        })
    }

    private fun callCreateActivity() {
        val intent = Intent(context, CreateActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun firebaseSignOut() {
        AuthManager.signOut()
        callSignInActivity(context)
    }

    fun refreshAdapter(posts: ArrayList<Post>) {
        postAdapter.addItems(posts)
    }
}