package id.toriqwah.project.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import id.toriqwah.project.model.Product

class AppPreference {

    companion object {
        private const val a_first_time = "a_first_time"
        private const val a_login = "a_login"
        private const val a_token = "a_token"


        fun deleteAll() {
            Hawk.delete(a_first_time)
            Hawk.delete(a_login)
            Hawk.delete(a_token)

        }

        fun putFirstTime(value: Boolean) {
            Hawk.put(a_first_time, value)
        }

        fun isFirstTime(): Boolean {
            return (Hawk.get(a_first_time, true))
        }

        fun putLogin(value: Boolean) {
            Hawk.put(a_login, value)
        }

        fun isLogin(): Boolean {
            return (Hawk.get(a_login, false))
        }
        fun putToken(value: String) {
            Hawk.put(a_token, value)
        }

        fun getToken(): String {
            return (Hawk.get(a_token, ""))
        }

    }

}