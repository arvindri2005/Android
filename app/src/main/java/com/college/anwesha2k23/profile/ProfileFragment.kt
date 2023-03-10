package com.college.anwesha2k23.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.college.anwesha2k23.databinding.FragmentProfileBinding
import com.yuyakaido.android.cardstackview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment(context : Context) : Fragment(),CardStackListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var cardStackView : CardStackView
    private var position = 0
    private val manager by lazy { CardStackLayoutManager(context, this) }
    private  var  adapter : CardStackAdapter = CardStackAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.removeAllViews()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        cardStackView = _binding!!.cardStackView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.myEvents.adapter = ProfileEventsAdapter(arrayListOf())

        CoroutineScope(Dispatchers.IO).launch {

            // user login first

            val response = UserProfileApi(requireContext()).profileApi.getProfile()
            if (response.isSuccessful) {
                val userInfo = response.body()!!
                Log.d("userinfo: ", "${userInfo.anwesha_id}, ${userInfo.full_name}")
                requireActivity().runOnUiThread(Runnable {
                    binding.profileName.text = userInfo.full_name
                    binding.anweshaId.text = userInfo.anwesha_id
                    binding.phoneNumber.text = userInfo.phone_number
                    binding.emailId.text = userInfo.email_id
                    binding.collegeName.text = userInfo.college_name ?: "XXXXXXXXXXX"
                })
            } else {
                requireActivity().runOnUiThread(Runnable {
                    Toast.makeText(
                        context,
                        "error found: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            }

            val response2 = UserProfileApi(requireContext()).profileApi.getMyEvents()
            if (response2.isSuccessful) {
                val eventsInfo = response2.body()!!
                Log.e("PRINT", eventsInfo.toString())
                //TODO delete recycler view
                requireActivity().runOnUiThread(Runnable {
                    // binding.myEvents.adapter = ProfileEventsAdapter(eventsInfo.solo)
                    adapter = CardStackAdapter(eventsInfo.solo)
                    setupStackedCards()
                })
            }

        }
    }
    //Demo data to test stacked view
    val demo = arrayListOf<MyEventDetails>(
        MyEventDetails(
            "1",
            "HELL TURN",
            "06:00",
            "12:00",
            "IIT PATNA",
            "no tags",
            true,
            "snksandksank",
            true
        ),
        MyEventDetails(
            "2",
            "VERVE",
            "08:00",
            "14:00",
            "IIT PATNA",
            "no tags",
            true,
            "snksandksank",
            true
        )
    )

    //Functions to handle Stacked Cards

    fun setupStackedCards(){
        manager.setStackFrom(StackFrom.Top)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(10.0f)
        manager.setScaleInterval(0.8f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }
    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        this.position++
    }



}