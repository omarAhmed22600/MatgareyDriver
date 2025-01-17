package com.brandsin.driver.ui.main.home

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.iid.FirebaseInstanceId
import com.brandsin.driver.R
import com.brandsin.driver.databinding.HomeFragmentHomeBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.ui.activity.home.HomeActivity
import com.brandsin.driver.ui.main.home.completedorders.CompletedOrdersFragment
import com.brandsin.driver.ui.main.home.neworders.NewOrdersFragment
import es.dmoral.toasty.Toasty
import timber.log.Timber

class HomeFragment : BaseHomeFragment(), Observer<Any?>
{
    lateinit var binding: HomeFragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var tabLayout:TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = HomeFragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        binding.ibMenu.setOnClickListener {
            (activity as HomeActivity).showDrawer()
        }

        val pagerAdapter = HomeOrdersPagerAdapter(this)
        binding.pager.adapter = pagerAdapter
        tabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, binding.pager) { tab, position ->
            when (position)
            {
                0 -> {
                    val customView = requireActivity().layoutInflater.inflate(R.layout.raw_report_tab_item, null)
                    val itemName = customView.findViewById<TextView>(R.id.tv_itemName)
                    itemName.text = getString(R.string.new_orders)
                    itemName.setTextColor(ContextCompat.getColor(requireActivity(), R.color.color_primary))
                    tab.customView = customView
                    customView.tag = 0

                    val viewName = customView!!.findViewById<TextView>(R.id.tv_itemName)
                    viewName.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                    viewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.tab_text_size));
                    viewName.background = ContextCompat.getDrawable(requireActivity(), R.drawable.btn_orders_selected)
                    val myCustomFont: Typeface? = ResourcesCompat.getFont(requireActivity(), R.font.cairo_semibold)
                    viewName.typeface = myCustomFont
                }
                1 -> {
                    val customView = requireActivity().layoutInflater.inflate(R.layout.raw_report_tab_item, null)
                    val itemName = customView.findViewById<TextView>(R.id.tv_itemName)
                    itemName.text = getString(R.string.completed_orders)
                    tab.customView = customView

                    customView.tag = 1

                }
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                val viewName = p0!!.customView!!.findViewById<TextView>(R.id.tv_itemName)
                viewName.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey_subcategory))
                viewName.background = ContextCompat.getDrawable(requireActivity(), R.drawable.btn_orders_unselected)
                val myCustomFont: Typeface? = ResourcesCompat.getFont(requireActivity(), R.font.cairo_semibold)
                viewName.typeface = myCustomFont
                viewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.tab_text_size));
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val viewName = p0!!.customView!!.findViewById<TextView>(R.id.tv_itemName)
                viewName.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
                viewName.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.tab_text_size));
                viewName.background = ContextCompat.getDrawable(requireActivity(), R.drawable.btn_orders_selected)
                val myCustomFont: Typeface? = ResourcesCompat.getFont(requireActivity(), R.font.cairo_semibold)
                viewName.typeface = myCustomFont
            }
        })
        setSelectedTab(0)
        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)
      /*  (requireActivity() as HomeActivity).orderClickLiveData.observe(viewLifecycleOwner, Observer {
           // findNavController().navigate(R.id.home_to_order_details)
           // (requireActivity() as HomeActivity).navController.navigate(R.id.home_to_order_details)
            Navigation.findNavController(binding.root).navigate(R.id.completed_orders_to_order_details)
        })*/

        // Get token
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val token = task.result.token
                    viewModel.deviceTokenRequest.token = token
                    viewModel.deviceToken()

                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                })
        // [END retrieve_current_token]
    }
    private fun setSelectedTab(tabIndex: Int) {
        // Check if the index is valid
        if (tabIndex in 0 until tabLayout.tabCount) {
            tabLayout.selectTab(tabLayout.getTabAt(tabIndex))
        } else {
            Timber.e("TabSelection Invalid tab index: $tabIndex")
        }
    }
    override fun onChanged(it: Any?)
    {
        if(it == null) return
        it.let {
            if (it is Order)
            {
                Toasty.success(requireActivity(), "eeeeeeee").show()
              //  findNavController().navigate(R.id.home_to_order_details)
            }
            else if (it is Order)
            {
                Toasty.success(requireActivity(), "sssssssss").show()
                //  findNavController().navigate(R.id.home_to_order_details)
            }
            else if (it is Int)
            {
                when (it) {
                    Codes.NOTIFICATION_CLICK -> {
                        findNavController().navigate(R.id.home_to_notifications)
                    }
                }
            }
        }
    }

    override fun onStart()
    {
        super.onStart()
        (requireActivity() as HomeActivity).customizeToolbar("", false)
    }

    override fun onPause()
    {
        super.onPause()
        (activity as HomeActivity).lockDrawer()
    }

    class HomeOrdersPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                1 -> CompletedOrdersFragment()
                else -> NewOrdersFragment()
            }
        }
    }
}

