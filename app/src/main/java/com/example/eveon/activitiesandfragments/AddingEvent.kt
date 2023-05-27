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
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.eveon.R
import com.google.android.material.snackbar.Snackbar
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
    var hours = 0
    private var loc = ""
    lateinit var eName :String
    lateinit var eDes :String
    lateinit var eTags :String
    private var eBug = 0
    private val cal = Calendar.getInstance()
    lateinit var dateBtn : Button
    lateinit var timeBtn : Button
    lateinit var eNameEt : EditText
    lateinit var eDesEt : EditText
    lateinit var eTagsEt : EditText
    lateinit var eBugEt : EditText
    lateinit var btn : Button
    lateinit var eHours : EditText

    private lateinit var locationDropDown : AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_event)
        setupActionBar()
        eNameEt = findViewById(R.id.eName)
        eDesEt = findViewById(R.id.eDes)
        eBugEt = findViewById(R.id.eBug)
        eTagsEt = findViewById(R.id.eTag)
        dateBtn = findViewById(R.id.dateButton)
        timeBtn = findViewById(R.id.timeButton)
        eHours = findViewById(R.id.eHours)
        btn = findViewById(R.id.button)
        locationDropDown = findViewById(R.id.eLoc)
        dateBtn.setOnClickListener {
            pickDate()
        }
        timeBtn.setOnClickListener {
            pickTime()
        }
        val items = listOf("Penman Auditorium","GJLT","OAT","Gymkhana Ground","Lower Ground","SAC","NLHC","Library")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        locationDropDown.setAdapter(adapter)
        locationDropDown.setOnItemClickListener { adapterView, _, i, _ ->
            loc = adapterView.getItemAtPosition(i).toString()
        }
        btn.setOnClickListener {
            val strhr = eHours.text.toString()
            if(strhr!="")
                hours = Integer.parseInt(strhr)
            eName = eNameEt.text.toString()
            eDes = eDesEt.text.toString()
            eBug = Integer.parseInt(eBugEt.text.toString())
            eTags = eTagsEt.text.toString()
            if(TextUtils.isEmpty(eName) || TextUtils.isEmpty(eDes)||TextUtils.isEmpty(dateBtn.text.toString())||TextUtils.isEmpty(timeBtn.text.toString())||loc==""||TextUtils.isEmpty(eBugEt.text.toString())||TextUtils.isEmpty(eTagsEt.text.toString())||TextUtils.isEmpty(eHours.text.toString())){
                showerrorsnackbar("Please Enter all the Field")
            }
            else{
                val uid : String? = mAuth.currentUser?.uid
                if (uid != null) {
                    val eventRef = db.collection("users").document(uid).collection("events")
                    var num:Int =0
                    var id :String=uid
                    uid.let { it1 ->
                        eventRef.get().addOnCompleteListener {
                            val list = it.result.documents
                            var i =0
                            val p = uid.length
                            for(d in list){
                               val idl = d.id.length
                                if(i!=Integer.parseInt(d.id.substring(p,idl)))
                               {
                                   break
                               }
                                i++
                            }
                            id = "$uid$i"
                            val event = Event(eName,sday,smonth,syear,shour,sminute,hours,eDes,loc,eBug,eid=id)
                            db.collection("users").document(it1).get().addOnSuccessListener() { ds ->
                                num = ds.toObject<UserModel>()!!.pDetails.eCount
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
                                            startActivity(Intent(this,MainActivity::class.java))
                                        }
                                }
                                    .addOnFailureListener {
                                            e->
                                        Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show()
                                    }

                            }
                        }

                    }
                }
            }

        }

    }
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
    fun setupActionBar() {
        val toolbar123 = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_full_image)
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
    }
    fun showerrorsnackbar(message:String){
        // gives the root element of a view without actually knowing its id
        val snackbar = Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG)
        val snackbarview=snackbar.view
        snackbarview.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackbar.show()

    }
}