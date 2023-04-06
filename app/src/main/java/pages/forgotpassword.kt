package pages

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.eveon.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class forgotpassword : AppCompatActivity() {
    private lateinit var  mprogressdialog: Dialog
    private lateinit var etemail:TextView
    private lateinit var resetbutton:Button
    private lateinit var mauth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

       etemail=findViewById(R.id.inputEmail)
        resetbutton=findViewById(R.id.resetButton)
        mauth=FirebaseAuth.getInstance()
        resetbutton.setOnClickListener {
         val password=etemail.text.toString().trim{it<=' '}
            if(password.isEmpty())
            {
                showerrorsnackbar("Please Enter Your Email")
            }
            if (!password.isEmpty())
            {
                showprogressdialog("Sending Email...")
                mauth.sendPasswordResetEmail(password).addOnCompleteListener {
                    task->hideprogressdialog()
                    if(task.isSuccessful)
                    {
                        val layout123=layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
                        val   toast4: Toast = Toast(this)
                        toast4.view=layout123
                        val txtmsg12:TextView=layout123.findViewById(R.id.textview_toast)
                        txtmsg12.setText( "Email Sent Successfully,You Can Now Reset Your Password")
                        toast4.duration.toLong()
                        toast4.show()
                        finish()
                    }
                    else
                    {
                        val layout1=layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
                        val toast1: Toast = Toast(this)
                        toast1.view=layout1
                        val txtmsg1:TextView=layout1.findViewById(R.id.textview_toast1)
                        txtmsg1.setText(task.exception!!.message)
                        toast1.duration.toShort()
                        toast1.show()
                    }
                }

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
}