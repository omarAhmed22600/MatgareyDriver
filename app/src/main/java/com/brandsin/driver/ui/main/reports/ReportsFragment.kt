package com.brandsin.driver.ui.main.reports

import android.content.Intent
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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.brandsin.driver.R
import com.brandsin.driver.databinding.HomeFragmentReportsBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.constants.Params
import com.brandsin.driver.ui.main.reports.personal.ReportsPersonalFragment
import com.brandsin.driver.model.menu.about.AboutItem
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.ui.activity.home.HomeActivity

class ReportsFragment : BaseHomeFragment(), Observer<Any?>
{
    lateinit var binding: HomeFragmentReportsBinding
    lateinit var viewModel: ReportsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = HomeFragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ReportsViewModel::class.java)
        binding.viewModel = viewModel

        // Instantiate a ViewPager2 and a PagerAdapter.
        val viewPager = binding.pager

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ReportsPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.pager) { tab, position ->
            when (position)
            {
                0 -> {
                    val customView = requireActivity().layoutInflater.inflate(R.layout.raw_report_tab_item, null)
                    val itemName = customView.findViewById<TextView>(R.id.tv_itemName)
                    itemName.text = getString(R.string.personal_report)
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
//                1 -> {
//                    val customView = requireActivity().layoutInflater.inflate(R.layout.raw_report_tab_item, null)
//                    val itemName = customView.findViewById<TextView>(R.id.tv_itemName)
//                    itemName.text = getString(R.string.store_report)
//                    tab.customView = customView
//                    customView.tag = 0
//                }
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

        (requireActivity() as HomeActivity).customizeToolbar(getString(R.string.reports))

        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)
    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        it.let {
            if (it is AboutItem)
            {
                when (it.id) {
                    Codes.SHOW_COMPLETED_ORDERS -> {
                        // do what you want
                    }
                }
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

    class ReportsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 1

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return ReportsPersonalFragment()
//                1 -> return ReportsStoreFragment()
            }
            return ReportsPersonalFragment()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == Codes.SHOW_DAILY_FILTER && data != null -> {
                when {
                    data.getIntExtra(Params.DIALOG_CLICK_ACTION, 1) == 1 -> {
                        viewModel.type = "daily"
                        viewModel.from = data.getStringExtra("from").toString()
                        viewModel.to = data.getStringExtra("to").toString()
                        viewModel.getReports()
                    }
                }
            }

            requestCode == Codes.SHOW_MONTH_FILTER && data != null -> {
                when {
                    data.getIntExtra(Params.DIALOG_CLICK_ACTION, 1) == 1 -> {
                        viewModel.type = "monthly"
                        viewModel.from = data.getStringExtra("from").toString()
                        viewModel.to = data.getStringExtra("to").toString()
                        viewModel.getReports()
                    }
                }
            }
        }
    }
}

