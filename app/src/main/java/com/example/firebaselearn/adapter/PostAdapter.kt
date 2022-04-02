package com.example.firebaselearn.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaselearn.R
import com.example.firebaselearn.activity.MainActivity
import com.example.firebaselearn.model.Post
import java.util.*
import kotlin.collections.ArrayList

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]
        if (holder is PostViewHolder) {
            holder.tv_title.text = post.title!!.uppercase(Locale.getDefault())
            holder.tv_body.text = post.body
            holder.ll_post.setOnLongClickListener {
                activity.apiDeletePost(post)
                return@setOnLongClickListener false
            }
        }
    }

    inner class PostViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tv_title: TextView = view.findViewById(R.id.tv_title)
        var tv_body: TextView = view.findViewById(R.id.tv_body)
        var ll_post: LinearLayout = view.findViewById(R.id.ll_post)

    }
}