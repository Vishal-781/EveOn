package com.example.eveon.activitiesandfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eveon.R



class MyEvents_Fragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view =inflater.inflate(R.layout.fragment_my_events_, container, false)
        return view
    }


}