package com.example.champions

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import data.clientQuery

class WelcomeClient : AppCompatActivity() {
    private var database:DatabaseReference=Firebase.database.reference
    private var auth:FirebaseAuth=Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_client)
        val wcSubmit:Button=findViewById(R.id.wcSubmit)
        val user=auth.currentUser
        val userId:String= user!!.providerId.toString()
        val query:EditText=findViewById(R.id.clientQuery)
        wcSubmit.setOnClickListener {
            if(!query.text.toString().isEmpty())
            {
                database.child("users").child(userId).child(userId).setValue(query.text.toString())
            }
            else
            {
                query.error="Query Cannot be empty"
                query.requestFocus()
                return@setOnClickListener
            }
        }
    }
}