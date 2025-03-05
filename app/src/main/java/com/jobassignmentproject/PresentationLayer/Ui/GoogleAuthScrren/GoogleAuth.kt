package com.jobassignmentproject.PresentationLayer.Ui.GoogleAuthScrren

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jobassignmentproject.PresentationLayer.utils.SnackbarUtil
import com.jobassignmentproject.R
import com.jobassignmentproject.databinding.ActivityGoogleAuthBinding

class GoogleAuth : AppCompatActivity() {

    private lateinit var binding: ActivityGoogleAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGoogleAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        onClickListener()
    }

    private fun initView() {
        // Initialize any views if needed
    }

    private fun onClickListener() {
        binding.btnbiomatic.setOnClickListener {
            checkBiometricSupportAndAuthenticate()
        }

        binding.btngoogleatuh.setOnClickListener {
            SnackbarUtil.showShort(binding.root, "Coming Soon")
        }
    }

    private fun checkBiometricSupportAndAuthenticate() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Biometric is available and ready to use
                showBiometricPrompt()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                SnackbarUtil.showShort(binding.root, "No biometric hardware available")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                SnackbarUtil.showShort(binding.root, "Biometric hardware currently unavailable")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                SnackbarUtil.showShort(binding.root, "No biometrics enrolled. Please set up in settings.")
            }
            else -> {
                SnackbarUtil.showShort(binding.root, "Biometric authentication not supported")
            }
        }
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                SnackbarUtil.showShort(binding.root, "Authentication error: $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                SnackbarUtil.showShort(binding.root, "Authentication succeeded!")
                // Proceed with your logic after successful authentication
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                SnackbarUtil.showShort(binding.root, "Authentication failed")
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use Account Password") // Mandatory for fallback
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}