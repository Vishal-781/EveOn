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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.eveon.R
import com.example.eveon.activitiesandfragments.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var  mprogressdialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
       findViewById<Button>(R.id.sgnButton).setOnClickListener {
           userregistered()
       }
     val btnsignup=findViewById<TextView>(R.id.gotoLogin)
        btnsignup.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }





    }

    private fun userregistered() {
        val name:String=findViewById<TextView>(R.id.inputname).text.toString().trim {it<=' '}
        val email:String=findViewById<TextView>(R.id.inputEmail).text.toString().trim {it<=' '}
        val password:String=findViewById<TextView>(R.id.inputPassword).text.toString().trim{it<=' '}
        if(formvalidate(name, email, password))
        {
           showprogressdialog("Signing Up...")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task->
                hideprogressdialog()
                if(task.isSuccessful)
                {
                    val layout  = layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
                    val toast: Toast = Toast(this)
                    toast.view=layout
                    val  txtmst:TextView=layout.findViewById(R.id.textview_toast)
                    txtmst.setText("You Have Successfully Registered")


                    toast.duration.toLong()
                    toast.show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                }else {
                    val layout1 =layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
                    val toast1: Toast=Toast(this)
                    toast1.view=layout1
                    val txtmsg:TextView=layout1.findViewById(R.id.textview_toast1)
                    txtmsg.setText(task.exception!!.message)
                    toast1.duration.toShort()
                    toast1.show()

                }
            }
        }
    }

    fun showprogressdialog(Text:String){
        mprogressdialog= Dialog(this)
        mprogressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mprogressdialog.setContentView(R.layout.dialog_progress)
        mprogressdialog.setCancelable(false)
        mprogressdialog.findViewById<TextView>(R.id.tv_progress_text).text=Text

        mprogressdialog.show()
        Handler().postDelayed({},2000)


    }
    fun hideprogressdialog()
    {
        mprogressdialog.dismiss()
    }
    private fun formvalidate(name:String,email:String,password:String)
            : Boolean {
        return when{
            TextUtils.isEmpty(name)->{
                showerrorsnackbar("Please enter your Name")
                false
            }
            TextUtils.isEmpty(email)->{
                showerrorsnackbar("Please enter your Email Address")
                false
            }
            TextUtils.isEmpty(password)->{
                showerrorsnackbar("Please enter your Password")
                false
            }
            else->{
                true
            }

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