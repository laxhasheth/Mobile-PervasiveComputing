package com.example.moviesearchapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.moviesearchapp.databinding.ActivityLoginBinding
import com.example.moviesearchapp.ui.viewmodel.FirebaseViewModel

class LoginActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle login button click
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Show loading indicator
                binding.progressBar.visibility = View.VISIBLE
                firebaseViewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to register activity when the "Go to Register" link is clicked
        binding.tvGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        // Observe authentication result
        firebaseViewModel.authResult.observe(this) { (success, error) ->
            // Hide the loading indicator
            binding.progressBar.visibility = View.GONE

            if (success) {
                // Navigate to MovieListActivity on success
                startActivity(Intent(this, MovieListActivity::class.java))
                finish()
            } else {
                // Show error message
                Toast.makeText(this, "Login failed: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
