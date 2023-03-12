package activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.launch
import androidx.activity.viewModels
import com.example.myapplication.Post
import com.example.myapplication.R
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
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }

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
        val newPostContract =
            registerForActivityResult(NewPostActivity.NewPostContract) { content ->
                content ?: return@registerForActivityResult
                viewModel.changeContent(content)
                viewModel.save()
            }
        val adapter = PostAdapter(interactionListener)
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.addButton.setOnClickListener {
            newPostContract.launch()
        }
    }
}
