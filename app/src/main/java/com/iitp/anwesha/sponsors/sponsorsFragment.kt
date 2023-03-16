package com.iitp.anwesha.sponsors

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.iitp.anwesha.R
import com.iitp.anwesha.databinding.FragmentSponsorsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class sponsorsFragment : Fragment() {
    private lateinit var binding: FragmentSponsorsBinding
    private lateinit var fragmentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
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
        binding = FragmentSponsorsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.deliveryShimmer.startShimmer()
//        val testlink = "https://as2.ftcdn.net/v2/jpg/01/33/48/03/1000_F_133480376_PWlsZ1Bdr2SVnTRpb8jCtY59CyEBdoUt.jpg"
//        val ls1 = SponsorResponse(1, "spacex", "", "", testlink, "", "", "", "")
//        val ls2 = SponsorResponse(1, "spacex", "", "", testlink, "", "", "", "")
//        val ls3 = SponsorResponse(1, "spacex", "", "", testlink, "", "", "", "")
//        val ls4 = SponsorResponse(1, "spacex", "", "", testlink, "", "", "", "")
//        val ls5 = SponsorResponse(1, "spacex", "", "", testlink, "", "", "", "")

//        val testlist: List<SponsorResponse> = listOf(ls1, ls2, ls3, ls4, ls5)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val response2 = SponsorCall(requireContext()).sponsorApi.getSponsor()
                if (response2.isSuccessful) {
                    val sponsorInfo = response2.body()!!
                    Log.e("PRINT", sponsorInfo.toString())
                    binding.recyclerView.layoutManager =
                        GridLayoutManager(fragmentContext, 2)
                    sponsorInfo.sortedBy { it.id }
                    binding.recyclerView.adapter = sponsorAdapter(sponsorInfo, fragmentContext)
                    binding.visibleFrag.visibility  =View.VISIBLE
                    binding.deliveryShimmer.visibility  =View.GONE
                }
            }
        }

//        binding.recyclerView.layoutManager =
//            GridLayoutManager(fragmentContext, 2)
//        binding.recyclerView.adapter = sponsorAdapter(testlist, fragmentContext)

        binding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(null, 0)
        }

        return view
    }
}