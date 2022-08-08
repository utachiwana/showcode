package com.utachiwana.messenger.ui

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.utachiwana.messenger.dagger.Auth
import com.utachiwana.messenger.data.local.AppSharedPreferences
import com.utachiwana.messenger.data.network.FConfig
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.security.MessageDigest
import javax.inject.Inject

@Auth
class AuthRepository @Inject constructor(
    private val store: FirebaseFirestore,
    private val authPref: AppSharedPreferences
) {

    private fun hashPwd(pwd: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(pwd.toByteArray()).joinToString { "%02x".format(it) }.replace(", ", "")
    }

    suspend fun auth(login: String, pwd: String): String? {
        val pass = hashPwd(pwd)
        return try {
            val user = store.collection(FConfig.FIREBASE_COL_USERS)
                .document(login)
                .get()
                .await()
            if (user == null) FConfig.AUTH_WRONG_LOGIN
            if (user.get(
                    FConfig.FIREBASE_FIELD_PWD,
                    String::class.java
                ) == pass
            ) {
                authPref.putString(FConfig.FIREBASE_FIELD_PWD, pwd)
                authPref.putString(FConfig.FIREBASE_FIELD_LOGIN, login)
                FConfig.AUTH_DONE
            } else FConfig.AUTH_WRONG_PWD
        } catch (e: Exception) {
            FConfig.UNEXPECTED_ERROR
        }
    }

    suspend fun register(login: String, pwd: String): String? {
        return try {
            store.collection(FConfig.FIREBASE_COL_USERS)
                .get()
                .await()
                .forEach { doc ->
                    if (doc.id == login) {
                        return FConfig.REG_ALREADY
                    }
                }
            val map = hashMapOf(FConfig.FIREBASE_FIELD_PWD to hashPwd(pwd))
            store.collection(FConfig.FIREBASE_COL_USERS)
                .document(login)
                .set(map, SetOptions.merge())
                .await()
            FConfig.REG_DONE
        } catch (e: Exception) {
            FConfig.UNEXPECTED_ERROR
        }
    }

    suspend fun isAuth(): String? {
        val login = authPref.getString(FConfig.FIREBASE_FIELD_LOGIN)
        val pwd = authPref.getString(FConfig.FIREBASE_FIELD_PWD)
        return if (login.isNotEmpty() || pwd.isNotEmpty()) auth(login, pwd)
        else null
    }

}
