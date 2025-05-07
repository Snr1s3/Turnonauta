package proj.tcg.turnonauta.socket

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket
import java.net.SocketException

class ClientSocket {
    private val host = "52.20.160.197"
    private val port = 8444
    private lateinit var socket: Socket
    private var listenJob: Job? = null
    private var output: OutputStream? = null
    private var input: BufferedReader? = null

    var isConnected = false
        private set

    fun connect(context: Context, nomPlayer: String,torneigID:Int,PlayerID:Int, onMessageReceived: (List<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                socket = Socket(host, port)
                input = BufferedReader(InputStreamReader(socket.getInputStream()))
                output = socket.getOutputStream()
                if(!isConnected){
                    //0.torniegID.PlayerID.nomPlayer
                    output?.write("0.$torneigID.$PlayerID.$nomPlayer".toByteArray())
                    output?.flush()
                }

                val serverMessage = input?.readLine()
                isConnected = true

                if (!serverMessage.isNullOrEmpty()) {
                    Handler(Looper.getMainLooper()).post {
                        val parts = serverMessage.split(".")
                        if (parts.isNotEmpty()) {
                            onMessageReceived(parts)
                        }
                        Toast.makeText(context, "Server: $serverMessage", Toast.LENGTH_SHORT).show()
                    }
                }

                // Start listening continuously
                listenJob = CoroutineScope(Dispatchers.IO).launch {
                    while (socket.isConnected && !socket.isClosed) {
                        val message = input?.readLine()
                        if (message != null) {
                            Log.d("ClientSocket", "Server: $message")
                            val parts = message.split(".")
                            if (parts.isNotEmpty()) {
                                Handler(Looper.getMainLooper()).post {
                                    onMessageReceived(parts)
                                }
                            }
                        } else {
                            Log.w("ClientSocket", "Disconnected from server")
                            break
                        }
                    }
                    isConnected = false
                }

            } catch (e: Exception) {
                isConnected = false
                Log.e("ClientSocket", "Error: ${e.message}")
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Socket Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun sendMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                output?.write(message.toByteArray())
                output?.flush()
            } catch (e: Exception) {
                Log.e("ClientSocket", "Send failed: ${e.message}")
            }
        }
    }
    fun sendInitMessage(torneigId: Int, userId: Int, playerName: String) {
        val message = "0.$torneigId.$userId.$playerName\n"
        sendMessage(message)
    }


    fun disconnect() {
        if (::socket.isInitialized && !socket.isClosed) {
            try {
                listenJob?.cancel()
                socket.close()
                isConnected = false
                Log.d("ClientSocket", "Socket closed")
            } catch (e: Exception) {
                Log.e("ClientSocket", "Error closing socket: ${e.message}")
            }
        }
    }
}
