package com.an_mikhaylov.animalshandbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.an_mikhaylov.animalshandbook.databinding.ActivityEditBinding
import com.an_mikhaylov.animalshandbook.databinding.ActivityMainBinding

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding
    private var indexImage = 0
    private var imageId = R.drawable.animal1
    private val imageIdList = listOf(
        R.drawable.animal1,
        R.drawable.animal2,
        R.drawable.animal3,
        R.drawable.animal4,
        R.drawable.animal5
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons() = with(binding) {
        buttonNext.setOnClickListener {
            indexImage++
            if (indexImage > imageIdList.size - 1) indexImage = 0
            imageId = imageIdList[indexImage]
            imageView.setImageResource(imageId)
        }
        bDone.setOnClickListener {
            val animal = Animal(imageId, edTitle.text.toString(), edDesc.text.toString())
            val editIntent = Intent().apply {
                putExtra("animal", animal)
            }
            setResult(RESULT_OK, editIntent)
            finish()
        }
    }
}