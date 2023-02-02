package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Post

class PostRepository : Repository {

    private var posts = listOf(
        Post(
            id = 1,
            author = "Нетология. Университет интернет - профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен - http://netolo.gy/fyb</string>",
            published = "21 мая в 18:36"
        ),
        Post(
            id = 2,
            author = "Нетология. Университет интернет - профессий",
            content = "Знаний хватит еа всех: на следующей неделе разбираемся с рзработкой мобильных приложений",
            published = "24 мая в 18:10"
        )
    )

    val data = MutableLiveData(posts)
    override fun get(): LiveData<Post> = data

    override fun like(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe)
        }
        data.value = posts
    }

    override fun repost(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(reposts = it.reposts + 10)
        }
        data.value = posts
    }

}
