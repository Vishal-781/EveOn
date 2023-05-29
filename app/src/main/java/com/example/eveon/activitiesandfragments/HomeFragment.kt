package com.example.eveon.activitiesandfragments

import Adapters.RunningEventsAdapter
import Adapters.UpcomingEventsAdapter
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
import java.util.*


class HomeFragment : Fragment() {
private lateinit var floatingactionbtn:FloatingActionButton
    private lateinit var recyclerviewrunningevent: RecyclerView
    private lateinit var recyclerviewupcomingevent:RecyclerView
    private var chatAdapterRunningEvents:RunningEventsAdapter?=null
    private var chatAdapterUpcomingEvents:UpcomingEventsAdapter?=null
    private var meventlist: List<Event>?=null
    private var list : MutableList<Event>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        recyclerviewupcomingevent=view.findViewById(R.id.upcoming_recycler)
        recyclerviewrunningevent=view.findViewById(R.id.recycler_view_running)
        recyclerviewupcomingevent.setHasFixedSize(true)
        val linearlayoutmanager1=LinearLayoutManager(context)
        linearlayoutmanager1.stackFromEnd=false
        recyclerviewupcomingevent.layoutManager=linearlayoutmanager1
        recyclerviewrunningevent.setHasFixedSize(true)
        val linearlayoutmanager= LinearLayoutManager(context)
        linearlayoutmanager.stackFromEnd = false
        recyclerviewrunningevent.layoutManager=linearlayoutmanager
        val db = FirebaseFirestore.getInstance()


        db.collection("allEvents").get()
            .addOnCompleteListener {
                if(it.isSuccessful)
                {
                    for(doc in it.result)
                    {
                        val currTime = System.currentTimeMillis()
                        val ev = doc.toObject<Event>()
                         val cal = Calendar.getInstance()
                            cal.set(ev.eYear,ev.eMonth,ev.eDay,ev.eHour,ev.eMinute)
                        val eTime = cal.timeInMillis
                        if(currTime>=eTime)
                            db.collection("allEvents").document(doc.id).update("bit",1)
                    }
                }
            }

        db.collection("allEvents").get()
//            .addOnSuccessListener {
//
//            }
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    list= mutableListOf<Event>()
                    list!!.clear()
                    for (document in task.result) {
//                        if(document.toObject<Event>().bit==0)
                        list!!.add(document.toObject<Event>())
                    }

                } else {
                    Toast.makeText(view.context,"list made",Toast.LENGTH_LONG).show()
                }
                chatAdapterUpcomingEvents= list?.let { UpcomingEventsAdapter(view.context, it) }
                recyclerviewupcomingevent.adapter=chatAdapterUpcomingEvents
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