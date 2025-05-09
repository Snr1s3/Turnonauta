package proj.tcg.turnonauta.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import proj.tcg.turnonauta.socket.ClientSocket

class AppTurnonauta : Application() {

    private var userId: Int = 0
    private var torneigId: Int = 0
    private var playerName: String = ""
    private var rondes: Int = 1
    private val clientSocket = ClientSocket()


    companion object {
        private var instance: AppTurnonauta? = null

        fun getInstance(): AppTurnonauta {
            return instance ?: throw IllegalStateException("Application instance not initialized")
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this

        val prefs = getSharedPreferences("ajustes", MODE_PRIVATE)
        val isNightMode = prefs.getBoolean("modo_noche", false)

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    fun getSocket(): ClientSocket {
        return clientSocket
    }

    fun setUserIdApp(response: Int) {
        userId = response
    }

    fun getUserIdApp(): Int {
        return userId
    }

    fun setPlayerNameApp(response: String) {
        playerName = response

    }

    fun getPlayerNameApp(): String {
        return playerName
    }

    fun setTorneigIdApp(response: Int) {
        torneigId = response
    }

    fun getTorneigIdApp(): Int {
        return torneigId
    }
    fun getNumRonda() : Int {
        return rondes
    }
    fun setNumRonda() {
        rondes = rondes + 1

    }
}