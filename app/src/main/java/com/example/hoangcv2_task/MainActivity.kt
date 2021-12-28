package com.example.hoangcv2_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hoangcv2_task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerFragment = AccountActivityFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, recyclerFragment)
            .commit()
        binding.mainToolBar.iconBack.setOnClickListener {
            onBackPressed()
            true
        }
    }

}