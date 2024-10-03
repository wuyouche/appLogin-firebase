@file:Suppress("DEPRECATION")

package com.example.new1.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.new1.android.MyApplicationTheme
import com.example.new1.android.R
import com.example.new1.android.view.ui.LoginPage
import com.example.new1.android.viewModel.AuthViewModel
import com.example.new1.android.viewModel.BubbleViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        setContent {
            MyApplicationTheme {
                LoginPage(googleSignInClient,AuthViewModel(), BubbleViewModel())
            }
        }
    }
}

