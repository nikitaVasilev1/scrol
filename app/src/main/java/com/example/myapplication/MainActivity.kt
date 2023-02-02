package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.CardPostBinding
import com.example.myapplication.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            posts.map { post ->
                CardPostBinding.inflate(layoutInflater, binding.container, true).apply {
                    author.text = post.author
                    text.text = post.content
                    date.text = post.published
                    like.setImageResource(
                        if (post.likedByMe) R.drawable.liked else R.drawable.like
                    )
                    likeText.text = post.numberOfReactrion(post.likes)
                    repostText.text = post.numberOfReactrion(post.reposts)

                    like.setOnClickListener {
                        viewModel.like(post.id)
                    }

                    repost.setOnClickListener {
                        viewModel.repost(post.id)
                    }
                }.root
            }
        }
    }
}
