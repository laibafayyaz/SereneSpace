package com.example.serenespace

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object CryptoHelper {
    private const val ALGORITHM = "AES"
    // Note: In a production app, this key should be generated and stored in Android Keystore.
    // For this academic project, we use a fixed 16-character key for demonstration.
    private val keyValue = "MySuperSecretKey".toByteArray()

    fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(keyValue, ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encrypted = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun decrypt(value: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(keyValue, ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decoded = Base64.decode(value, Base64.DEFAULT)
        val decrypted = cipher.doFinal(decoded)
        return String(decrypted)
    }
}