package com.example.champions

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class clientSignin : AppCompatActivity() {
    private val auth:FirebaseAuth=Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clientsignin)
        val email : EditText=findViewById(R.id.cemailin)
        val password: EditText=findViewById(R.id.cpassin)
        val signIn:Button=findViewById(R.id.clogin)
        signIn.setOnClickListener {
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
}