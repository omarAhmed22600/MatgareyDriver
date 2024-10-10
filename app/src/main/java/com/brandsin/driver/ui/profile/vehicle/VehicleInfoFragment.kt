package com.brandsin.driver.ui.profile.vehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brandsin.driver.R
import com.brandsin.driver.databinding.ProfileFragmentVehicleInfoBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.constants.Params
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.utils.bindImage
import com.bumptech.glide.Glide
import org.koin.android.ext.android.bind
import timber.log.Timber

class VehicleInfoFragment : BaseHomeFragment(), Observer<Any?>
{
    lateinit var binding : ProfileFragmentVehicleInfoBinding
    lateinit var viewModel : VehicleInfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment_vehicle_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(VehicleInfoViewModel::class.java)
        binding.viewModel = viewModel

        setBarName(getString(R.string.vehicle_info))

        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)

        if (PrefMethods.getUserData() != null) {
            binding.vehicleType.text = PrefMethods.getUserData()!!.driver!!.vehicleType.toString()
            binding.vehicleNumber.text = PrefMethods.getUserData()!!.driver!!.vehicleNumber.toString()
            Timber.e("image is ${PrefMethods.getUserData()!!.driver!!.drivingLicence}")
            binding.licenceNumber.bindImage("${Params.BASE_URL}${PrefMethods.getUserData()!!.driver!!.drivingLicence.toString()}")
//            Glide.with(view.context).load(PrefMethods.getUserData()!!.driver!!.drivingLicence.toString()).error(R.drawable.logo3).into(view.findViewById(R.id.licence_number))

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
                        Codes.RATING_SUCCESS ->
                        {
                            // do what you want
                        }
                    }
                }
            }
        }
    }

}