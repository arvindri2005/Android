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
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.FragmentTeamEventBinding
import com.iitp.anwesha.databinding.TeamMemberFieldBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.HashSet


private const val ARG_PARAM1 = "minTeamMembers"
private const val ARG_PARAM2 = "maxTeamMembers"
private const val ARG_PARAM3 = "eventName"
private const val ARG_PARAM4 = "eventID"


class TeamEventFragment : Fragment() {

    private var minTeamMembers: Int? = null
    private var maxTeamMembers: Int? = null
    private var eventName: String? = null
    private var eventID: String? = null
    private lateinit var binding: FragmentTeamEventBinding
    private var counter: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            minTeamMembers = it.getInt(ARG_PARAM1)
            maxTeamMembers = it.getInt(ARG_PARAM2)
            eventName = it.getString(ARG_PARAM3)
            eventID = it.getString(ARG_PARAM4)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeamEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.teamEventName.text = eventName

        for (i in 1..minTeamMembers!!-1) {
            val teamMember = layoutInflater.inflate(R.layout.team_member_field, null)
//            val teamMember = View.inflate(context, R.layout.team_member_field, binding.teamMembers)
            teamMember.findViewById<ImageView>(R.id.delete_team_member).visibility = View.INVISIBLE
            teamMember.findViewById<TextView>(R.id.team_member_index).text = counter.toString()
//            if(teamMember.parent != null) {
//                (teamMember.parent as ViewGroup).removeView(teamMember)
//            }
            counter++
            binding.teamMembers.addView(teamMember)
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
                val members = binding.teamMembers.childCount
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
            val teamList = arrayListOf<String>()
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
            CoroutineScope(Dispatchers.IO).launch {

                val teamRegistration =
                    TeamRegistration(eventID!!, binding.teamName.text.toString(), teamList)
                val response =
                    EventsRegistrationApi(requireContext()).allEventsApi.teamEventRegistration(
                        teamRegistration
                    )
                if (response.isSuccessful) {
                    val teamReg = response.body()!!
                    requireActivity().runOnUiThread {
                        if (teamReg.message == null) {
                            Toast.makeText(
                                requireContext(),
                                "Already resgisterd",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@runOnUiThread
                        } else if (teamReg.payment_url==null) {

                            Toast.makeText(requireContext(), teamReg.message, Toast.LENGTH_SHORT)
                                .show()
                        } else {

                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(teamReg.payment_url))
                            val headers = Bundle()
                            val sharedPref = requireActivity().getSharedPreferences(
                                "UserPreferences",
                                Context.MODE_PRIVATE
                            )
                            var cookieString = ""
                            for (cookie in sharedPref.getStringSet(
                                getString(R.string.cookies),
                                HashSet()
                            )!!) {
                                cookieString += "$cookie; "
                            }
                            headers.putString("Set-Cookie", cookieString)
                            intent.putExtra(Browser.EXTRA_HEADERS, headers)
                            startActivity(intent)
                        }
                    }
                }
                else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Invalid details ${response.errorBody()}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }


    }
}