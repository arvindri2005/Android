package com.iitp.anwesha.events

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import com.atom.atompaynetzsdk.PayActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.FragmentTeamEventBinding
import com.iitp.anwesha.databinding.TeamMemberFieldBinding
import com.iitp.anwesha.profile.UserProfileApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashSet


private const val ARG_PARAM1 = "minTeamMembers"
private const val ARG_PARAM2 = "maxTeamMembers"
private const val ARG_PARAM3 = "eventName"
private const val ARG_PARAM4 = "eventID"
private const val ARG_PARAM5 = "eventFee"
private const val TAG = "TeamEventFragment"


class TeamEventFragment : Fragment() {

    private var minTeamMembers: Int? = null
    private var maxTeamMembers: Int? = null
    private var eventName: String? = null
    private var eventID: String? = null
    private var eventFee: String? = null
    private lateinit var binding: FragmentTeamEventBinding
    private var counter: Int = 1
    private lateinit var teamList: ArrayList<String>
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var anweshaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            minTeamMembers = it.getInt(ARG_PARAM1)
            maxTeamMembers = it.getInt(ARG_PARAM2)
            eventName = it.getString(ARG_PARAM3)
            eventID = it.getString(ARG_PARAM4)
            eventFee= it.getString(ARG_PARAM5)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTeamEventBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.IO).launch {

            val response = UserProfileApi(requireContext()).profileApi.getProfile()

            if (response.isSuccessful) {

                val userInfo = response.body()!!
                Log.d(TAG,"User info: $userInfo")

                withContext(Dispatchers.Main) {

                    name = userInfo.full_name
                    email = userInfo.email_id
                    phone = userInfo.phone_number
                    anweshaId = userInfo.anwesha_id

                }
            } else {
                withContext(Dispatchers.Main) {

                    Log.d(TAG,"Error in getting profile info: ${response.message()}")
                    Toast.makeText(context, "error found: ${response.message()}", Toast.LENGTH_SHORT).show()

                }
            }
        }
        return binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.teamEventName.text = eventName


        for (i in 1..minTeamMembers!!) {

            val teamMember = layoutInflater.inflate(R.layout.team_member_field, null)
            teamMember.findViewById<ImageView>(R.id.delete_team_member).visibility = View.INVISIBLE
            teamMember.findViewById<TextView>(R.id.team_member_index).text = counter.toString()
            counter++
            binding.teamMembers.addView(teamMember)

        }

        binding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, 0)
        }

        binding.addMembers.setOnClickListener {

            if (counter >= maxTeamMembers!!) {
                Toast.makeText(context, "Max participant!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val teamMember = layoutInflater.inflate(R.layout.team_member_field, null)
            teamMember.findViewById<ImageView>(R.id.delete_team_member).setOnClickListener {

                binding.teamMembers.removeView(teamMember)
                counter--

                var i = 1
                for (child in binding.teamMembers.children) {
                    child.findViewById<TextView>(R.id.team_member_index).text = i.toString()
                    i++
                }

            }

            teamMember.findViewById<TextView>(R.id.team_member_index).text = counter.toString()
            binding.teamMembers.addView(teamMember)
            counter++
        }

        binding.teamRegister.setOnClickListener {

            teamList = arrayListOf<String>()
            var count = 0

            for (child in binding.teamMembers.children) {
                val memberName = child.findViewById<EditText>(R.id.team_member_id).text.toString()
                if(memberName.isNotEmpty()) count++
                teamList.add(memberName)
            }

            if(count < minTeamMembers!! || binding.teamName.text.toString().isEmpty()) {
                Toast.makeText(context, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pay(eventFee!!)
        }
    }
    private fun pay(price: String){

        val transactionId = generateTransactionId()
        Log.d(TAG, "Transaction id: $transactionId")

        Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show()

        val newPayIntent = Intent(requireContext(), PayActivity::class.java)
        newPayIntent.putExtra("merchantId", "564719")
        newPayIntent.putExtra("password", "b5d2bc5e")
        newPayIntent.putExtra("prodid", "STUDENT")
        newPayIntent.putExtra("txncurr", "INR")
        newPayIntent.putExtra("custacc", "100000036600")
        newPayIntent.putExtra("amt", price)
        newPayIntent.putExtra("txnid", transactionId)
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
        newPayIntent.putExtra("eventId", eventID)
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

                Log.d(TAG, "Transaction Successful! \n" + data.extras!!.getString("response"))
                Toast.makeText(requireContext(), "Transaction Successful!", Toast.LENGTH_LONG).show()

                registerEvent()

            } else {

                Log.d(TAG, "Transaction Failed! \n" + data.extras!!.getString("response"))
                Toast.makeText(requireContext(), "Transaction Failed!", Toast.LENGTH_LONG).show()

            }
        } else {

            Log.d(TAG, "Transaction Cancelled!")
            Toast.makeText(requireContext(), "Transaction Cancelled!", Toast.LENGTH_LONG).show()

        }
    } // onActivityResult

    private fun registerEvent() {

        CoroutineScope(Dispatchers.IO).launch {
            try {

                CoroutineScope(Dispatchers.IO).launch {

                    val teamRegistration = TeamRegistration(eventID!!, binding.teamName.text.toString(), teamList)
                    Log.d(TAG, "Team registration: $teamRegistration")

                    val response = EventsRegistrationApi(requireContext()).allEventsApi.teamEventRegistration(teamRegistration)

                    if (response.isSuccessful) {
                        val teamReg = response.body()!!
                        requireActivity().runOnUiThread {
                            if (teamReg.message == null) {
                                Toast.makeText(requireContext(), "Already resgisterd", Toast.LENGTH_SHORT).show()
                                return@runOnUiThread

                            } else {
                                Toast.makeText(requireContext(), "Registered successfully", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else {
                        requireActivity().runOnUiThread {
                            Toast.makeText(context, "Invalid details", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            catch (e: Exception){
                Log.d(TAG, e.toString())
                requireActivity().runOnUiThread {
                    Snackbar.make(requireView(), "Oops Some problem Occurred", Snackbar.LENGTH_LONG)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE).show()
                }
            }
        }
    }
}