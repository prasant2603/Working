package com.example.champions

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import data.clientQuery
import org.w3c.dom.Document

class WelcomeClient : AppCompatActivity() {
    private var db:FirebaseFirestore= FirebaseFirestore.getInstance()
    private var auth:FirebaseAuth=Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_client)
        val logout:Button=findViewById(R.id.logout)
        val wcSubmit:Button=findViewById(R.id.wcSubmit)
        val userId= auth.currentUser!!.uid
        var email:String?=""
        val query:EditText=findViewById(R.id.clientQuery)
        db.collection("Users").document(userId).get()
            .addOnSuccessListener { document ->
                email=document.getString("Email")
            }
        wcSubmit.setOnClickListener {
            if(!query.text.toString().isEmpty())
            {
                val userquery= hashMapOf(
                    "Query" to query.text.toString(),
                    "Email" to email.toString()
                )
                db.collection("Query").document("Client Query").set(userquery)
                    .addOnSuccessListener{
                        Toast.makeText(baseContext,"Query Submitted Succesful",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(baseContext,"Error while writing document",Toast.LENGTH_SHORT)
                            .show()
                    }
            }
            else
            {
                query.error="Query Cannot be empty"
                query.requestFocus()
                return@setOnClickListener
            }
        }
        logout.setOnClickListener {
            auth.signOut()
            Toast.makeText(baseContext,"Logout Sucessful",Toast.LENGTH_SHORT)
                .show()
            db.collection("Users").document(userId).get()
                .addOnSuccessListener { document ->
                    email=document.getString("Email")
                }
            val intent:Intent=Intent(this,MainActivity::class .java)
            startActivity(intent)
            finish()
        }
    }
}