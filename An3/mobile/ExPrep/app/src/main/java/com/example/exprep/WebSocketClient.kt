import android.util.Log
import com.example.exprep.MainActivity
import com.example.exprep.services.TAG
import okhttp3.*
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

interface WebSocketListenerCallback {
    fun onMessage(message: String)
    fun onFailure(t: Throwable)
}

class WebSocketClient(private val listener: WebSocketListenerCallback) {
    private var webSocket: WebSocket? = null

    fun openSocket(url: String) {
        val request = Request.Builder().url(url).build()
        val webSocketListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                // WebSocket connection opened
                Log.d(TAG, "WebSocket connection opened")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(TAG, "WebSocket message received: $text")
                listener.onMessage(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(TAG, "WebSocket connection failed ${t.message} ;; ${t.cause} ;; ${response?.message}}")
                listener.onFailure(t)
            }
        }
        webSocket = OkHttpClient().newWebSocket(request, webSocketListener)
    }

    fun closeSocket() {
        webSocket?.cancel()
    }
}
