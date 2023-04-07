package com.example.eveon.activitiesandfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.eveon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    var firebase = FirebaseFirestore.getInstance()
    var mAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        var uid = mAuth.uid

        var email = mAuth.currentUser?.email
        var name = view.findViewById<TextView>(R.id.name)
        var emailView = view.findViewById<TextView>(R.id.email)


        return view
    }



}