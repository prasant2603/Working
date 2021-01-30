package com.example.champions

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import data.User

class clientSignup : AppCompatActivity() {
    private val auth: FirebaseAuth=Firebase.auth
    private val database:DatabaseReference=Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clientsignup)
        val clientSignup: Button=findViewById(R.id.clientSignup)
        clientSignup.setOnClickListener {
            signup()
        }
    }
    private fun signup() {
        val userId:EditText=findViewById(R.id.clientId)
        val name: EditText=findViewById(R.id.clientName)
        val clientNumber: EditText=findViewById(R.id.clientPhone)
        val clientEmail : EditText=findViewById(R.id.clientEmail)
        val clientPassword: EditText=findViewById(R.id.clientPassword)
        val progressbar: ProgressBar=findViewById(R.id.progressBar)
        var email:String=clientEmail.text.toString()
        if(name.text.toString().isEmpty())
        {
            name.error="Cannot be Empty"
            name.requestFocus()
            return
        }
        if(userId.text.toString().isEmpty())
        {
            userId.error="Cannot be Empty"
            userId.requestFocus()
            return
        }
        if(clientNumber.text.toString().isEmpty())
        {
            clientNumber.error="Cannot be Empty"
            clientNumber.requestFocus()
            return
        }

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
                        var user= User(name.text.toString(),clientNumber.text.toString(),clientEmail.text.toString())
                        database.child("users").child(userId.text.toString()).setValue(user)
                        startActivity(Intent(this,clientSignin::class.java))
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }

    }
}