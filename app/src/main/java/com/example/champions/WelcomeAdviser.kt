package com.example.champions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WelcomeAdviser : AppCompatActivity() {
    private val db:FirebaseFirestore= FirebaseFirestore.getInstance()
    private val auth:FirebaseAuth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_adviser)
        val queryFetch: TextView = findViewById(R.id.queryFetch)
        val sendResponse: Button = findViewById(R.id.sendRespond)
        val textView:TextView=findViewById(R.id.textView3)
        var email: String? =null
        db.collection("Query").document("Client Query").get()
            .addOnSuccessListener { document ->
                if (document.getString("Query").toString().isEmpty()) {
                    queryFetch.setText("No Query Asked Yet")
                }
                else
                {
                    queryFetch.setText(document.getString("Query").toString())
                    email = document.getString("Email").toString()
                    Log.d("TAG","Message :$email")
                    sendResponse.setOnClickListener {
                        val queryRespond: EditText = findViewById(R.id.queryRespond)
                        if (queryRespond.text.isEmpty()) {
                            queryRespond.error = "Please Enter some Respond"
                            queryRespond.requestFocus()
                        }
                        else
                        {
                            val mintent=Intent(Intent.ACTION_SEND)
                            mintent.data= Uri.parse("mailto:")
                            mintent.type="text/html"
                            mintent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                            mintent.putExtra(Intent.EXTRA_SUBJECT,"Query Respond")
                            mintent.putExtra(Intent.EXTRA_TEXT,queryRespond.text.toString())
                            startActivity(Intent.createChooser(mintent,"Choose Email"))
                        }
                    }
                }
            }.addOnFailureListener{
                Toast.makeText(baseContext,"Unable to Fetch Data",Toast.LENGTH_SHORT).show()
                email=""
            }
    }
}