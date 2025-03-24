package proj.tcg.turnonauta.app

import android.app.Application
import android.content.Context


class AppTurnonauta : Application() {

    var userId: Int = 0

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
}