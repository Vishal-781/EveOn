package com.example.eveon.activitiesandfragments

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.example.eveon.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val typeFace: Typeface = Typeface.createFromAsset(assets,"1.ttf")
        val tvsplash: TextView =findViewById(R.id.tv_splash)
        tvsplash.typeface=typeFace
        val typeface1:Typeface= Typeface.createFromAsset(assets,"kwokwi.regular.otf")
        val tv_splash1:TextView=findViewById(R.id.tv_splash_1)
        tv_splash1.typeface=typeface1
        Handler().postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            1500,
        )
    }
}