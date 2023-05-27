package com.example.eveon.activitiesandfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.eveon.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import models.Event


class CardViewFully : AppCompatActivity() {
  private lateinit var db:FirebaseFirestore
  var name:String=""
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
       var eventImage=findViewById<ImageView>(R.id.imageView3)
        var eventDate=findViewById<TextView>(R.id.textView)
        var eventTime=findViewById<TextView>(R.id.textview_time)
        var eventLocation=findViewById<TextView>(R.id.textView2)
        var eventDescription=findViewById<TextView>(R.id.event_description)
        var registerButton=findViewById<Button>(R.id.eRegisterButton)
        var cardView = findViewById<CardView>(R.id.cardView)
        var progressbar = findViewById<ProgressBar>(R.id.progressBar2)
        var regTextView = findViewById<TextView>(R.id.reg_count_view)

        var eid=intent.getStringExtra("Url_event_id")
        db=FirebaseFirestore.getInstance()
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

            val docRef = db.collection("allEvents").document(eid)

            docRef.get().addOnCompleteListener { it ->
                val document = it.result
                var regCount:Int
                if(document.exists())
                {
                    regCount = document.getLong("regCount")?.toInt()!!
                    regCount +=1
                    docRef.update("regCount",regCount)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registration Successful!",Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {e->
                            Toast.makeText(this, "Error:${e.message}",Toast.LENGTH_LONG).show()
                        }
                }
            }
        }




    }

}