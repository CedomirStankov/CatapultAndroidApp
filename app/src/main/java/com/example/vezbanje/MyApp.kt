package com.example.vezbanje

import android.app.Application
import android.util.Log
import com.example.vezbanje.auth.AuthStore
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {

    @Inject lateinit var autData: AuthStore

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(){
        super.onCreate()

        val authData = autData.authData.value


        Log.d("DATASTORE","Authdata = $authData")

    }
}