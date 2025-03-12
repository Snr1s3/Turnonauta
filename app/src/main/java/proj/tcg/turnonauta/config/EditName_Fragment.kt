package proj.tcg.turnonauta.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import proj.tcg.turnonauta.R

class EditName_Fragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_name_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    dismiss()
                }
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}
