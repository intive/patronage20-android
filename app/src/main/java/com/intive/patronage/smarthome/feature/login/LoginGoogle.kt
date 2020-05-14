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

class LoginGoogle(private val appCompatActivity: AppCompatActivity, private val loginCoordinator: LoginCoordinator): LoginServices() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 1

    fun initialGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(appCompatActivity.applicationContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(appCompatActivity.applicationContext, mGoogleSignInOptions)
    }

    fun initialAuthFirebase() {
        mAuth = FirebaseAuth.getInstance()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(appCompatActivity, "Google sign in finished successfully", Toast.LENGTH_SHORT).show()
                val data = appCompatActivity.intent?.data

                data?.let {
                    loginCoordinator.goToScreenBasedOnDeeplinkUri(data)
                } ?: loginCoordinator.goToScreen()
            } else {
                mGoogleSignInClient.signOut()
                Toast.makeText(appCompatActivity.applicationContext, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signIn() {
        appCompatActivity.startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)
    }

    fun signOut() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener {
            loginCoordinator.goToLoginScreen()
        }
    }

    fun userIsLogged() {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            loginCoordinator.goToMainScreen()
        }
    }

    fun isUserLogged(): Boolean{
        if(mAuth.currentUser == null){
            return false
        }
        return true
    }

    fun accountVerification(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w(appCompatActivity.getString(R.string.login), "Google sign in failed", e)
                Toast.makeText(appCompatActivity.applicationContext, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
