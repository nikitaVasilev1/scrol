package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет - профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен - http://netolo.gy/fyb</string>",
            published = "21 мая в 18:36"
        )
        with(binding) {
            author.text = post.author
            text.text = post.content
            date.text = post.published
            if (post.likedByMe) {
                like.setImageResource(R.drawable.liked)
            }

            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.liked else R.drawable.like
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likeText.text = post.numberOfReactrion(post.likes)
            }

            repost.setOnClickListener {
                post.reposts += 10
                repostText.text = post.numberOfReactrion(post.reposts)

            }
        }

    }
}
