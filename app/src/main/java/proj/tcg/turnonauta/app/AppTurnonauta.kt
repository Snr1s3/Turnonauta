package proj.tcg.turnonauta.app

import android.app.Application


class AppTurnonauta : Application() {

    private var userId: Int = 0
    private var torneigId: Int = 0
    private var playerName: String = ""
    private var rondes: Int = 1

    companion object {
        private var instance: AppTurnonauta? = null

        fun getInstance(): AppTurnonauta {
            return instance ?: throw IllegalStateException("Application instance not initialized")
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
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