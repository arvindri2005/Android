package com.iitp.anwesha.events

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.atom.atompaynetzsdk.PayActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.FragmentSingleEventBinding
import com.iitp.anwesha.home.EventList
import com.iitp.anwesha.profile.UserProfileApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random
import java.util.TimeZone

private const val TAG = "SingleEventFragment"
class SingleEventFragment : Fragment() {
    private lateinit var binding: FragmentSingleEventBinding
    private lateinit var eventId: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var anweshaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onResume() {

        super.onResume()

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.visibility = View.GONE

    }

    override fun onDestroy() {

        super.onDestroy()

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView?.visibility = View.VISIBLE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSingleEventBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.IO).launch {

            val response = UserProfileApi(requireContext()).profileApi.getProfile()

            if (response.isSuccessful) {

                val userInfo = response.body()!!
                Log.d(TAG, "User Profile Response: ${response.body()}")

                withContext(Dispatchers.Main) {
                    name = userInfo.full_name
                    email = userInfo.email_id
                    phone = userInfo.phone_number
                    anweshaId = userInfo.anwesha_id
                }

            } else {

                withContext(Dispatchers.Main) {

                    Log.d(TAG, "Error in User Profile Response: ${response.message()}")
                    Toast.makeText(context, "error found: ${response.message()}", Toast.LENGTH_SHORT).show()

                }
            }
        }

        if (isAdded) {

            val event = arguments?.getSerializable("event") as EventList
            Log.d(TAG, "Event: $event")

            Glide.with(requireContext())
                .load(event.poster)
                .into(binding.eventPoster)

            binding.eventName.text = event.name
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEE dd MMMM yyyy, HH:mm", Locale.getDefault())
            val outputFormat1 = SimpleDateFormat("dd MMMM", Locale.getDefault())
            val outputFormat2 = SimpleDateFormat("dd", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC+5:30") // set input timezone to UTC
            outputFormat.timeZone = TimeZone.getDefault() // set output timezone to default timezone
            val startTimeString =
                outputFormat2.format(inputFormat.parse(event.start_time!!)!!) // format date object into output string
            val endDateString = outputFormat1.format(inputFormat.parse(event.end_time!!)!!)
            val startTimeSeparatedStrings = startTimeString.split(",")
            val endTimeSeparatedString1 = endDateString.split(",")
            binding.eventDate.text =
                startTimeSeparatedStrings[0] + " - " + endTimeSeparatedString1[0]

            binding.eventDescription.text = event.description
            if (event.is_solo!!) {
                binding.teamSize.text = "Individual Participant"
            } else {
                binding.teamSize.text = "${event.min_team_size}-${event.max_team_size} Peoples"
            }

            eventId = event.id.toString()
            binding.registrationFee.text = "₹" + event.registration_fee

            if (event.registration_deadline==null){
                binding.registerDeadline.visibility = View.GONE
            }

            else{
                val endTime = event.registration_deadline
                val endDate = inputFormat.parse(endTime)!!
                val endTimeString = outputFormat.format(endDate)
                val endTimeSeparatedString = endTimeString.split(",").map { it.trim() }
                binding.registerDeadline.text = endTimeSeparatedString[0]
            }


            binding.eventLocation.text = event.venue

            val organizerT = event.organizer!!
            var organizer = ""
            for (string in organizerT) {
                organizer = organizer+string[0] + " " +string[1]  +"\n"
            }

            binding.organizer.text = organizer


            binding.prize.text = "Prizes worth ₹${event.prize}"


            if(!event.is_active!!){
                binding.registerBtn.visibility = View.GONE
            }

            binding.registerBtn.setOnClickListener {
                if(event.is_active){

                    if(event.is_solo){

                        Log.d(TAG, "Solo Event Registration")
                        pay(event.registration_fee!!)

                    }

                    else {

                        val bundle = Bundle()
                        bundle.putInt("minTeamMembers", event.min_team_size!!)
                        bundle.putInt("maxTeamMembers", event.max_team_size!!)
                        bundle.putString("eventName", event.name)
                        bundle.putString("eventID", event.id)
                        bundle.putString("eventFee", event.registration_fee!!)

                        Log.d(TAG, "Starting Team Event Registration")
                        val teamEventFragment = TeamEventFragment()
                        teamEventFragment.arguments = bundle
                        val fragmentManager = requireActivity().supportFragmentManager.beginTransaction()
                        fragmentManager.addToBackStack(null)
                        fragmentManager.replace(R.id.fragmentContainer, teamEventFragment)
                        fragmentManager.commit()

                    }
                }
            }

            if (event.video!!.isEmpty()) {

                binding.rulebookBtn.visibility = View.GONE

            }

            binding.rulebookBtn.setOnClickListener {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.video))
                startActivity(intent)

            }

        }
        binding.backBtn.setOnClickListener {

            requireActivity().supportFragmentManager.popBackStack(null, 0)

        }

        return binding.root
    }

    private fun pay(price: String){

        val transactionId = generateTransactionId()
        Log.d(TAG, "Transition ID: $transactionId")

        Toast.makeText(requireContext(), "Please Wait", Toast.LENGTH_SHORT).show()

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
    }

    private fun registerEvent(){
        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response1 = EventsRegistrationApi(requireContext()).allEventsApi.soloEventRegistration(SoloRegistration(eventId))

                if(response1.isSuccessful){

                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Registered Successfully", Toast.LENGTH_SHORT).show()
                    }
                }

                else{
                    Log.d(TAG, "${response1.errorBody()}")
                }
            }

            catch (e: Exception){
                Log.d(TAG, e.toString())
            }
        }
    }
}

fun generateTransactionId(): String {
    val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
    return (1..12)
        .map { Random().nextInt(chars.length) }
        .map(chars::get)
        .joinToString("")
}