package proj.tcg.turnonauta.socket

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class clientSocket {
    private val host = "52.20.160.197"
    private val port = 8444
    private  lateinit var  socket: Socket
    fun main(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                socket = Socket(host, port)
                val input = BufferedReader(InputStreamReader(socket.getInputStream()))
                val output: OutputStream = socket.getOutputStream()
                // Send data to the server
                output.write("3:2".toByteArray())
                output.flush()
                val serverMessage = input.readLine()
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Server says: $serverMessage", Toast.LENGTH_LONG).show()
                }
                Log.d("Retrofit", serverMessage)
            } catch (e: Exception) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.d("Retrofit", e.toString())
            }
        }
    }
    fun sonClose(){
        if(socket !=null){
            socket.close()
        }
    }
}