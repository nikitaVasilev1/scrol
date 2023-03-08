package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.InputDevice
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.Group
import com.example.myapplication.adapter.PostAdapter
import com.example.myapplication.adapter.onInteractionListener
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    val viewModel: PostViewModel by viewModels()
    val interactionListener = object : onInteractionListener {
        override fun onEdit(post: Post) {
            viewModel.edit(post)
        }

        override fun onLike(post: Post) {
            viewModel.like(post.id)
        }

        override fun onShare(post: Post) {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, post.content)
            intent.type = "text/plain"
            val shareIntent = Intent.createChooser(intent, getString(R.string.post_text))
            startActivity(shareIntent)
        }

        override fun onRemove(post: Post) {
            viewModel.remove(post.id)
        }
    }

    object AndroidUtils {
        fun hideKeyboard(view: View) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PostAdapter(interactionListener)
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.cancel.setOnClickListener { viewModel.cancel() }
        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                with(binding.editText){
                    setText(post.content)
                }
                with(binding.content) {
                    binding.group.visibility = View.VISIBLE
                    requestFocus()
                    setText(post.content)
                    AndroidUtils.hideKeyboard(this)
                }
            } else {
                with(binding.content) {
                    binding.group.visibility = View.INVISIBLE
                    clearFocus()
                    setText("")
                    AndroidUtils.hideKeyboard(this)
                }
            }
        }
        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Content can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
}
