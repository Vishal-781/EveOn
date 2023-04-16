package com.example.eveon.activitiesandfragments

import Adapters.RunningEventsAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eveon.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import models.Event
import java.util.ArrayList


class HomeFragment : Fragment() {
private lateinit var floatingactionbtn:FloatingActionButton
    private lateinit var recyclerviewrunningevent: RecyclerView
    private var chatAdapter:RunningEventsAdapter?=null
    private var meventlist: List<Event>?=null
    private var list : MutableList<Event>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        recyclerviewrunningevent=view.findViewById(R.id.recycler_view_running)
        recyclerviewrunningevent.setHasFixedSize(true)
        val linearlayoutmanager= LinearLayoutManager(context)
        linearlayoutmanager.stackFromEnd=true
        recyclerviewrunningevent.layoutManager=linearlayoutmanager
        val db = FirebaseFirestore.getInstance()

        db.collection("allEvents").get()
//            .addOnSuccessListener {
//
//            }
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    list= mutableListOf<Event>()
                    list!!.clear()
                    for (document in task.result) {
                        list!!.add(document.toObject<Event>())
                    }

                } else {
                    Toast.makeText(view.context,"list made",Toast.LENGTH_LONG).show()
                }
                chatAdapter= list?.let { RunningEventsAdapter(view.context, it) }
                recyclerviewrunningevent.adapter=chatAdapter
//                chatAdapter?.notifyDataSetChanged()
            })
         floatingactionbtn=view.findViewById(R.id.floating_action_home)
         floatingactionbtn.setOnClickListener {
            val intent=Intent(context,AddingEvent::class.java)
             startActivity(intent)
        }
        return view
    }



}