package pages

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.eveon.R
import com.google.android.material.snackbar.Snackbar

class SignUp : AppCompatActivity() {
    private lateinit var  mprogressdialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)







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