package com.example.eveon.activitiesandfragments

import Adapters.RunningEventsAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        val db = FirebaseFirestore.getInstance()
        var list : MutableList<Event>
        db.collection("allEvents").get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    list= mutableListOf<Event>()
                    for (document in task.result) {
                        list.add(document.toObject<Event>())
                    }
                } else {
                    Toast.makeText(view.context,"list made",Toast.LENGTH_LONG).show()
                }
            })

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