package com.example.champions

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class advisorSignin : AppCompatActivity() {
    private val auth:FirebaseAuth=Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advisorsignin)
        val login:Button=findViewById(R.id.alogin)
        login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email : EditText=findViewById(R.id.aemail)
        val password: EditText=findViewById(R.id.apassin)
        if(email.text.toString().isEmpty())
        {
            email.error="Empty Email"
            email.requestFocus()
            return
        }
        if(password.text.toString().isEmpty())
        {
            password.error="Empty Email"
            password.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Login Succesful",
                        Toast.LENGTH_SHORT).show()
                }
                else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Login failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }
}