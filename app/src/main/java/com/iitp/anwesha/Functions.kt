package com.iitp.anwesha

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import okhttp3.Interceptor

import okhttp3.Response
import java.io.IOException



const val BASE_URL = "https://anweshabackend.xyz/"
class MyDialog(val context: Context) {

    private val builder = MaterialAlertDialogBuilder(context)
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
            .setSingleChoiceItems(array,checkedItem){ _, which ->
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



class AddCookiesInterceptor(val context: Context) : Interceptor {

    private val sharedPref: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        for (cookie in sharedPref.getStringSet(context.getString(R.string.cookies), HashSet())!!) {
            builder.addHeader("Cookie", cookie)
            Log.v(
                "OkHttp",
                "Adding Header: $cookie"
            ) // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }

        return chain.proceed(builder.build())
    }
}

class ReceivedCookiesInterceptor(val context: Context) : Interceptor {

    private val sharedPref: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }

            with(sharedPref.edit()) {
                putStringSet(context.getString(R.string.cookies), cookies)
                apply()
            }
        }
        return originalResponse
    }
}

