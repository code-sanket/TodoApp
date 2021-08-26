package code.sanky.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import code.sanky.todoapp.databinding.ActivityFullTaskBinding

class FullTask : AppCompatActivity() {
    lateinit var binding: ActivityFullTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = intent.getStringExtra("TITLE")
        binding.fullTitle.text = textView

        val descView = intent.getStringExtra("DESC")
        binding.fullDescription.text = descView
    }
}