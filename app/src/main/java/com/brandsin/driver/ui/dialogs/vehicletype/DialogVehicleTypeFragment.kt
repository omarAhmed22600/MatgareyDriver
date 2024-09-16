package com.brandsin.driver.ui.dialogs.vehicletype

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.brandsin.driver.R
import com.brandsin.driver.databinding.DialogVehicleTypeBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.constants.Params

class DialogVehicleTypeFragment : DialogFragment(), Observer<Any?>
{
    lateinit  var  binding: DialogVehicleTypeBinding
    var vehicleType=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when {
            arguments != null -> {
                when {
                    requireArguments().containsKey(Params.Driver_VehicleType) -> {
                        if (requireArguments().getString(Params.Driver_VehicleType)!=null
                                && requireArguments().getString(Params.Driver_VehicleType)!!.isNotEmpty()
                                && requireArguments().getString(Params.Driver_VehicleType)!="null"){
                            vehicleType = requireArguments().getString(Params.Driver_VehicleType).toString()
                        }
                    }
                }
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DialogVehicleTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (vehicleType.isNotEmpty()){
            if (vehicleType == "car"){
                binding.ivSelectedCar.setImageResource(R.drawable.ic_size_check_box_24px)
                binding.ivSelectedMotorcycle.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
                binding.ivSelectedBicycle.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            }else if (vehicleType == "motorcycle"){
                binding.ivSelectedCar.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
                binding.ivSelectedMotorcycle.setImageResource(R.drawable.ic_size_check_box_24px)
                binding.ivSelectedBicycle.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            }else if (vehicleType == "bicycle"){
                binding.ivSelectedCar.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
                binding.ivSelectedMotorcycle.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
                binding.ivSelectedBicycle.setImageResource(R.drawable.ic_size_check_box_24px)
            }
        }

        binding.car.setOnClickListener {
            binding.ivSelectedCar.setImageResource(R.drawable.ic_size_check_box_24px)
            binding.ivSelectedMotorcycle.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            binding.ivSelectedBicycle.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            vehicleType = "car"
            val intent = Intent()
            intent.putExtra(Params.DIALOG_CLICK_ACTION, 1)
            intent.putExtra("vehicleType", vehicleType)
            requireActivity().setResult(Codes.DIALOG_VehicleType_CODE, intent)
            requireActivity().finish()
        }

        binding.motorcycle.setOnClickListener {
            binding.ivSelectedCar.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            binding.ivSelectedMotorcycle.setImageResource(R.drawable.ic_size_check_box_24px)
            binding.ivSelectedBicycle.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            vehicleType = "motorcycle"
            val intent = Intent()
            intent.putExtra(Params.DIALOG_CLICK_ACTION, 1)
            intent.putExtra("vehicleType", vehicleType)
            requireActivity().setResult(Codes.DIALOG_VehicleType_CODE, intent)
            requireActivity().finish()
        }

        binding.bicycle.setOnClickListener {
            binding.ivSelectedCar.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            binding.ivSelectedMotorcycle.setImageResource(R.drawable.ic_check_box_outline_blank_24px)
            binding.ivSelectedBicycle.setImageResource(R.drawable.ic_size_check_box_24px)
            vehicleType ="bicycle"
            val intent = Intent()
            intent.putExtra(Params.DIALOG_CLICK_ACTION, 1)
            intent.putExtra("vehicleType", vehicleType)
            requireActivity().setResult(Codes.DIALOG_VehicleType_CODE, intent)
            requireActivity().finish()
        }
    }

    override fun onChanged(it: Any?) {
        when (it) {
            null -> return
            else -> when (it) {

            }
        }
    }
}