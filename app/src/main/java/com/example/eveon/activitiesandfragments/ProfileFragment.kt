package com.example.eveon.activitiesandfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.eveon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import models.PDetails
import models.UserModel

class ProfileFragment : Fragment() {
    private var db = Firebase.firestore
    var mAuth = FirebaseAuth.getInstance()
    private lateinit var nameView: TextView
    private lateinit var emailView: TextView
    private lateinit var photo: ImageView
    lateinit var photoUri: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        nameView = view.findViewById(R.id.name)
        emailView = view.findViewById(R.id.email)
        photo = view.findViewById(R.id.photo)
        nameView.visibility = View.INVISIBLE
        emailView.visibility = View.INVISIBLE
        photo.visibility = View.INVISIBLE
        nameView.setTextColor(resources.getColor(R.color.onPrimaryDark))
        emailView.setTextColor(resources.getColor(R.color.onPrimaryDark))
        val progBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val uid = mAuth.uid
        uid?.let {
            db.collection("users").document(it).get().addOnSuccessListener() { ds ->
                nameView.text = ds.toObject<UserModel>()?.pDetails?.name
                emailView.text = ds.toObject<UserModel>()?.pDetails?.email
                photoUri = ds.toObject<UserModel>()?.pDetails?.photo.toString()
                progBar.visibility = View.INVISIBLE
                nameView.visibility = View.VISIBLE
                emailView.visibility = View.VISIBLE
                activity?.let { it1 ->
                    Glide.with(it1).load(photoUri).transform(CircleCrop()).placeholder(R.drawable.user)
                        .into(photo)
                    photo.visibility = View.VISIBLE
                }
            }
        }
        return view
    }
}
