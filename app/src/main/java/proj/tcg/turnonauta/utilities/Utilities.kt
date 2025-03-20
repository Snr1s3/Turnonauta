package proj.tcg.turnonauta.utilities

import androidx.fragment.app.FragmentManager
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.fragments.BottomMenu
class Utilities(private val fragmentManager: FragmentManager) {

    fun loadFragment(userId: Int, containerId: Int) {
        val fragment = BottomMenu.newInstance(userId)
        fragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
}