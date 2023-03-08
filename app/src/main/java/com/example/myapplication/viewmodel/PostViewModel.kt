package com.example.myapplication.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Post
import com.example.myapplication.repository.PostRepository
import com.example.myapplication.repository.Repository

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likes = 0,
    reposts = 0,
    likedByMe = false
)

class PostViewModel : ViewModel() {
    private val repository: Repository = PostRepository()
    val data = repository.get()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }
    }

    fun like(id: Long) = repository.like(id)
    fun repost(id: Long) = repository.repost(id)
    fun remove(id: Long) = repository.remove(id)
    fun edit(post: Post) {
        edited.value = post
    }
    fun cancel(){
        edited.value = empty
    }
}
