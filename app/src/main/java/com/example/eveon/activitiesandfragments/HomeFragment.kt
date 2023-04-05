package com.example.eveon.activitiesandfragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eveon.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
class HomeFragment : Fragment() {
private lateinit var floatingactionbtn:FloatingActionButton
//hello1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {



        val view= inflater.inflate(R.layout.fragment_home, container, false)

        floatingactionbtn=view.findViewById(R.id.floating_action_home)
        floatingactionbtn.setOnClickListener {
         val intent=Intent(context,AddingEvent::class.java)
            startActivity(intent)
        }







        return view
    }



}