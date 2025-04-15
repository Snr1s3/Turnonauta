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
import proj.tcg.turnonauta.aplicacio.PreviewTorneig
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

    fun main(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Initialize the socket connection
                socket = Socket(host, port)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                val output: OutputStream = socket.getOutputStream()

                // Send data to the server
                output.write("0.3.4532.player3".toByteArray())
                output.flush()

                val serverMessage = input.readLine()

                // Post server response to main thread
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Server says: $serverMessage", Toast.LENGTH_LONG).show()
                    val parts = serverMessage.split(".")
                    if (parts.isNotEmpty() && parts[0] == "1") {
                        // Make sure PreviewTorneig is properly imported and the method is defined
                        val pr = PreviewTorneig()
                        pr.startRecycled(parts)
                    }
                }
                Log.d("Retrofit", "server: $serverMessage")

                // Listening for server messages in a separate coroutine
                listenJob = CoroutineScope(Dispatchers.IO).launch {
                    while (socket.isConnected && !socket.isClosed) {
                        val message = input.readLine()
                        if (message != null) {
                            Handler(Looper.getMainLooper()).post {
                                // Uncomment this line to show server message in a Toast
                                Toast.makeText(context, "Server: $message", Toast.LENGTH_SHORT).show()

                            }

                            Log.d("SocketClient", "Server: $message")
                        } else {
                            Log.w("SocketClient", "Server closed connection")
                            break
                        }
                    }
                }
            } catch (e: SocketException) {
                // Handle socket exceptions
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Socket Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.e("SocketClient", "Socket error: ${e.message}")
            } catch (e: Exception) {
                // Handle general exceptions
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.e("SocketClient", "Error: ${e.message}")
            }
        }
    }

    // Gracefully close the socket
    fun closeSocket() {
        // Ensure the socket is initialized and is not already closed
        if (::socket.isInitialized && !socket.isClosed) {
            try {
                socket.close()
                listenJob?.cancel()  // Cancel the listening job if it is still running
                Log.d("SocketClient", "Socket closed successfully")
            } catch (e: Exception) {
                Log.e("SocketClient", "Error closing socket: ${e.message}")
            }
        }
    }
}
