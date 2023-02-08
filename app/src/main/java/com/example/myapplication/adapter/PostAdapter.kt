package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Post
import com.example.myapplication.R
import com.example.myapplication.databinding.CardPostBinding

typealias OnLikeListenner = (post: Post) -> Unit

class PostAdapter(
    private val onLikeListenner: OnLikeListenner,
    private val onShareListenner: OnLikeListenner
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListenner,onShareListenner)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListenner: OnLikeListenner,
    private val onShareListenner: OnLikeListenner
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            text.text = post.content
            date.text = post.published
            like.setImageResource(
                if (post.likedByMe) R.drawable.liked else R.drawable.like
            )
            like.setOnClickListener {
                onLikeListenner(post)
            }
            repost.setOnClickListener {
                onShareListenner(post)
            }
            likeText.text = post.numberOfReactrion(post.likes)
            repostText.text = post.numberOfReactrion(post.reposts)
        }
    }

}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}

