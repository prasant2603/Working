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

class advisorSignup : AppCompatActivity() {
    private val auth: FirebaseAuth = Firebase.auth
    private val database: DatabaseReference = Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advisorsignup)
        val advisorSubmit: Button = findViewById(R.id.advisorSubmit)
        advisorSubmit.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        val userId: EditText = findViewById(R.id.advisorId)
        val name: EditText = findViewById(R.id.advisorName)
        val Number: EditText = findViewById(R.id.advisorMobile)
        val Email: EditText = findViewById(R.id.advisorEmail)
        val Password: EditText = findViewById(R.id.advisorPassword)
        val college: EditText = findViewById(R.id.advisorCollege)
        val branch: EditText = findViewById(R.id.avisorBranch)
        //val progressbar: ProgressBar = findViewById(R.id.progressBar)
        if (name.text.toString().isEmpty()) {
            name.error = "Cannot be Empty"
            name.requestFocus()
            return
        }
        /*if (userId.text.toString().isEmpty()) {
            userId.error = "Cannot be Empty"
            userId.requestFocus()
            return
        }*/
        if (Number.text.toString().isEmpty()) {
            Number.error = "Cannot be Empty"
            Number.requestFocus()
            return
        }

        if (Email.text.isEmpty()) {
            Email.error = "Email Cannot be empty"
            Email.requestFocus()
            return
        }
        if (Password.text.isEmpty()) {
            Password.error = "Password cannot be empty"
            Password.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email.text.toString()).matches()) {
            Email.error = "Invalid Email"
            Email.requestFocus()
            return
        }
        if (Password.text.length < 6) {
            Password.error = "Enter Password of length more than 6"
            Password.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(Email.text.toString(), Password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Authentication Success.",
                                Toast.LENGTH_SHORT).show()
                        var user = AdvisorData(name.text.toString(),
                                Number.text.toString(), Email.text.toString(),
                        college.text.toString(),branch.text.toString())
                        database.child("Advisor").child(userId.text.toString()).setValue(user)
                        startActivity(Intent(this, advisorSignin::class.java))
                    } else {
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }
}