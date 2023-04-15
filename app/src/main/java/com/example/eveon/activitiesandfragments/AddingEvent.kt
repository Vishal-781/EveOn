package com.example.eveon.activitiesandfragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.eveon.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import models.Event
import models.UserModel
import java.util.*
import kotlin.math.min

class AddingEvent : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var mAuth = FirebaseAuth.getInstance()
    private var db = FirebaseFirestore.getInstance()

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0
    var sday = 0
    var smonth = 0
    var syear = 0
    var shour = 0
    var sminute = 0
    private var loc = ""
    lateinit var eName :String
    lateinit var eDes :String

    private val cal = Calendar.getInstance()
    lateinit var dateBtn : Button
    lateinit var timeBtn : Button
    lateinit var eNameEt : EditText
    lateinit var eDesEt : EditText
    lateinit var btn : Button
    private lateinit var locationDropDown : AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_event)
        eNameEt = findViewById(R.id.eName)
        eDesEt = findViewById(R.id.eDes)
        dateBtn = findViewById(R.id.dateButton)
        timeBtn = findViewById(R.id.timeButton)
        btn = findViewById(R.id.button)
        locationDropDown = findViewById(R.id.location)
        dateBtn.setOnClickListener {
            pickDate()
        }
        timeBtn.setOnClickListener {
            pickTime()
        }
        val items = listOf("Penman Auditorium","GJLT","OAT","Gymkhana Ground","Lower Ground","SAC","NLHC","Library")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        locationDropDown.setAdapter(adapter)
        locationDropDown.setOnItemClickListener { adapterView, view, i, l ->
            loc = adapterView.getItemAtPosition(i).toString()
        }
        btn.setOnClickListener {

            eName = eNameEt.text.toString()
            eDes = eDesEt.text.toString()
            if(TextUtils.isEmpty(eName) || TextUtils.isEmpty(eDes)||TextUtils.isEmpty(dateBtn.text.toString())||TextUtils.isEmpty(timeBtn.text.toString())||loc==""){
                Toast.makeText(this,"Enter all the fields!!",Toast.LENGTH_LONG).show()
            }
            else{
                val uid : String? = mAuth.currentUser?.uid
                if (uid != null) {
                    val eventRef = db.collection("users").document(uid).collection("events")
                    val event = Event(eName,sday,smonth,syear,shour,sminute,eDes,loc)
                    var num:Int =0
                    var id :String=uid
                    uid.let {
                        db.collection("users").document(it).get().addOnSuccessListener() { ds ->
                            num = ds.toObject<UserModel>()?.pDetails?.eCount!!
                            id = "$uid$num"
                            num+=1
//                            Toast.makeText(this,id,Toast.LENGTH_LONG).show()
                        }.addOnCompleteListener(){
                            eventRef.document(id).set(event).addOnSuccessListener {
                                db.collection("users").document(uid).update("pdetails.ecount",num)
                                db.collection("allEvents").document(id).set(event).addOnSuccessListener {
                                    Toast.makeText(this,"Event added",Toast.LENGTH_LONG).show()
                                }
                                    .addOnFailureListener {
                                            e->
                                        Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
                                    }
                                    .addOnCompleteListener {
                                        finish()
                                    }
                            }
                                .addOnFailureListener {
                                    e->
                                    Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
                                }

                        }
                        //                    val db1 = FirebaseFirestore.getInstance()
                        //                    db1.collection("users").document(id).set(event)

                    }
                }
            }

        }

    }


//    eventRef.document(id).set(event).addOnSuccessListener {
////                            ds.toObject<UserModel>()?.eCount= ds.toObject<UserModel>()?.eCount?.plus(
////                                1
////                            )!!
////                            db.collection("AllEvents").document(id).set(event)
//        Toast.makeText(this,"Event Added", Toast.LENGTH_LONG).show()
//    }
//    .addOnFailureListener { e->
//        Toast.makeText(this, "Event could not be added\nError: $e", Toast.LENGTH_LONG).show()
//    }
//    .addOnCompleteListener {
//        finish()
//    }
    private fun pickTime() {
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)

        TimePickerDialog(this, this, hour,minute,true).show()
    }

    private fun pickDate() {
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)

        DatePickerDialog(this, this, year,month,day).show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val mth = p2 +1
        sday = p3
        smonth = p2
        syear = p1
        dateBtn.text = "$p3-$mth-$p1"
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        shour = p1
        sminute = p2
        timeBtn.text = "$p1:$p2"
    }
}