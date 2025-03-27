package proj.tcg.turnonauta.app

import android.app.Application


class AppTurnonauta : Application() {

    private var userId: Int = 0
    private var torneigId: Int = 0

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
    fun setTorneigIdApp(response: Int) {
        torneigId = response
    }

    fun getTorneigIdApp(): Int {
        return torneigId
    }
}