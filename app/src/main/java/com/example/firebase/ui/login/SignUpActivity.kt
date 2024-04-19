package com.example.firebase.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.R
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btn: Button
    private lateinit var btn2: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btn = findViewById(R.id.registeruser)
        btn2 = findViewById(R.id.button2)



        btn.setOnClickListener {
            createAccount()
        }
        btn2.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }





    private fun createAccount(){
        val username = username.text.toString()
        val password = password.text.toString()

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
        }
        else {
            try {
                auth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            // go to login activity
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: Exception) {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

