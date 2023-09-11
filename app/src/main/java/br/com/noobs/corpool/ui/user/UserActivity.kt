package br.com.noobs.corpool.ui.user

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.noobs.corpool.MainActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class UserActivity : AppCompatActivity() {


    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser?.let {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } ?: run {
            createSignInIntent()
        }
    }

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }


    private fun createSignInIntent() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }


    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        } else {
            Toast.makeText(this, "Erro ao logar", Toast.LENGTH_SHORT).show()
        }
    }
}