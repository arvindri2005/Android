package com.iitp.anwesha.home

//import com.iitp.anwesha.home.functions.MapClickHandle
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.iitp.anwesha.R
import com.iitp.anwesha.tickets.PassesFragment
import com.iitp.anwesha.databinding.FragmentHomeBinding
import com.iitp.anwesha.events.SingleEventFragment
import com.iitp.anwesha.home.functions.NavItemsFunctions
import java.text.SimpleDateFormat
import java.util.Locale


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var eventViewModel: EventsViewModel
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var newEventList: ArrayList<EventList>
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var adapter: EventAdapter
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        newEventList = arrayListOf()
        drawerLayout = binding.frameLayout
        actionBarToggle = ActionBarDrawerToggle(activity, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        binding.allDay.isChecked = true

        binding.composeView.setContent {
            AnweshaMap()
        }


        binding.deliveryShimmer.startShimmer()
        val sharedPref = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        binding.navBar.setOnClickListener {
            requireActivity().findViewById<TextView>(R.id.nameText2).text =
                sharedPref.getString(getString(R.string.user_name), "User")
            requireActivity().findViewById<TextView>(R.id.textView).text =
                sharedPref.getString("anwesha_id", "id")
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val bottomSheet = binding.eventBottomSheet
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = 1000
        binding.hintImg.visibility = View.GONE
        binding.hintTxt.visibility = View.GONE

        binding.festPasses.setOnClickListener {
            loadPassesFragment(sharedPref.getString(getString(R.string.user_type),"student")!!)
        }

        NavItemsFunctions(binding, requireActivity(), requireActivity()).selectingItems()
        eventViewModel = ViewModelProvider(this)[EventsViewModel::class.java]
        return binding.root

    }
    @Preview
    @Composable
    fun AnweshaMap() {
        var scale  by remember {
            mutableStateOf(2f) }

        var offset  by remember {
            mutableStateOf(Offset.Zero) }

        val interactionSource = remember { MutableInteractionSource() }

        BoxWithConstraints(modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                slideDown()
            }
            .fillMaxWidth()
            //Set the Aspect Ratio Of map
            .aspectRatio(2496f/1356f)
        ) {
            val state = rememberTransformableState{zoomChange, panChange, rotationChange ->
                scale = (scale* zoomChange).coerceIn(1f,5f)
                val extraHeight = (scale-1)*constraints.maxHeight
                val extraWidth = (scale-1)*constraints.maxWidth

                val maxX=extraHeight/2
                val maxY=extraWidth/2


                offset = Offset(
                    x = (offset.x + scale*panChange.x).coerceIn(-maxX*2, + maxX*2),
                    y = (offset.y + scale*panChange.y).coerceIn(-maxY, + maxY)
                )

            }

            Box(modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state)){

                Image(painter = painterResource(id = R.drawable.map), contentDescription ="Anwesha Map")

                //Nescafe
                Pointer(id = R.drawable.nescafe, Modifier.padding(68.dp, 50.dp, 0.dp, 0.dp),"Nescafe, IIT PATNA","Nescafe")
                //Sac
                Pointer(id = R.drawable.sac, Modifier.padding(120.dp, 60.dp, 0.dp, 0.dp),"Open Air Theatre","SAC")
                //gym
                Pointer(id = R.drawable.gym, Modifier.padding(175.dp, 60.dp, 0.dp, 0.dp), "Gymkhana, IIT PATNA", "Gymkhana")
                //basketball
                Pointer(id = R.drawable.basketball, Modifier.padding(165.dp, 70.dp, 0.dp, 0.dp),"Basketball Court","Basketball Court")
                //nsit
                Pointer(id = R.drawable.nsit, Modifier.padding(125.dp, 80.dp, 0.dp, 0.dp),"NSIT Wall","NSIT Wall")
                //senate
                Pointer(id = R.drawable.senate, Modifier.padding(85.dp, 65.dp, 0.dp, 0.dp),"Senate Hall","Senate Hall")
                //helipad
                Pointer(id = R.drawable.helipad, Modifier.padding(40.dp, 88.dp, 0.dp, 0.dp),"test","Helipad")
                //main_stage
                Pointer(id = R.drawable.main_stage, Modifier.padding(63.dp, 76.dp, 0.dp, 0.dp),"Main Fest Ground","Main Stage")
                //lh1
                Pointer(id = R.drawable.lh1, Modifier.padding(40.dp, 60.dp, 0.dp, 0.dp),"Block 9","Block 9")

            }

        }





    }
    @Composable
    fun Pointer(id: Int , modifier: Modifier = Modifier,venue:String,shortVenue: String){
        Box(modifier = modifier){
            Image(painter = painterResource(id = id) , contentDescription =null,
                modifier= Modifier
                    .size(20.dp, 20.dp)
                    .clickable {
                        slideUp()
                        binding.eventText.text = "Events at $shortVenue"
                        venueClicked(venue)
                    }
            )
        }
    }

    private fun slideDown(){
        val slideDown = ValueAnimator.ofInt(1000, dpToPx(150))
        slideDown.duration = 500
        slideDown.addUpdateListener {
            behavior.peekHeight = it.animatedValue as Int
        }
        if(behavior.peekHeight!=dpToPx(150)){
            slideDown.start()
        }
        binding.hintImg.visibility = View.VISIBLE
        binding.hintTxt.visibility = View.VISIBLE
    }

    private fun slideUp(){
        val slideUp = ValueAnimator.ofInt(200, 1000)
        slideUp.duration = 500
        slideUp.addUpdateListener {
            behavior.peekHeight = it.animatedValue as Int
        }
        if(behavior.peekHeight!=1000){
            slideUp.start()
        }
        binding.hintImg.visibility = View.GONE
        binding.hintTxt.visibility = View.GONE
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp.toFloat() * density + 0.5f).toInt()
    }

    private fun venueClicked(venue: String) {
        binding.hintTxt.visibility = View.GONE
        binding.hintImg.visibility = View.GONE
        val venueEvent = ArrayList<EventList>()
        for(i in newEventList){
            if (i.venue==venue){
                venueEvent.add(i)
            }
        }
        binding.isEmpty.visibility = View.GONE
        adapter.setEvents(venueEvent)
        adapter.notifyDataSetChanged()

        binding.allDay.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        binding.day1.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        binding.day2.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        binding.day3.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        binding.allDay.setBackgroundResource(R.drawable.home_day_btn_bg)
        binding.day1.setBackgroundResource(R.drawable.home_day_btn_bg)
        binding.day2.setBackgroundResource(R.drawable.home_day_btn_bg)
        binding.day3.setBackgroundResource(R.drawable.home_day_btn_bg)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadEvents()

//      Day wise event loading
        binding.allDay.setOnClickListener {
            slideUp()
            adapter.setEvents(newEventList)
            adapter.notifyDataSetChanged()
        }
        binding.day1.setOnClickListener {
            slideUp()
            loadDayEvents("02")
        }
        binding.day2.setOnClickListener {
            slideUp()
            loadDayEvents("03")
        }
        binding.day3.setOnClickListener {
            slideUp()
            loadDayEvents("04")
        }
    }

    private fun loadDayEvents(day: String) {
        binding.eventText.text = "Events"
        val dayEvent = ArrayList<EventList>()
        for (i in newEventList) {
            val da = getDayFromDate(i.start_time!!)
            if (day == da) {
                dayEvent.add(i)
            }
        }
        adapter.setEvents(dayEvent)
        adapter.notifyDataSetChanged()
    }

    private fun getDayFromDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd", Locale.getDefault())
        return outputFormat.format(date!!)
    }

    private fun loadEvents() {
        eventRecyclerView = binding.eventsList
        eventRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = EventAdapter(requireContext())
        eventRecyclerView.adapter = adapter
        getEvents()
    }

    private fun getEvents() {
        eventViewModel.getEventListObserver().observe(viewLifecycleOwner) {
            if (it != null) {
                newEventList = it
                adapter.setEvents(it)
                adapter.notifyDataSetChanged()
                adapter.setOnItemClickListener(object : EventAdapter.OnItemClickListener {
                    override fun onItemClicked(event: EventList) {          //when any event from the recycler view is clicked
                        loadSingleEventFragment(event)
                    }
                })

                binding.deliveryShimmer.visibility = View.GONE
//                binding.animationView.visibility = View.VISIBLE
                binding.deliveryShimmer.stopShimmer()
//                binding.animationView.smoothScrollTo(dpToPx(150),0)
//                binding.verticalScroll.smoothScrollTo(0, dpToPx(480))
            } else {
                Toast.makeText(context, "Error in getting Events", Toast.LENGTH_SHORT).show()
            }
        }

        eventViewModel.makeApiCall(requireContext())
    }

    private fun loadPassesFragment(userType: String) {
        val bundle = Bundle()
        val fragment = PassesFragment()
        val passPrice = eventViewModel.getPassPrice().value
        bundle.putSerializable("passData", PassesData(userType, passPrice!!))
        fragment.arguments = bundle
        val fragmentManager = activity?.supportFragmentManager!!.beginTransaction()
        fragmentManager.replace(R.id.fragmentContainer, fragment)
        fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }

    private fun loadSingleEventFragment(event: EventList) {
        val bundle = Bundle()
        bundle.putSerializable("event", event)
        val fragment = SingleEventFragment()
        fragment.arguments = bundle
        val fragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}


