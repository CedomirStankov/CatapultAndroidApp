package com.example.vezbanje

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vezbanje.auth.AuthStore
import com.example.vezbanje.core.theme.VezbanjeTheme
import com.example.vezbanje.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var autData: AuthStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        CoroutineScope(Dispatchers.IO).launch {
//            autData.updateAuthData("")
//        }

        enableEdgeToEdge()
        setContent {
            VezbanjeTheme {
                AppNavigation(autData)
            }
        }
    }
}

