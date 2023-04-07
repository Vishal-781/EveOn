package pages

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.eveon.R
import com.example.eveon.activitiesandfragments.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import models.UserModel
import models.pDetails

class SignUp : AppCompatActivity() {
    var firebaseDatabase = FirebaseFirestore.getInstance()
    var mAuth = FirebaseAuth.getInstance()
    private lateinit var  mprogressdialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
       findViewById<Button>(R.id.sgnButton).setOnClickListener {
           userRegistered()
       }
          val btnsignup=findViewById<TextView>(R.id.gotoLogin)
              btnsignup.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }





    }

    private fun userRegistered() {
//        val name =""
        val email:String=findViewById<TextView>(R.id.inputEmail).text.toString().trim {it<=' '}
        val password:String=findViewById<TextView>(R.id.inputPassword).text.toString().trim{it<=' '}
        val uid = mAuth.currentUser?.uid
//        var userRef = uid?.let { firebaseDatabase.collection("users").document(it) }
//        val pDetails = pDetails(name, email, )
//        val user = UserModel()
        if(formValidate(email, password))
        {
           showProgressDialog("Signing Up...")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task->
                hideProgressDialog()
                if(task.isSuccessful)
                {
                    val layout  = layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
                    val toast = Toast(this)
                    toast.view=layout
                    val  txtmst:TextView=layout.findViewById(R.id.textview_toast)
                    txtmst.text = getString(R.string.sucReg)
                    toast.duration.toLong()
                    toast.show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                }else {
                    val layout1 =layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
                    val toast1 =Toast(this)
                    toast1.view=layout1
                    val txtmsg:TextView=layout1.findViewById(R.id.textview_toast1)
                    txtmsg.text = task.exception!!.message
                    toast1.duration.toLong()
                    toast1.show()
                }
            }
        }
    }

    private fun showProgressDialog(Text:String){
        mprogressdialog= Dialog(this)
        mprogressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mprogressdialog.setContentView(R.layout.dialog_progress)
        mprogressdialog.setCancelable(false)
        mprogressdialog.findViewById<TextView>(R.id.tv_progress_text).text=Text

        mprogressdialog.show()
        Handler().postDelayed({},2000)


    }
    private fun hideProgressDialog()
    {
        mprogressdialog.dismiss()
    }
    private fun formValidate(email:String, password:String)
            : Boolean {
        return when{
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter your Email Address")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter your Password")
                false
            }
            else->{
                true
            }

        }



    }
    private fun showErrorSnackBar(message:String){
        // gives the root element of a view without actually knowing its id
        val snackBar = Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG)
        val snackBarView=snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackBar.show()
    }
}