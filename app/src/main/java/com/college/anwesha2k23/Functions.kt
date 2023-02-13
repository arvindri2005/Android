package com.college.anwesha2k23

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class MyDialog(val context: Context) {

    val builder = MaterialAlertDialogBuilder(context)
    lateinit var dialog : androidx.appcompat.app.AlertDialog

    fun showProgressDialog(fragment : Fragment, msg: String? = "Please Wait") {
        val view =  fragment.layoutInflater.inflate(R.layout.dialog_progress,null,false)
        builder.setCancelable(false)
        val progressTextview = view.findViewById<TextView>(R.id.tv_progress_text)
        if (msg != null)
            progressTextview.text = msg
         else
            progressTextview.visibility = View.GONE
        builder.setView(view)
        dialog =   builder.show()
    }

    fun showProgressDialogForActivity(msg: String? = null,activity : Activity) {
        val view =  activity.layoutInflater.inflate(R.layout.dialog_progress,null,false)
        builder.setCancelable(false)
        val progressTextview = view.findViewById<TextView>(R.id.tv_progress_text)
        if (msg != null)
            progressTextview.text = msg
         else
            progressTextview.visibility = View.GONE
        builder.setView(view)
        dialog =   builder.show()
    }

    fun showConfirmationDialog(activity : Context, title : String, array: Array<String>, checkedItem : Int, textView : TextView){
        MaterialAlertDialogBuilder(activity)
            .setTitle(title)
            .setSingleChoiceItems(array,checkedItem){dialog,which ->
                textView.text = array[which]
            }
            .setPositiveButton("OK",null)
            .setNegativeButton("CANCEL",null)
            .show()
    }

    fun showErrorAlertDialog(msg : String?,listener : DialogInterface.OnClickListener? = null) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Something went wrong")
            .setMessage(msg)
            .setPositiveButton("OK",listener)
            .show()
    }


    fun dismissProgressDialog() {
        dialog.dismiss()
    }

}

 fun checkValue(input: TextInputLayout): String? {
    val value = input.editText?.text.toString()
    if(value.isBlank()) {
        input.error = "Required Field!"
        return null
    }
    input.error = null
    return value
}

 fun checkValue(input: EditText): String? {
    val value = input.text.toString()
    if(value.isBlank()) {
        input.error = "Required Field!"
        return null
    }
    input.error = null
    return value
}