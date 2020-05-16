package com.intive.patronage.smarthome.feature.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.navigator.LoginCoordinator

private const val RC_SIGN_IN = 1

class Authentication(
    private val appCompatActivity: AppCompatActivity,
    private val loginCoordinator: LoginCoordinator
): LoginServices() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    fun initGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(appCompatActivity.applicationContext.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(appCompatActivity.applicationContext, googleSignInOptions)
    }

    fun initAuthFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Google sign in finished successfully")
                goToDashboard()
            } else {
                googleSignInClient.signOut()
                showToast("Google sign in failed")
            }
        }
    }

    fun signIn() {
        appCompatActivity.startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
    }

    fun signOut() {
        auth.signOut()

        googleSignInClient.signOut().addOnCompleteListener {
            loginCoordinator.goToLoginScreen()
        }
    }

    fun checkIfUserIsLogged() {
        if (isUserLogged()) {
            loginCoordinator.goToMainScreen()
        }
    }

    fun isUserLogged() = auth.currentUser != null

    fun accountVerification(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (exception: ApiException) {
                Log.d(appCompatActivity.getString(R.string.login), "Google sign in failed", exception)
                showToast("Google sign in failed")
            }
        }
    }

    fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                goToDashboard()
            } else {
                Log.d("TAG", "failure", task.exception)
                showToast("Authentication failed")
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(appCompatActivity.applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun goToDashboard() {
        val data = appCompatActivity.intent?.data
        data?.let {
            loginCoordinator.goToScreenBasedOnDeeplinkIntent(appCompatActivity.intent)
        } ?: loginCoordinator.goToScreen()
    }
}
