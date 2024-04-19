package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firebase.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var destinationName: EditText
    private lateinit var destinationDescription: EditText

    // firestore
    private lateinit var db: FirebaseFirestore
    // auth
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()


if(auth.currentUser == null){
    // go to login
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
}
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        destinationName = findViewById(R.id.editTextText)
        destinationDescription = findViewById(R.id.editTextTextMultiLine)

        button.setOnClickListener {
            clickHandler()
        }


        val spinner: Spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.destinations,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Jashanpreet kaur Bucket list"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun clickHandler(){
        // if either of the fields are empty, show an error message - toast
        if(destinationName.text.isEmpty() || destinationDescription.text.isEmpty()){
            // show an error message
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
        else{
            // save the data to firestore
            saveToFirestore(destinationName.text.toString(), destinationDescription.text.toString())
        }
    }

    private fun saveToFirestore(destinationName: String, description:String){
        // save the data to firestore
        val destination = hashMapOf(
            "name" to destinationName,
            "description" to description
        )
        db.collection("destinations").add(destination)
            .addOnSuccessListener {
                Toast.makeText(this, "Destination added successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error adding destination", Toast.LENGTH_SHORT).show()
            }

    }
}