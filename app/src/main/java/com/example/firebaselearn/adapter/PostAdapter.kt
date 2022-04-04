package com.example.firebaselearn.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebaselearn.R
import com.example.firebaselearn.activity.MainActivity
import com.example.firebaselearn.managers.DatabaseHandler
import com.example.firebaselearn.managers.DatabaseManager
import com.example.firebaselearn.model.Post
import com.example.firebaselearn.utils.Extensions.toast
import java.util.*
import kotlin.collections.ArrayList

class PostAdapter(var activity: MainActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: ArrayList<Post> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: ArrayList<Post>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearAll() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]
        if (holder is PostViewHolder) {
            holder.tv_title.text = post.title!!.uppercase(Locale.getDefault())
            holder.tv_body.text = post.body

            Glide.with(activity).load(post.img)
                .into(holder.iv_photo)

            holder.ll_post.setOnLongClickListener {
                activity.apiDeletePost(post)
                return@setOnLongClickListener false
            }
        }
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_title: TextView = view.findViewById(R.id.tv_title)
        var tv_body: TextView = view.findViewById(R.id.tv_body)
        var ll_post: LinearLayout = view.findViewById(R.id.ll_post)
        var iv_photo: ImageView = view.findViewById(R.id.iv_photo)
    }
}