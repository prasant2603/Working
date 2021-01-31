package com.example.champions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WelcomeAdviser : AppCompatActivity() {
    private lateinit var email:String
    private val db:FirebaseFirestore= FirebaseFirestore.getInstance()
    private val auth:FirebaseAuth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_adviser)
        val queryFetch:TextView=findViewById(R.id.queryFetch)
        val sendResponse:Button=findViewById(R.id.sendRespond)
        db.collection("Query").document("Client Query").get()
            .addOnSuccessListener {document ->
                if(document.getString("Query").toString().isEmpty())
                {
                    queryFetch.setText("No Query Asked Yet")
                }
                else
                {
                    queryFetch.setText(document.getString("Query").toString())
                    email=document.getString("Email").toString()
                }
            }
        sendResponse.setOnClickListener {
            sendResponse()
        }
    }

    private fun sendResponse() {
        val queryRespond:EditText=findViewById(R.id.queryRespond)
        if(queryRespond.text.isEmpty())
        {
            queryRespond.error="Please Enter some Respond"
            queryRespond.requestFocus()
        }
        else
        {

        }
    }
}