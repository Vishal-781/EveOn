package com.example.eveon.activitiesandfragments

import Adapters.RunningEventsAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eveon.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import models.Event

class HomeFragment : Fragment() {
private lateinit var floatingactionbtn:FloatingActionButton
    private lateinit var recyclerviewrunningevent: RecyclerView
    private var chatAdapter:RunningEventsAdapter?=null
    private var meventlist: List<Event>?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
//        recyclerviewrunningevent.setHasFixedSize(true)
//        recyclerviewrunningevent=view.findViewById(R.id.recycler_view_running)
//        var linearlayoutmanager= LinearLayoutManager(context)
//        linearlayoutmanager.stackFromEnd=true
////        recyclerviewrunningevent
         floatingactionbtn=view.findViewById(R.id.floating_action_home)
         floatingactionbtn.setOnClickListener {
            val intent=Intent(context,AddingEvent::class.java)
             startActivity(intent)
        }







        return view
    }



}