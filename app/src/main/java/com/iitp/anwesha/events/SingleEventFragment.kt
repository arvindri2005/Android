package com.iitp.anwesha.events

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class SingleEventFragment : Fragment() {
    private lateinit var binding: FragmentSingleEventBinding
    private lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSingleEventBinding.inflate(inflater, container, false)

        if (isAdded) {
            val event = arguments?.getSerializable("event") as EventList


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
//            binding.eventStartTime.text = separatedStrings[1]

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
                    if(event.is_online!!){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.registration_link))
                        startActivity(intent)
                    }
                    else{
                        if(event.is_solo){

                            pay(event.registration_fee!!)

                        }
                        else {
                            val minTeamMembers = event.min_team_size
                            val maxTeamMembers = event.max_team_size
                            val bundle = Bundle()
                            bundle.putInt("minTeamMembers", minTeamMembers!!)
                            bundle.putInt("maxTeamMembers", maxTeamMembers!!)
                            bundle.putString("eventName", event.name)
                            bundle.putString("eventID", event.id)
                            bundle.putString("eventFee", event.registration_fee!!)
                            val teamEventFragment = TeamEventFragment()
                            teamEventFragment.arguments = bundle
                            val fragmentManager = requireActivity().supportFragmentManager.beginTransaction()
                            fragmentManager.addToBackStack(null)
                            fragmentManager.replace(R.id.fragmentContainer, teamEventFragment)
                            fragmentManager.commit()
                        }

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
        Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show()
        val newPayIntent = Intent(requireContext(), PayActivity::class.java)
        newPayIntent.putExtra("merchantId", "564719")
        newPayIntent.putExtra("password", "b5d2bc5e")
        newPayIntent.putExtra("prodid", "STUDENT")
        newPayIntent.putExtra("txncurr", "INR")
        newPayIntent.putExtra("custacc", "100000036600")
        newPayIntent.putExtra("amt", price)
        newPayIntent.putExtra("txnid", "txnfeb2023")
        newPayIntent.putExtra("signature_request", "KEY1234567234")
        newPayIntent.putExtra("signature_response", "KEYRESP123657234")
        newPayIntent.putExtra("enc_request", "1E67285F56177ADD96D6453F90482D12")
        newPayIntent.putExtra("salt_request", "1E67285F56177ADD96D6453F90482D12")
        newPayIntent.putExtra("salt_response", "66F34D46E547C535047F3465E640F32B")
        newPayIntent.putExtra("enc_response", "66F34D46E547C535047F3465E640F32B")
        newPayIntent.putExtra("isLive", true)
        newPayIntent.putExtra("custFirstName", "test user")
        newPayIntent.putExtra("customerEmailID", "test@gmail.com")
        newPayIntent.putExtra("customerMobileNo", "8888888888")
        newPayIntent.putExtra("udf1", "udf1")
        newPayIntent.putExtra("udf2", "udf2")
        newPayIntent.putExtra("udf3", "udf3")
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
                Toast.makeText(requireContext(), "Transaction Successful! \n" + data.extras!!.getString("response"), Toast.LENGTH_LONG).show()
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.d("log","128")
                        val response1 = EventsRegistrationApi(requireContext()).allEventsApi.soloEventRegistration(SoloRegistration(eventId))
                        Log.d("response", "130")
                        if(response1.isSuccessful){
                            requireActivity().runOnUiThread {
                                Toast.makeText(requireContext(), "Registered Successfully", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Log.d("e", "${response1.errorBody()}")
                        }
                    }
                    catch (e: Exception){
                        Log.d("Error", e.toString())
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Transaction Failed! \n" + data.extras!!.getString("response"),
                    Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Toast.makeText(requireContext(), "Transaction Cancelled!", Toast.LENGTH_LONG).show()
        }
    } // onActivityResult

}