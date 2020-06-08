package com.example.moorthi.taskkttelematices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.moorthi.taskkttelematices.model.Controller
import com.example.moorthi.taskkttelematices.model.LoginModel
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.exceptions.RealmPrimaryKeyConstraintException
import kotlinx.android.synthetic.main.activity_register_avtivity.*

class RegisterAvtivity :  AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_avtivity)
        objretaxt.setOnClickListener(View.OnClickListener {
            val i = Intent(this@RegisterAvtivity, LoginActivity::class.java)
            startActivity(i)
        })
        objBnregister.setOnClickListener(View.OnClickListener {
             if (objreemail.length() == 0) {
            showSnackBar("Enter a valid email")
            objreemail.requestFocus()
           } else if (objrefirstname.length() == 0) {
                showSnackBar("Enter FirstName")
                objrefirstname.requestFocus()
            } else if (objrelastname.length() == 0) {
                showSnackBar("Enter LastName")
                objrelastname.requestFocus()
            }
            else if (objrepassword.length() == 0) {
                showSnackBar("Enter a valid password")
                 objrepassword.requestFocus()
            }
            else {
                try {
                    var str: String = objrefirstname.text.toString()
                    var firstName: String = str
                    var str1: String = objrelastname.text.toString()
                    var lastName: String = str1
                    var str2: String = objreemail.text.toString()
                    var email: String = str2
                    var str3: String = objrepassword.text.toString()
                    var password: String = str3

                    Controller.insertUser(firstName,lastName,email,password)
                    showSnackBar("Save Success")
                    clearText()
                } catch (e: RealmPrimaryKeyConstraintException) {
                    e.printStackTrace()
                    showSnackBar("User found on db.")
                }
            }
        })
    }

    private fun clearText() {
        objreemail.setText("")
        objrefirstname.setText("")
        objrelastname.setText("")
        objrepassword.setText("")
    }

    private fun showSnackBar(msg: String) {
        try {
            Snackbar.make(findViewById(R.id.relativeregister), msg, Snackbar.LENGTH_SHORT).show()
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

}
