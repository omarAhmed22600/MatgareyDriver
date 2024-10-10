package com.brandsin.driver.ui.main.orderdetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.brandsin.driver.R
import com.brandsin.driver.databinding.HomeFragmentOrderDetailsBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.constants.Const
import com.brandsin.driver.model.constants.Params
import com.brandsin.driver.network.observe
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.ui.activity.home.HomeActivity
import com.brandsin.driver.ui.activity.map.MapActivity
import com.brandsin.driver.ui.dialogs.finishorder.DialogFinishOrderFragment
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.utils.Utils
import com.brandsin.store.network.Status
import timber.log.Timber

class OrderDetailsFragment : BaseHomeFragment(), Observer<Any?>
{
    private lateinit var viewModel: OrderDetailsViewModel
    private lateinit var binding : HomeFragmentOrderDetailsBinding
    val fragmentArgs : OrderDetailsFragmentArgs by navArgs()
    var orderId = 0
    private val itemCheckedList = ArrayList<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment_order_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderDetailsViewModel::class.java)
        binding.viewModel = viewModel


        setBarName(getString(R.string.order_details))

        if (requireArguments().get(Params.ORDER_ID)!=null){
            orderId = requireArguments().get(Params.ORDER_ID) as Int
        }else{
            orderId = fragmentArgs.orderId
        }
//        if (viewModel.orderDetails.order?.userNotes!=null){
//            binding.adds.setText(viewModel.orderDetails.order?.userNotes.toString())
//        }else{
//            binding.adds.visibility=View.GONE
//            binding.textAdds.visibility=View.GONE
//        }

        viewModel.getOrderDetails(orderId)
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

        viewModel.orderContentsAdapter.orderItemLiveData.observe(viewLifecycleOwner, Observer {
            if (itemCheckedList.size > 0) {
                if (!itemCheckedList.contains(it!!.id!!.toInt())) {
                    itemCheckedList.add(it.id!!.toInt())
                }
            } else {
                itemCheckedList.add(it!!.id!!.toInt())
            }
            viewModel.allItemChecked = viewModel.allItemSize <= itemCheckedList.size
        })

        val mapFragmentOrder = childFragmentManager.findFragmentById(R.id.map_view) as SupportMapFragment?
        mapFragmentOrder!!.getMapAsync(onMapReadyCallback1())

        val mapFragmentStore = childFragmentManager.findFragmentById(R.id.map_view_store) as SupportMapFragment?
        mapFragmentStore!!.getMapAsync(onMapReadyCallback2())


        observe(viewModel.apiResponseLiveData) {
            when (it.status) {
                Status.ERROR_MESSAGE -> {
                    showToast(it.message.toString(), 1)
                }
                Status.SUCCESS_MESSAGE -> {
                    showToast(it.message.toString(), 2)
                }
                Status.SUCCESS -> {
                }
                else -> {
                    Timber.e(it.message)
                }
            }
        }
    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        it.let {
            if (it is Int) {
                when (it) {
                    Codes.ALL_ITEM_NOT_CHECKED -> {
                        showToast(getString(R.string.select_all_items), 1)
                    }
                    Codes.SUCCESS -> {
                        if (viewModel.orderDetails.order!!.status!!.contains("accepted")) {
                            binding.btnAccept.visibility = View.VISIBLE
                            if (PrefMethods.getUserData()!!.driver!!.storeId != null
                                    && PrefMethods.getUserData()!!.driver!!.storeId.toString().isNotEmpty()){
                                if (viewModel.orderDetails.order!!.status!! == "accepted_by_driver"){
                                    binding.btnAccept.setText(R.string.accept_delivery)
                                }else{
                                    binding.btnAccept.setText(R.string.accept_order)
                                }
                            }else{
                                binding.btnAccept.setText(R.string.accept_delivery)
                            }
                        } else if (viewModel.orderDetails.order!!.status == "shipping") {
                            binding.btnAccept.visibility = View.VISIBLE
                            binding.btnAccept.setText(R.string.start_delivery)
                        } else if (viewModel.orderDetails.order!!.status == "shipped") {
                            binding.btnAccept.visibility = View.VISIBLE
                            binding.btnAccept.setText(R.string.show_map)
                        } else if (viewModel.orderDetails.order!!.status == "completed") {
                            binding.btnAccept.visibility = View.GONE
                        }
                    }
                    Codes.CALL_CLICKED -> {
                        Utils.callPhone(requireActivity(), viewModel.orderDetails.order!!.phoneNumber!!)
                    }
                    Codes.CALL_STORE_CLICKED -> {
                        if (viewModel.orderDetails.order!!.store!!.phoneNumber!=null) {
                            Utils.callPhone(requireActivity(), viewModel.orderDetails.order!!.store!!.phoneNumber!!)
                        }else if (viewModel.orderDetails.order!!.store!!.phoneNumber2!=null){
                            Utils.callPhone(requireActivity(), viewModel.orderDetails.order!!.store!!.phoneNumber2!!)
                        }
                    }
                    Codes.UPDATE_STATUS -> {

                        viewModel.getOrderDetails(orderId)
                        viewModel.setShowProgress(false)
                    }
                    Codes.OPEN_MAP -> {
                        val intent = Intent(requireActivity(), MapActivity::class.java)
                        intent.putExtra("driver", true)
                        startActivityForResult(intent, Codes.SHOW_DRIVER_LOCATION)
                    }
                }
            } else  if (it is String) {
                showToast(it.toString(), 1)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == Codes.SHOW_DRIVER_LOCATION && data != null -> {
                if (data.hasExtra(Params.DIALOG_CLICK_ACTION)) {
                    when {
                        data.getIntExtra(Params.DIALOG_CLICK_ACTION, 0) == 1 -> {
                            val bundle = Bundle()
                            Utils.startDialogActivity(requireActivity(), DialogFinishOrderFragment::class.java.name, Codes.DIALOG_FINISH_ORDER_CODE, bundle)
                        }
                    }
                }
            }
            requestCode == Codes.DIALOG_FINISH_ORDER_CODE && data != null -> {
                if (data.hasExtra(Params.DIALOG_CLICK_ACTION)) {
                    when {
                        data.getIntExtra(Params.DIALOG_CLICK_ACTION, 0) == 1 -> {
                            viewModel.setUpdateStatus("completed")
                        }
                    }
                }
            }
        }

    }
    private fun onMapReadyCallback1(): OnMapReadyCallback? {
        return OnMapReadyCallback { googleMap ->
            observe(viewModel.isMapReady) {
                when (it) {
                    true -> {
                        googleMap!!.clear()
                        val latLng = LatLng(viewModel.orderDetails.order!!.lat!!.toDouble(), viewModel.orderDetails.order!!.lng!!.toDouble())
                        val option = MarkerOptions().position(latLng)
                        googleMap.addMarker(option)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Const.zoomLevel))
                    }

                    else -> {}
                }
            }
        }
    }

    private fun onMapReadyCallback2(): OnMapReadyCallback? {
        return OnMapReadyCallback { googleMap ->
            observe(viewModel.isMapReady) {
                when (it) {
                    true -> {
                        googleMap!!.clear()
                        val latLng = LatLng(
                                viewModel.orderDetails.order!!.store!!.lat!!.toDouble(),
                                viewModel.orderDetails.order!!.store!!.lng!!.toDouble())
                        val option = MarkerOptions().position(latLng)
                        googleMap.addMarker(option)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, Const.zoomLevel))
                    }

                    else -> {}
                }
            }
        }
    }
}