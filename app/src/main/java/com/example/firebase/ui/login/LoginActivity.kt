package com.example.firebase.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.MainActivity
import com.example.firebase.R
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btn: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var btn4: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btn = findViewById(R.id.loginacv)
        btn4 = findViewById(R.id.button4)

        btn.setOnClickListener {
            login()
        }
        btn4.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }


    private fun login(){
        val username = username.text.toString()
        val password = password.text.toString()

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
        }
        else {
            try {
                auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        // go to main activity
                        val intent = Intent(this, MainActivity::class.java)
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

