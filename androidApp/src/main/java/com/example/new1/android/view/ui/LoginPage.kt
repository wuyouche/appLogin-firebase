package com.example.new1.android.view.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.new1.android.view.background.LinearGradientBackgroundWithBubbles
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.example.new1.android.viewModel.AuthViewModel
import com.example.new1.android.viewModel.BubbleViewModel


@Composable
fun LoginPage(
    googleSignInClient: GoogleSignInClient,
    authViewModel: AuthViewModel,
    bubbleViewModel: BubbleViewModel
) {
    val isSignedIn = authViewModel.isSignedIn.collectAsState()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            authViewModel.handleSignInResult(result.data)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LinearGradientBackgroundWithBubbles(
            modifier = Modifier.fillMaxSize(),
            bubbles = bubbleViewModel.bubbles
        )

        LoginContent(
            isSignedIn = isSignedIn.value,
            onSignInClick = {
                authViewModel.signIn(googleSignInClient) { intent ->
                    launcher.launch(intent)
                }
            }
        )
    }
}
@Composable
fun LoginContent(isSignedIn: Boolean, onSignInClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isSignedIn) {
            Button(onClick = onSignInClick) {
                Text(text = "Sign in with Google")
            }
        } else {
            Text(text = "Welcome! You are signed in.")
        }
    }
}