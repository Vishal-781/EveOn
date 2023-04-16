package pages

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.Window
import android.widget.Button
//import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.eveon.R
import com.example.eveon.activitiesandfragments.MainActivity
//import com.example.eveon.activitiesandfragments.p_details
//import com.facebook.AccessToken
//import com.facebook.CallbackManager
//import com.facebook.FacebookCallback
//import com.facebook.FacebookException
//import com.facebook.login.LoginManager
//import com.facebook.login.LoginResult
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.GoogleAuthCredential
//import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {
    private lateinit var  mprogressdialog: Dialog
    private lateinit var mauth:FirebaseAuth
//     private lateinit var googlesigninclient:GoogleSignInClient
//     private lateinit var facebookbutton:Button
//     private lateinit var callbackmanager:CallbackManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    mauth= FirebaseAuth.getInstance()
        val btnsignup=findViewById<TextView>(R.id.gotoRegister)
//        facebookbutton=findViewById(R.id.facebookLogin)
        btnsignup.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }

//        callbackmanager= CallbackManager.Factory.create()
//        val accessToken=AccessToken.getCurrentAccessToken()
//        if(accessToken!=null && !accessToken.isExpired)
//        {
//           startActivity(Intent(this,MainActivity::class.java))
//            finish()
//        }
//        LoginManager.getInstance().registerCallback(callbackmanager,object:FacebookCallback<LoginResult>{
//            override fun onCancel() {
//
//            }
//
//            override fun onError(error: FacebookException) {
//
//            }
//
//            override fun onSuccess(result: LoginResult) {
//                startActivity(Intent(this@Login,MainActivity::class.java))
//                finish()
//            }
//
//        })
//        facebookbutton.setOnClickListener {
//            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile,email"))
//        }
        findViewById<Button>(R.id.lgnButton).setOnClickListener{
           signinregistereduser()
    }
//        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
//        requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
//
//        googlesigninclient= GoogleSignIn.getClient(this,gso)
//        val googlelogin=findViewById<ImageView>(R.id.googleLogin).setOnClickListener {
//            showprogressdialog("Signing Up...")
//            SigninGoogle()
//        }
        val forgotpassword=findViewById<TextView>(R.id.forgotPassword).setOnClickListener {
            val intent =Intent(this,forgotpassword::class.java)
            startActivity(intent)

        }







    }

//    private fun SigninGoogle() {
//
//        val signinintent=googlesigninclient.signInIntent
//        startActivityForResult(signinintent,100)
//
//
//
//
//
//    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode==100)
//        {
//            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
//            val account=task.getResult(ApiException::class.java)
//            val credential=GoogleAuthProvider.getCredential(account.idToken,null)
//            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(this) {
//            task->hideprogressdialog()
//                if(task.isSuccessful)
//                {
//
//
//                    val layout123=layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
//                    val   toast4: Toast = Toast(this)
//                    toast4.view=layout123
//                    val txtmsg12:TextView=layout123.findViewById(R.id.textview_toast)
//                    txtmsg12.setText( "You Have Signed In Successfully")
//                    toast4.duration.toLong()
//                    toast4.show()
//                    val intent=Intent(this,MainActivity::class.java)
//                    startActivity(intent)
//
//
//                }
//                else{
//
//                    val layout1=layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
//                    val toast1:Toast= Toast(this)
//                    toast1.view=layout1
//                    val txtmsg1:TextView=layout1.findViewById(R.id.textview_toast1)
//                    txtmsg1.setText(task.exception!!.message)
//                    toast1.duration.toShort()
//                    toast1.show()
//
//                }
//            }
//        }
//    }

    private fun signinregistereduser() {
        val email1:String = findViewById<TextView>(R.id.inputEmail).text.toString().trim{it<= ' ' }
        val password1:String =findViewById<TextView>(R.id.inputPassword).text.toString().trim{it<=' '}
        if(revalidate1(email1, password1))
        {
          showprogressdialog("Signing In...")
            mauth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this){
              task-> hideprogressdialog()
              if(task.isSuccessful)
              {
                  val layout123=layoutInflater.inflate(R.layout.custom_toast_layout,findViewById(R.id.view_layout_of_toast))
                  val   toast4: Toast = Toast(this)
                  toast4.view=layout123
                  val txtmsg12:TextView=layout123.findViewById(R.id.textview_toast)
                  txtmsg12.setText( "You Have Signed In Successfully")
                  toast4.duration.toLong()
                  toast4.show()
                  startActivity(Intent(this, MainActivity::class.java))
                  finish()
              }else{
                  val layout1=layoutInflater.inflate(R.layout.error_toast_layout,findViewById(R.id.view_layout_of_toast1))
                  val toast1:Toast= Toast(this)
                  toast1.view=layout1
                  val txtmsg1:TextView=layout1.findViewById(R.id.textview_toast1)
                  txtmsg1.setText("Authentication failed,Please enter correct credentials.")
                  toast1.duration.toShort()
                  toast1.show()
              }
            }





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