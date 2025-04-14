package com.example.moviesearchapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.moviesearchapp.databinding.ActivityRegisterBinding
import com.example.moviesearchapp.ui.viewmodel.FirebaseViewModel

class RegisterActivity : ComponentActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseViewModel.register(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        firebaseViewModel.authResult.observe(this) { (success, error) ->
            if (success) {
                Log.d("RegisterActivity", "Registration successful, navigating to MovieListActivity")
                val intent = Intent(this, MovieListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Registration failed: $error", Toast.LENGTH_SHORT).show()
                Log.e("RegisterActivity", "Registration failed: $error")
            }
        }
    }
}
