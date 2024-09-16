package com.brandsin.driver.ui.auth.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fxn.pix.Pix
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.brandsin.driver.R
import com.brandsin.driver.databinding.AuthFragmentRegisterBinding
import com.brandsin.driver.model.UserLocation
import com.brandsin.driver.model.auth.register.RegisterResponse
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.constants.Params
import com.brandsin.driver.ui.activity.auth.BaseAuthFragment
import com.brandsin.driver.ui.activity.map.MapActivity
import com.brandsin.driver.ui.dialogs.vehicletype.DialogVehicleTypeFragment
import com.brandsin.driver.utils.MyApp
import com.brandsin.driver.utils.Utils
import com.brandsin.driver.utils.observe
import com.brandsin.store.network.Status
import timber.log.Timber
import java.io.File

class RegisterFragment : BaseAuthFragment(), Observer<Any?>
{
    lateinit var binding : AuthFragmentRegisterBinding
    lateinit var viewModel : RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.auth_fragment_register,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        binding.viewModel = viewModel

        val secondFragmentArgs: RegisterFragmentArgs = RegisterFragmentArgs.fromBundle(requireArguments())
        viewModel.storeCode = secondFragmentArgs.storeCode
        if (viewModel.storeCode == "1"){
            binding.codeLayout.visibility = View.VISIBLE
        }else if (viewModel.storeCode == "0"){
            binding.codeLayout.visibility = View.GONE
        }

        setBarName(getString(R.string.create_account))

        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)

        viewModel.showProgress().observe(viewLifecycleOwner, { aBoolean ->
            if (!aBoolean!!) {
                binding.progressLayout.visibility = View.GONE
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } else {
                binding.progressLayout.visibility = View.VISIBLE
                requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        })

        observe(viewModel.apiResponseLiveData) {
            when (it.status) {
                Status.ERROR_MESSAGE -> {
                    showToast(it.message.toString(), 1)
                }
                Status.SUCCESS_MESSAGE -> {
                    showToast(it.message.toString(), 2)
                }
                Status.SUCCESS -> {
                    when (it.data) {
                        is RegisterResponse -> {
                            showToast(getString(R.string.account_created_successfully), 2)
                            findNavController().navigate(R.id.register_to_login)
                        }
                    }
                }
                else -> {
                    Timber.e(it.message)
                }
            }
        }

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

                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                })
        // [END retrieve_current_token]
    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        it.let {
            when (it) {
                Codes.EMPTY_UserPic_THUMB -> {
                    showToast(getString(R.string.please_enter_user_photo), 1)
                }
                Codes.NAME_EMPTY -> {
                    showToast(getString(R.string.please_enter_your_first_name), 1)
                }
                Codes.EMPTY_LAST_NAME -> {
                    showToast(getString(R.string.please_enter_your_last_name), 1)
                }
                Codes.EMPTY_PHONE -> {
                    showToast(getString(R.string.please_enter_your_phone), 1)
                }
                Codes.INVALID_PHONE -> {
                    showToast(getString(R.string.invalid_phone), 1)
                }
                Codes.EMAIL_EMPTY -> {
                    showToast(getString(R.string.please_enter_your_email), 1)
                }
                Codes.EMAIL_INVALID -> {
                    showToast(getString(R.string.email_must_correct), 1)
                }
                Codes.EMPTY_DRIVER_LAT -> {
                    showToast(getString(R.string.please_enter_store_address), 1)
                }
                Codes.EMPTY_NationalId_IMAGE -> {
                    showToast(getString(R.string.please_enter_national_id_photo), 1)
                }
                Codes.EMPTY_DriverLicence_THUMB -> {
                    showToast(getString(R.string.please_enter_driver_licence_photo), 1)
                }
                Codes.EMPTY_STORE_CODE -> {
                    showToast(getString(R.string.please_enter_store_code), 1)
                }
                Codes.EMPTY_VehicleType -> {
                    showToast(getString(R.string.select_vehicle_type), 1)
                }
                Codes.EMPTY_VehicleNumber -> {
                    showToast(getString(R.string.enter_vehicle_number), 1)
                }
                Codes.EMPTY_DrivingLicence -> {
                    showToast(getString(R.string.enter_licence_number), 1)
                }
                Codes.PASSWORD_EMPTY -> {
                    showToast(getString(R.string.please_enter_your_password), 1)
                }
                Codes.PASSWORD_SHORT -> {
                    showToast(getString(R.string.short_password), 1)
                }
                Codes.EMPTY_Vehicle_THUMB -> {
                    showToast(getString(R.string.please_enter_vehicle_photo), 1)
                }
                Codes.SELECT_VehicleType -> {
                    val bundle = Bundle()
                    bundle.putString(Params.Driver_VehicleType,viewModel.request.vehicle_type.toString())
                    Utils.startDialogActivity(requireActivity(), DialogVehicleTypeFragment::class.java.name, Codes.DIALOG_VehicleType_CODE, bundle)
                }
                Codes.LOCATION_CLICKED -> {
                    val intent = Intent(requireActivity(), MapActivity::class.java)
                    startActivityForResult(intent, Codes.GETTING_USER_LOCATION)
                }
                Codes.SELECT_NationalId_IMAGE -> {
                    pickImage(Codes.SELECT_NationalId_IMAGE)
                }
                Codes.SELECT_DriverLicence_IMAGE -> {
                    pickImage(Codes.SELECT_DriverLicence_IMAGE)
                }
                Codes.SELECT_Vehicle_IMAGE -> {
                    pickImage(Codes.SELECT_Vehicle_IMAGE)
                }
                Codes.SELECT_UserPic_IMAGE -> {
                    pickImage(Codes.SELECT_UserPic_IMAGE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    Codes.SELECT_NationalId_IMAGE -> {
                        if (data != null) {
                            val returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                            returnValue?.let { array ->
                                binding.notNationalIdImg.visibility = View.GONE
                                binding.ivNationalIdPhoto.visibility = View.VISIBLE
                                binding.ivNationalIdImg.setImageURI(array[0].toUri())
                                viewModel.request.national_id_image =  File(array[0])
                            } }
                    }
                    Codes.SELECT_DriverLicence_IMAGE -> {
                        if (data != null) {
                            val returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                            returnValue?.let { array ->
                                binding.notThumbImg.visibility = View.GONE
                                binding.ivThumb.visibility = View.VISIBLE
                                binding.ivDriverLicencePhoto.setImageURI(array[0].toUri())
                                viewModel.request.driving_licence_image =  File(array[0])
                            }
                        }
                    }

                    Codes.SELECT_Vehicle_IMAGE -> {
                        if (data != null) {
                            val returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                            returnValue?.let { array ->
                                binding.notVehicleImg.visibility = View.GONE
                                binding.ivVehicle.visibility = View.VISIBLE
                                binding.ivVehiclePhoto.setImageURI(array[0].toUri())
                                viewModel.request.vehicle_image =  File(array[0])
                            }
                        }
                    }

                    Codes.SELECT_UserPic_IMAGE -> {
                        if (data != null) {
                            val returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                            returnValue?.let { array ->
                                binding.ivUserImg.setImageURI(array[0].toUri())
                                viewModel.request.user_picture =  File(array[0])
                            }
                        }
                    }
                }
            }
        }

        /* When user select his location manually from map activity*/
        when {
            requestCode == Codes.GETTING_USER_LOCATION && data != null -> {
                when {
                    data.hasExtra(Params.USER_LOCATION) -> {
                        val locationItem = data.getParcelableExtra<UserLocation>(Params.USER_LOCATION)
                        when {
                            locationItem != null -> {
                                viewModel.request.lat = locationItem.lat!!.toString()
                                viewModel.request.lng = locationItem.lng!!.toString()
                                binding.etAddress.setText(locationItem.address.toString())
                            }
                        }
                    }
                }
            }
            requestCode == Codes.DIALOG_VehicleType_CODE && data != null -> {
                if (data.hasExtra(Params.DIALOG_CLICK_ACTION)) {
                    when {
                        data.getIntExtra(Params.DIALOG_CLICK_ACTION, 0) == 1 -> {
                            if (data.getStringExtra("vehicleType")!!.isNotEmpty()){
                                if (data.getStringExtra("vehicleType") == "car"){
                                    binding.etVehicleType.text = MyApp.context.getString(R.string.car)
                                }else if (data.getStringExtra("vehicleType") == "motorcycle"){
                                    binding.etVehicleType.text = MyApp.context.getString(R.string.motorcycle)
                                }else if (data.getStringExtra("vehicleType") == "bicycle"){
                                    binding.etVehicleType.text = MyApp.context.getString(R.string.bicycle)
                                }
                                viewModel.request.vehicle_type = data.getStringExtra("vehicleType")
                            }
                        }
                    }
                }
            }
        }
    }
}