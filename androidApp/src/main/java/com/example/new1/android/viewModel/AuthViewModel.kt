@file:Suppress("DEPRECATION")

package com.example.new1.android.viewModel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    private var signInCallback: ((Intent) -> Unit)? = null

    init {
        checkSignInStatus()
    }

    fun signIn(googleSignInClient: GoogleSignInClient, callback: (Intent) -> Unit) {
        signInCallback = callback
        val signInIntent = googleSignInClient.signInIntent
        callback(signInIntent)
    }

    fun handleSignInResult(data: Intent?) {
        viewModelScope.launch {
            try {
                data?.let {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(it)
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "Google sign-in failed: ${e.message}", e)
            } catch (e: Exception) {
                Log.w("GoogleSignIn", "An unexpected error occurred", e)
            }
        }
    }

    private fun checkSignInStatus() {
        _isSignedIn.value = auth.currentUser != null
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        try {
            auth.signInWithCredential(credential).await()
            val user = auth.currentUser
            Log.d("GoogleSignIn", "signInWithCredential:success, user: ${user?.providerData}")
            _isSignedIn.value = true
        } catch (e: Exception) {
            Log.w("GoogleSignIn", "signInWithCredential:failure", e)
            _isSignedIn.value = false
        }
    }
}
