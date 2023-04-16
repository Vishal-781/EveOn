package com.example.eveon.activitiesandfragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.eveon.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import models.PDetails
import models.UserModel
import pages.Login


class p_details : AppCompatActivity() {
    var mAuth = FirebaseAuth.getInstance()
    var firebaseDatabase = FirebaseFirestore.getInstance()
    private lateinit var mimageView:ImageView
    private lateinit var nameEditText: EditText
    private var mselectedimageuri: Uri?=null
    private lateinit var submitBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p_details)
        mimageView=findViewById(R.id.imageView)
        submitBtn = findViewById(R.id.btn)
        nameEditText=findViewById(R.id.editTextTextPersonName)

        val uid = mAuth.currentUser?.uid
        mimageView.setOnClickListener {
            chooseimage()
        }
        submitBtn.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = mAuth.currentUser?.email
            val photo = mselectedimageuri.toString()
            val pDetails = email?.let { it1 -> PDetails(name, it1, photo) }
            val user = pDetails?.let { it1 -> UserModel(it1) }
            if (uid != null) {
                if (user != null) {
                    firebaseDatabase.collection("users").document(uid).set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Info uploaded!", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, Login::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, Login::class.java))
                            finish()
                        }
                }
            }
        }
    }

    private fun chooseimage() {

       val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
          startActivityForResult(intent,100)
    }

//URI : Uniform resource identifier
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
          if(resultCode== RESULT_OK && requestCode==100 && data?.data!=null)
          {
            mselectedimageuri=data.data
            Glide.with(this@p_details).load(mselectedimageuri).transform(CircleCrop()).placeholder(R.drawable.user).into(mimageView)
          }
    }
}