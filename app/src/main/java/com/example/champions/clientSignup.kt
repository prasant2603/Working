package com.example.champions

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class clientSignup : AppCompatActivity() {
    private val auth: FirebaseAuth=Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clientsignup)
        val clientSignup: Button=findViewById(R.id.clientSignup)
        clientSignup.setOnClickListener {
            signup()
        }
    }
    private fun signup() {
        val clientEmail : EditText=findViewById(R.id.clientEmail)
        val clientPassword: EditText=findViewById(R.id.clientPassword)
        if(clientEmail.text.isEmpty())
        {
            clientEmail.error="Email Cannot be empty"
            clientEmail.requestFocus()
            return
        }
        if(clientPassword.text.isEmpty())
        {
            clientPassword.error="Password cannot be empty"
            clientPassword.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(clientEmail.text.toString()).matches())
        {
            clientEmail.error="Invalid Email"
            clientEmail.requestFocus()
            return
        }
        if(clientPassword.text.length<6)
        {
            clientPassword.error="Enter Password of length more than 6"
            clientPassword.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(clientEmail.text.toString(), clientPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Authentication Success.",
                                Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,clientSignin::class.java))
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }

    }
}