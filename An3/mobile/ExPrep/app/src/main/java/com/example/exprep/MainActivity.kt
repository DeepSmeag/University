package com.example.exprep

import WebSocketClient
import WebSocketListenerCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.exprep.services.Api
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*


data class MenuItem(val code: Int, val name: String, val price: Double)

class MainActivity : AppCompatActivity(), WebSocketListenerCallback  {

    private val webSocketClient = WebSocketClient(this)
    private var menuItems: List<MenuItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {
            MainScreen()
        }

        val url = Api.wsUrl
        webSocketClient.openSocket(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketClient.closeSocket()
    }

    override fun onMessage(message: String) {
        // Handle incoming message
    }

    override fun onFailure(t: Throwable) {
        // Handle failure
    }




    @Composable
    private fun MainScreen() {
        val loading by remember { mutableStateOf(menuItems == null) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (loading) {
                CircularProgressIndicator(color = Color.Magenta)
            } else {
                // Afiseaza meniul aici folosind Jetpack Compose
            }
        }
    }

}