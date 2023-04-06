package pages

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.eveon.R
import com.google.android.material.snackbar.Snackbar

class Login : AppCompatActivity() {
    private lateinit var  mprogressdialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnsignup=findViewById<TextView>(R.id.gotoRegister)
        btnsignup.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }







    }
    private fun revalidate1(
        email1: String,
        password1: String
    )
            : Boolean {
        return when{
            TextUtils.isEmpty(email1)->{
                showerrorsnackbar("Please enter your Email Address")
                false
            }
            TextUtils.isEmpty(password1)->{
                showerrorsnackbar("Please enter your Password")
                false
            }
            else->{
                true
            }

        }




    }
    fun showprogressdialog(Text:String){
        mprogressdialog=Dialog(this)
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
    fun showerrorsnackbar(message:String){
        // gives the root element of a view without actually knowing its id
        val snackbar = Snackbar.make(findViewById(android.R.id.content),message, Snackbar.LENGTH_LONG)
        val snackbarview=snackbar.view
        snackbarview.setBackgroundColor(ContextCompat.getColor(this, R.color.snackbar_error_color))
        snackbar.show()

    }
}