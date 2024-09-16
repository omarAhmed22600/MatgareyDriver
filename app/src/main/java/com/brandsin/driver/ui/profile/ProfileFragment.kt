package com.brandsin.driver.ui.profile

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.brandsin.driver.R
import com.brandsin.driver.databinding.ProfileFragmentBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.ui.activity.home.HomeActivity
import timber.log.Timber
import java.util.*

class ProfileFragment : BaseHomeFragment(), Observer<Any?>
{
    lateinit var binding : ProfileFragmentBinding
    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.viewModel = viewModel

        setBarName(getString(R.string.personal_profile))

        if (PrefMethods.getLanguage(requireActivity()) == "ar")
        {
           binding.tvLanguage.text = "English"
        }
        else
        {
            binding.tvLanguage.text = "العربية"
        }

        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)

        binding.ibSwitch.isChecked = PrefMethods.getIsNotificationsEnabled()!!

        binding.notificationLayout.setOnClickListener {
            when {
                binding.ibSwitch.isChecked -> {
                    binding.ibSwitch.isChecked = false
                    PrefMethods.setIsNotificationsEnabled(false)
                }
                else -> {
                    binding.ibSwitch.isChecked = true
                    PrefMethods.setIsNotificationsEnabled(true)
                }
            }
        }
        binding.ibSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                PrefMethods.setIsNotificationsEnabled(true)
            } else {
                PrefMethods.setIsNotificationsEnabled(false)
            }
        }
    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        it.let {
            when (it) {
                is Int -> {
                    when (it)
                    {
                        Codes.EDIT_CLICKED ->
                        {
                            findNavController().navigate(R.id.profile_to_update)
                        }
                        Codes.VEHICLE_CLICKED ->
                        {
                            findNavController().navigate(R.id.profile_to_vehicle)
                        }
                        Codes.CHANGE_PASS_CLICKED ->
                        {
                            findNavController().navigate(R.id.profile_to_change_pass)
                        }
                        Codes.LANGUAGE_CLICKED ->
                        {
                            if (PrefMethods.getLanguage(requireActivity()) == "ar")
                            {
                                setLanguage("en")
                            }
                            else
                            {
                                setLanguage("ar")
                            }
                        }
                        else ->
                            Timber.e("")
                    }
                }
                else ->
                    Timber.e("")
            }
        }
    }

    private fun setLanguage(language: String?)
    {
        val mainConfig = Configuration(resources.configuration)
        val locale = Locale(language)
        Locale.setDefault(locale)
        mainConfig.setLocale(locale)
        resources.updateConfiguration(mainConfig, null)
        PrefMethods.setLanguage(language.toString())

        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }
}