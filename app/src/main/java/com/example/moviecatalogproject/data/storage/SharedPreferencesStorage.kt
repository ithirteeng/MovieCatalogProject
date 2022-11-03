package com.example.moviecatalogproject.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.moviecatalogproject.domain.common.model.Token

class SharedPreferencesStorage(context: Context) : TokenStorage {

    companion object {
        const val ENCRYPTED_SHARED_PREFS_NAME = "encryptedSharedPreferences"
    }

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        ENCRYPTED_SHARED_PREFS_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )


    override fun saveToken(token: Token) {
        sharedPreferences.edit().putString(TokenStorage.TOKEN_KEY, token.token).apply()
    }

    override fun getToken(): Token {
        return Token(
            sharedPreferences.getString(
                TokenStorage.TOKEN_KEY, TokenStorage.EMPTINESS_KEY
            ).toString()
        )
    }

    override fun deleteToken() {
        sharedPreferences.edit().remove(TokenStorage.TOKEN_KEY).apply()
    }
}