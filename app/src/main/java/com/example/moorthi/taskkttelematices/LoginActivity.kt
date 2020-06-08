package com.example.moorthi.taskkttelematices

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.JobIntentService.enqueueWork
import com.example.moorthi.taskkttelematices.model.Controller
import com.example.moorthi.taskkttelematices.model.LoginModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register_avtivity.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    private val myRealmObject: LoginModel? = null
    private val mTimer: Timer? = null
    private val TIMER_INTERVAL = 120000 // 2 Minute

    private val TIMER_DELAY = 0
    var mContext: Appsevice? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        /*  mContext =   Appsevice()
        val serviceInetent = Intent(applicationContext, Appsevice::class.java)
        // serviceInetent.putExtra("sakthi", intent)
        mContext?.enqueuework(applicationContext,serviceInetent, "")*/
        btloRegister.setOnClickListener {
            val i = Intent(this@LoginActivity, RegisterAvtivity::class.java)
            startActivity(i)
        }
        objLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (objloEmail.length() == 0) {
                    showSnackBar("Enter EMAIL")
                    objloEmail.requestFocus()
                } else if (objloPassword.length() == 0) {
                    showSnackBar("Enter password")
                    objloPassword.requestFocus()
                }
                checkUser(objloEmail.text.toString(), objloPassword.text.toString())
            }

            private fun checkUser(email: String, password: String): Boolean {
                val realmObject = Controller.getSingleRecord(email, password)
                // val realmObject = Controller.getUser()
                for (myRealmObject in realmObject!!) {
                    if (myRealmObject != null) {
                        if (email == myRealmObject.em) {
                            val i = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(i)
                            clearTxt()
                            return true
                        } else {
                               showSnackBar("Invalid User")
                        }
                    }
                    return false
                }
                return false
            }

            private fun showSnackBar(msg: String) {
                try {
                    Snackbar.make(findViewById(R.id.objparentlogin), msg, Snackbar.LENGTH_SHORT).show()
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
            }
            private fun clearTxt(){
                objloEmail.setText("")
                objloPassword.setText("")
            }
        })
    }
}


