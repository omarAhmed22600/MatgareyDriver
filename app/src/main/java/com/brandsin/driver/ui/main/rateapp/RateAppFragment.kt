package com.brandsin.driver.ui.main.rateapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brandsin.driver.R
import com.brandsin.driver.databinding.MenuFragmentRateAppBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.activity.BaseHomeFragment
import es.dmoral.toasty.Toasty

class RateAppFragment : BaseHomeFragment(), Observer<Any?>
{
    private lateinit var binding : MenuFragmentRateAppBinding
    private lateinit var rateAppViewModel: RateAppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.menu_fragment_rate_app, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        rateAppViewModel = ViewModelProvider(this).get(RateAppViewModel::class.java)
        binding.viewModel = rateAppViewModel

        setBarName(getString(R.string.rate_app_title))

        rateAppViewModel.mutableLiveData.observe(viewLifecycleOwner, this)
    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        when (it) {
            Codes.RATING_SUCCESS ->
            {
                val packageName = "com.brandsin.driver"
                val uri = Uri.parse("market://details?id=$packageName")
                val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                try
                {
                    startActivity(myAppLinkToMarket)
                }
                catch (e: ActivityNotFoundException)
                {
                    Toasty.warning(requireActivity(), "Impossible to find an application for the market").show()
                }
            }
        }
    }
}