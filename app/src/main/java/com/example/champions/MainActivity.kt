package com.example.champions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val db :FirebaseFirestore= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressbar:ProgressBar=findViewById(R.id.mainprogress)
        val signin: Button = findViewById(R.id.login)
        val signup: Button = findViewById(R.id.signup)
        signin.setOnClickListener {
            progressbar.visibility= View.VISIBLE
            signin()
            closeKeyboard()
        }
        signup.setOnClickListener {
            progressbar.visibility= View.VISIBLE
            signup()
        }
    }

    private fun closeKeyboard() {
        val view=this.currentFocus
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun signup() {
        val checkState: RadioGroup = findViewById(R.id.checkState)
        val id: Int = checkState.checkedRadioButtonId
        if (id != -1) {
            val radioButton: RadioButton = findViewById(checkState.checkedRadioButtonId)
            if (radioButton.text.toString() == "Advisor") {
                startActivity(Intent(this,advisorSignup::class.java))
                finish()
            }
            else
            {
                startActivity(Intent(this,clientSignup::class.java))
                finish()
            }
        }
        else
        {
            Toast.makeText(applicationContext,"Select a RadioButton",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun signin() {
        val checkState: RadioGroup = findViewById(R.id.checkState)
        val id: Int = checkState.checkedRadioButtonId
        if (id != -1) {
            val radioButton: RadioButton = findViewById(checkState.checkedRadioButtonId)
            if (radioButton.text.toString() == "Advisor") {
                loginAdvisor()
            }
            else
            {
                loginClient()
            }
        }
        else
        {
            Toast.makeText(applicationContext,"Select a RadioButton",
                Toast.LENGTH_SHORT).show()
        }
    }
    private fun loginClient() {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val email : EditText =findViewById(R.id.email)
        val password: EditText =findViewById(R.id.password)
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
                    var userId= Firebase.auth.currentUser?.uid
                    Log.d("TAG","Message ${userId}")
                    checkClient(userId)
                }
                else
                {

                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Login failed ",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun checkClient(userId: String?="") {
            db.collection("Users").document(userId!!)
                .get().addOnSuccessListener { result ->
                    if(result.get("isClient")!=null)
                    {
                        Toast.makeText(baseContext, "Login Succesful",
                            Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,WelcomeClient::class.java))
                        finish()
                    }
                    else
                    {
                        Toast.makeText(baseContext, "Email or Password is incorrect",
                            Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }

    private fun loginAdvisor(){
         val auth: FirebaseAuth = FirebaseAuth.getInstance()
         val email : EditText=findViewById(R.id.email)
         val password: EditText=findViewById(R.id.password)
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
                     checkAdviser(auth.currentUser!!.uid)
                 }
                 else {
                     // If sign in fails, display a message to the user.
                     Toast.makeText(baseContext, "Login failed.",
                         Toast.LENGTH_SHORT).show()
                 }
             }
     }
    private fun checkAdviser(uid: String) {
        db.collection("Users").document(uid)
            .get()
            .addOnSuccessListener { document ->
            if(document.getString("isAdviser")=="1") {
                Log.d("TAG", "DocumentSnapshot data: ${document.getString("isAdviser")}")
                Toast.makeText(baseContext,"Login Succesful",Toast.LENGTH_SHORT).show()
                var email:String=intent.getStringExtra("Email").toString()
                var query:String=intent.getStringExtra("Query").toString()
                val intent:Intent= Intent(this,WelcomeAdviser::class.java)
                startActivity(Intent(this,WelcomeAdviser::class.java))
                finish()
            } else {
                Toast.makeText(baseContext,"Email or Password is Incorrect",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }
}