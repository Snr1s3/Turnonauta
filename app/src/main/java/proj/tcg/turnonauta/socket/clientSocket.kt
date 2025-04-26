package proj.tcg.turnonauta.socket

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException

class clientSocket {
    private val host = "52.20.160.197"
    private val port = 8444
    private lateinit var socket: Socket
    private var listenJob: Job? = null

    fun main(context: Context, onMessageReceived: (List<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                socket = Socket(host, port)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                val output: OutputStream = socket.getOutputStream()

                output.write("0.3.453ws2.plaeyer3".toByteArray())
                output.flush()

                // Read the initial response
                var serverMessage = input.readLine()
                if (!serverMessage.isNullOrEmpty()) {
                    Handler(Looper.getMainLooper()).post {
                        val parts = serverMessage.split(".")
                        if (parts.isNotEmpty() && parts[0] == "1") {
                            onMessageReceived(parts)
                        }
                        Toast.makeText(context, "Server: $serverMessage", Toast.LENGTH_SHORT).show()
                    }
                }

                // Start listening continuously
                listenJob = CoroutineScope(Dispatchers.IO).launch {
                    while (socket.isConnected && !socket.isClosed) {
                        val message = input.readLine()
                        if (message != null) {
                            Log.d("SocketClient", "Server: $message")
                            val parts = message.split(".")
                            if (parts.isNotEmpty() && parts[0] == "1") {
                                Handler(Looper.getMainLooper()).post {
                                    onMessageReceived(parts)
                                }
                            }
                        } else {
                            Log.w("SocketClient", "Connection closed by server")
                            break
                        }
                    }
                }
            } catch (e: SocketException) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Socket Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.e("SocketClient", "Socket error: ${e.message}")
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.e("SocketClient", "Error: ${e.message}")
            }
        }
    }

    fun closeSocket() {
        if (::socket.isInitialized && !socket.isClosed) {
            try {
                socket.close()
                listenJob?.cancel()
                Log.d("SocketClient", "Socket closed successfully")
            } catch (e: Exception) {
                Log.e("SocketClient", "Error closing socket: ${e.message}")
            }
        }
    }
}
