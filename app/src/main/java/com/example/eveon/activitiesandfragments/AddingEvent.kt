package com.example.eveon.activitiesandfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eveon.R

class AddingEvent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_event)
        setupActionBar()
    }
    fun setupActionBar() {
        val toolbar123 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_full_image)
        setSupportActionBar(toolbar123)
        val actionbar = supportActionBar
        actionbar?.title="Add Events"
        toolbar123.setTitleTextColor(resources.getColor(R.color.black))
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }
        toolbar123.setNavigationOnClickListener {
            onBackPressed()

        }
    }
}