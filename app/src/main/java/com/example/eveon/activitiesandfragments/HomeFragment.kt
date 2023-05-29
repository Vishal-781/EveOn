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
    private var eventAdapterRunningEvents:RunningEventsAdapter?=null
    private var eventAdapterUpcomingEvents:UpcomingEventsAdapter?=null
    private var meventlist: List<Event>?=null
    private var list1 : MutableList<Event>?=null
    private var list2 : MutableList<Event>?=null
    private lateinit var db : FirebaseFirestore

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
        db = FirebaseFirestore.getInstance()

        db.collection("allEvents").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    list1 = mutableListOf<Event>()
                    list1!!.clear()
                    list2 = mutableListOf<Event>()
                    list2!!.clear()

                    for (doc in task.result) {
                        val currTime : Long = System.currentTimeMillis()
                        val ev = doc.toObject<Event>()
                        val hrs = ev.eHours
                        val cal = Calendar.getInstance()
                        cal.set(ev.eYear, ev.eMonth, ev.eDay, ev.eHour, ev.eMinute)
                        val eStartTime : Long = cal.timeInMillis
                        cal.add(Calendar.HOUR_OF_DAY,hrs)
                        val eFinishTime : Long = cal.timeInMillis
                        if(currTime >= eStartTime)
                        {
                            if(eFinishTime>=currTime)
                            {
                                db.collection("allEvents").document(ev.eid).update("bit",1)
                                list2!!.add(ev)
                            }
                            else
                            {
                                db.collection("allEvents").document(ev.eid).update("bit",-1)
                            }
                        }
                        else
                        {
                            db.collection("allEvents").document(ev.eid).update("bit",0)
                            list1!!.add(ev)
                        }
                    }
                    eventAdapterUpcomingEvents = list1?.let { UpcomingEventsAdapter(view.context, it) }
                    recyclerviewupcomingevent.adapter = eventAdapterUpcomingEvents
                    eventAdapterRunningEvents = list2?.let { RunningEventsAdapter(view.context, it) }
                    recyclerviewrunningevent.adapter = eventAdapterRunningEvents
                }
            }

        floatingactionbtn=view.findViewById(R.id.floating_action_home)
         floatingactionbtn.setOnClickListener {
            val intent=Intent(context,AddingEvent::class.java)
             startActivity(intent)
        }
        return view
    }



}