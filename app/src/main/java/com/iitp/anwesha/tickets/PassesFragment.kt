package com.iitp.anwesha.tickets

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import app.rive.runtime.kotlin.RiveAnimationView
import com.atom.atompaynetzsdk.PayActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.FragmentElitePassBinding
import com.iitp.anwesha.databinding.FragmentPassesBinding
import com.iitp.anwesha.databinding.FragmentProPassBinding
import com.iitp.anwesha.events.EventsRegistrationApi
import com.iitp.anwesha.events.SoloRegistration
import com.iitp.anwesha.events.generateTransactionId
import com.iitp.anwesha.home.EventList
import com.iitp.anwesha.profile.UserProfileApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Math.abs

const val TAG = "PassesFragment"

class PassesFragment : Fragment() {

    private lateinit var binding: FragmentPassesBinding
    private lateinit var email: String
    private lateinit var eventId: String
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var anweshaId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPassesBinding.inflate(inflater, container, false)
        val view = binding.root

        val userType = arguments?.getString("eventID").toString()

        eventId = if(userType=="iitp_student"){
                "EVTe96c6" //General Pronite Pass for iitp
            }else{
                "EVT7a8a7" //General Pronite Pass for outsiders
            }
        Log.d(TAG, "Event Id: $eventId")

        CoroutineScope(Dispatchers.IO).launch {

            val response = UserProfileApi(requireContext()).profileApi.getProfile()
            if (response.isSuccessful) {

                val userInfo = response.body()!!
                Log.d(TAG, "UserInfo: $userInfo")

                withContext(Dispatchers.Main) {
                    name = userInfo.full_name
                    email = userInfo.email_id
                    phone = userInfo.phone_number
                    anweshaId = userInfo.anwesha_id
                }

            } else {
                Log.d(TAG, "Error found while getting profile info")
                Log.d(TAG, "Error ${response.message()}")
            }
        }

        binding.cilcker.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                if(userType=="iitp_student"){
                    val isEmailInList = blackListUsers.contains(email)
                    Log.d(TAG, "Is Email blackListed: $isEmailInList")

                    if(isEmailInList){
                        pay("699")
                    }
                    else {
                        purchasePass()
                    }
                }
                else{
                    pay("1599")
                }
            }

        }

        binding.getBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, 0)
        }

        return view
    }

    private fun pay(price: String){

        val transactionId = generateTransactionId()
        Log.d(TAG, "TransactionId: $transactionId ",)

        requireActivity().runOnUiThread {
            Toast.makeText(context, "Please Wait", Toast.LENGTH_SHORT).show()
        }

        val newPayIntent = Intent(requireContext(), PayActivity::class.java)
        newPayIntent.putExtra("merchantId", "564719")
        newPayIntent.putExtra("password", "b5d2bc5e")
        newPayIntent.putExtra("prodid", "STUDENT")
        newPayIntent.putExtra("txncurr", "INR")
        newPayIntent.putExtra("custacc", "100000036600")
        newPayIntent.putExtra("amt", price)
        newPayIntent.putExtra("txnid",transactionId)
        newPayIntent.putExtra("signature_request", "KEY1234567234")
        newPayIntent.putExtra("signature_response", "KEYRESP123657234")
        newPayIntent.putExtra("enc_request", "1E67285F56177ADD96D6453F90482D12")
        newPayIntent.putExtra("salt_request", "1E67285F56177ADD96D6453F90482D12")
        newPayIntent.putExtra("salt_response", "66F34D46E547C535047F3465E640F32B")
        newPayIntent.putExtra("enc_response", "66F34D46E547C535047F3465E640F32B")
        newPayIntent.putExtra("isLive", true)
        newPayIntent.putExtra("custFirstName", name)
        newPayIntent.putExtra("customerEmailID", email)
        newPayIntent.putExtra("customerMobileNo", phone)
        newPayIntent.putExtra("AnweshaId", anweshaId)
        newPayIntent.putExtra("eventId", eventId)
        newPayIntent.putExtra("transactionId", transactionId)
        newPayIntent.putExtra("udf4", "udf4")
        newPayIntent.putExtra("udf5", "udf5")

        startActivityForResult(newPayIntent, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        println("resultCode = $resultCode")
        println("onActivityResult data = $data")

        if (data != null && resultCode != 2) {

            println("ArrayList data = " + data.extras!!.getString("response"))

            if (resultCode == 1) {

                Log.d(TAG, "Transaction Successful")
                Toast.makeText(requireContext(), "Transaction Successful! \n" + data.extras!!.getString("response"), Toast.LENGTH_LONG).show()

                purchasePass()

            } else {
                Log.d(TAG, "Transaction Failed")
                Toast.makeText(requireContext(), "Transaction Failed! \n" + data.extras!!.getString("response"), Toast.LENGTH_LONG).show()
            }
        } else {
            Log.d(TAG, "Transaction Cancelled")
            Toast.makeText(requireContext(), "Transaction Cancelled!", Toast.LENGTH_LONG).show()
        }
    }

    private fun purchasePass(){
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response1 = EventsRegistrationApi(requireContext()).allEventsApi.soloEventRegistration(
                    SoloRegistration(eventId)
                )

                if(response1.isSuccessful){
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Pass Purchased Successfully", Toast.LENGTH_SHORT).show()
                    }
                }

                else{
                    Log.d(TAG, "Error in registering in event ${response1.errorBody()}")
                }
            }
            catch (e: Exception){
                Log.d(TAG, e.toString())
            }
        }
    }
    override fun onResume() {
        super.onResume()
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.visibility = View.VISIBLE
    }
}