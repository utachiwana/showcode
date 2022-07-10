package com.utachiwana.messenger.network

object FConfig {

    const val FIREBASE_COL_USERS = "users"
    const val FIREBASE_FIELD_PWD = "password"
    const val FIREBASE_FIELD_LOGIN = "login"
    const val PREFERENCES_AUTH = "auth"

    const val EMPTY_LOGIN = "reg_empty_login"
    const val EMPTY_PWD = "reg_empty_pwd"
    const val AUTH_WRONG_PWD = "auth_pwd_wrong"
    const val AUTH_WRONG_LOGIN = "auth_login_wrong"
    const val AUTH_DONE = "auth_done"
    const val REG_ALREADY = "reg_already"
    const val REG_INCORRECT_PWD = "reg_incorrect_pwd"
    const val REG_INCORRECT_LOGIN = "reg_incorrect_login"
    const val REG_DONE = "reg_successful"
    const val REG_PWD_NOT_REPEAT = "reg_pwd_not_repeat"
    const val UNEXPECTED_ERROR = "unexpected_error"

    private const val regex = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_"

    fun checkRules(login: String, pwd: String, repeatPwd: String = pwd): String? {
        return if (login.isEmpty()) EMPTY_LOGIN
        else if (login.length < 5 || login.length > 30 || login.filter { it in regex }.length != login.length) REG_INCORRECT_LOGIN
        else if (pwd.isEmpty()) EMPTY_PWD
        else if (pwd.length < 5) REG_INCORRECT_PWD
        else if (pwd != repeatPwd) REG_PWD_NOT_REPEAT
        else null
    }

}