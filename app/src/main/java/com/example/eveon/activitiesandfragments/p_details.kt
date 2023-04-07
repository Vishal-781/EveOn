package com.example.eveon.activitiesandfragments

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.eveon.R

class p_details : AppCompatActivity() {
    private lateinit var mimageView:ImageView
    private lateinit var mtextView:TextView
    private var mselectedimageuri: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p_details)
        mimageView=findViewById(R.id.imageView)
        mtextView=findViewById(R.id.editTextTextPersonName)
        mimageView.setOnClickListener {
            chooseimage()
        }

    }

    private fun chooseimage() {

      val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,100)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
          if(resultCode== RESULT_OK && requestCode==100 && data?.data!=null)
          {
            mselectedimageuri=data.data
            Glide.with(this@p_details).load(mselectedimageuri).centerCrop().placeholder(R.drawable.user).into(mimageView)


          }
    }
}