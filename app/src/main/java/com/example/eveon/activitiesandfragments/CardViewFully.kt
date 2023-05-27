package com.example.eveon.activitiesandfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.eveon.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
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





        var eid=intent.getStringExtra("Url_event_id")
        db=FirebaseFirestore.getInstance()
        db.collection("allEvents").document(eid!!).get().addOnSuccessListener { ds->
           eventDate.text="${ds.toObject<Event>()?.eDay}/${ds.toObject<Event>()?.eMonth}/${ds.toObject<Event>()?.eYear}"
            eventTime.text="${ds.toObject<Event>()?.eHour}:${ds.toObject<Event>()?.eMinute}"
            eventLocation.text="${ds.toObject<Event>()?.eLoc}"
            eventDescription.text="${ds.toObject<Event>()?.eDes}"
            actionbar!!.title="${ds.toObject<Event>()?.eName}"



        }







    }

}