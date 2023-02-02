package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.repository.PostRepository
import com.example.myapplication.repository.Repository

class PostViewModel : ViewModel() {
    private val repository: Repository = PostRepository()
    val data = repository.get()
    fun like(id: Long) = repository.like(id)
    fun repost(id: Long) = repository.repost(id)
}
