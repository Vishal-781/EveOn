package com.example.eveon.activitiesandfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.eveon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import models.Event
import models.UserModel
import java.util.*


class CardViewFully : AppCompatActivity() {
  private lateinit var db:FirebaseFirestore
  var mAuth = FirebaseAuth.getInstance()
  var name:String=""
    var flag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view_fully)
        // toolbar related due to changing of name
        val toolbar123 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_cardViewFully)
        setSupportActionBar(toolbar123)
        val actionbar = supportActionBar

        toolbar123.setTitleTextColor(resources.getColor(R.color.onPrimaryDark))
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)
        }
        toolbar123.setNavigationOnClickListener {
            onBackPressed()

        }
       val eventImage=findViewById<ImageView>(R.id.imageView3)
        val eventDate=findViewById<TextView>(R.id.textView)
        val eventTime=findViewById<TextView>(R.id.textview_time)
        val eventLocation=findViewById<TextView>(R.id.textView2)
        val eventDescription=findViewById<TextView>(R.id.event_description)
        val registerButton=findViewById<Button>(R.id.eRegisterButton)
        val cardView = findViewById<CardView>(R.id.cardView)
        val progressbar = findViewById<ProgressBar>(R.id.progressBar2)
        val regTextView = findViewById<TextView>(R.id.reg_count_view)
        registerButton.visibility = View.INVISIBLE
        val eid=intent.getStringExtra("Url_event_id")
        db=FirebaseFirestore.getInstance()

        val uid : String? = mAuth.currentUser?.uid
        val docRef = eid?.let { db.collection("allEvents").document(it) }

        db.collection("allEvents").document(eid!!).get().addOnSuccessListener { ds->
           eventDate.text="${ds.toObject<Event>()?.eDay}/${ds.toObject<Event>()?.eMonth}/${ds.toObject<Event>()?.eYear}"
            eventTime.text="${ds.toObject<Event>()?.eHour}:${ds.toObject<Event>()?.eMinute}"
            eventLocation.text="${ds.toObject<Event>()?.eLoc}"
            eventDescription.text="${ds.toObject<Event>()?.eDes}"
            actionbar!!.title="${ds.toObject<Event>()?.eName}"
            regTextView.text = "${ds.toObject<Event>()?.regCount}"
        }
            .addOnCompleteListener {
                progressbar.visibility = View.INVISIBLE
                eventImage.visibility = View.VISIBLE
                cardView.visibility = View.VISIBLE
                registerButton.visibility =  View.VISIBLE

            }
        registerButton.setOnClickListener {
            var eList = mutableListOf<String>()
            if (uid != null) {
                db.collection("users").document(uid).get().addOnCompleteListener {
                    eList = it.result.toObject<UserModel>()?.elist as MutableList<String>
                    for (eid_i in eList) {
                        if (eid_i == eid) {
                            flag = 1
                            break
                        }
                    }
                }.addOnSuccessListener {
                    if (flag == 0) {
                        docRef?.get()?.addOnCompleteListener { it ->
                            val document = it.result
                            var regCount: Int
                            if (document.exists()) {
                                regCount = document.getLong("regCount")?.toInt()!!
                                regCount += 1
                                docRef.update("regCount", regCount)
                                    .addOnSuccessListener {
                                        if (uid != null) {
                                            eList.add(document.get("eid").toString())
                                            db.collection("users").document(uid).update("elist", eList)
                                        }
                                        Toast.makeText(
                                            this,
                                            "Registration Successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Error:${e.message}", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            }
                        }
                    } else {
                        Toast.makeText(this, "Already registered!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            }
        }


}