package proj.tcg.turnonauta.config

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import proj.tcg.turnonauta.R
import proj.tcg.turnonauta.app.AppTurnonauta
import proj.tcg.turnonauta.models.UpdateNameRequest
import proj.tcg.turnonauta.models.UsuarisStatistics
import proj.tcg.turnonauta.retrofit.ConnexioAPI
import retrofit2.HttpException
import java.io.IOException

class EditName_Fragment : BottomSheetDialogFragment() {

    private var userId: Int = 0
    private lateinit var response: UsuarisStatistics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        return inflater.inflate(R.layout.edit_name_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appInstance = AppTurnonauta.getInstance()
        userId = appInstance.getUserIdApp()
        getDataUser()

        val editText = view.findViewById<EditText>(R.id.editName)
        val errorTextView = view.findViewById<TextView>(R.id.tConObl)
        val btnSave = view.findViewById<Button>(R.id.btnSave)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        btnSave.setOnClickListener {
            val newName = editText.text.toString().trim()

            when {
                newName.length > 14 -> {
                    errorTextView.text = "El nom a de contenir maxim 14 caracters"
                    errorTextView.visibility = View.VISIBLE
                }
                newName.equals("pau", ignoreCase = true) || newName.equals("hola", ignoreCase = true) -> {
                    errorTextView.text = "Aquet nom de usuari ja existeix"
                    errorTextView.visibility = View.VISIBLE
                }
                else -> {
                    errorTextView.visibility = View.INVISIBLE
                    val updateNameRequest = UpdateNameRequest(newName)

                    lifecycleScope.launch {
                        try {
                            val updatedUser = ConnexioAPI.api().updateUserName(userId, updateNameRequest)

                            // Mostrar mensaje de éxito
                            Toast.makeText(requireContext(), "Nombre de usuario actualizado", Toast.LENGTH_SHORT).show()
                            dismiss()

                        } catch (e: HttpException) {
                            // Mostrar error en Logcat
                            Log.e("EditName_Fragment", "HTTP Error: ${e.message}", e)
                            Toast.makeText(requireContext(), "Error HTTP: ${e.message}", Toast.LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            // Mostrar error en Logcat
                            Log.e("EditName_Fragment", "Network Error: ${e.message}", e)
                            Toast.makeText(requireContext(), "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            // Mostrar error en Logcat
                            Log.e("EditName_Fragment", "Error inesperado: ${e.message}", e)
                            Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }




        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun getDataUser(){
        lifecycleScope.launch {
            try {
                response = ConnexioAPI.api().getStatistic(userId)
                Log.d("User_ID ID del user:", "ID: "+response)
                val nomText = view?.findViewById<EditText>(R.id.editName)
                nomText?.hint = response.username

            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("EditName_Fragment", "Error inesperado: ${e.message}", e)

                Toast.makeText(requireContext(),"Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
