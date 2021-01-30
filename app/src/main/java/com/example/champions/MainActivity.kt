package com.example.champions

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.asignup)
        button.setOnClickListener {
            val intent = Intent(this, advisorSignup::class.java)
            startActivity(intent)
        }
        val button1 = findViewById<Button>(R.id.csignup)
        button1.setOnClickListener {
            val intent = Intent(this, clientSignup::class.java)
            startActivity(intent)
        }
        val button2 = findViewById<Button>(R.id.asignin)
        button2.setOnClickListener {
            val intent = Intent(this, advisorSignin::class.java)
            startActivity(intent)
        }
        val button3 = findViewById<Button>(R.id.csignin)
        button3.setOnClickListener {
            val intent = Intent(this, clientSignin::class.java)
            startActivity(intent)
        }
    }
}