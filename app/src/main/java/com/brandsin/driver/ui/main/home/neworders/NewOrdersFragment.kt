package com.brandsin.driver.ui.main.home.neworders

import android.annotation.SuppressLint
import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brandsin.driver.R
import com.brandsin.driver.databinding.HomeFragmentNewOrdersBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.ui.activity.home.HomeActivity
import com.brandsin.driver.ui.main.home.Order
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class NewOrdersFragment : BaseHomeFragment(), Observer<Any?>, GoogleApiClient.ConnectionCallbacks,
    OnConnectionFailedListener, LocationListener
{
    private lateinit var newOrdersViewModel: NewOrdersViewModel
    private lateinit var binding : HomeFragmentNewOrdersBinding
    private var currentLatitude = 0.0
    private var currentLongitude = 0.0
    private val CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment_new_orders, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        newOrdersViewModel = ViewModelProvider(this).get(NewOrdersViewModel::class.java)
        binding.viewModel = newOrdersViewModel



        ///////////////////lng lat//////////////
//        newOrdersViewModel!!.obsLat.set(currentLatitude)
//        newOrdersViewModel!!.obsLang.set(currentLongitude)

        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = false
            newOrdersViewModel.getUserStatus()
            newOrdersViewModel.apiupdateLocation()

        }

        mGoogleApiClient =
            GoogleApiClient.Builder(requireActivity()) // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build()


        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval((10 * 1000).toLong()) // 10 seconds, in milliseconds
            .setFastestInterval((1 * 1000).toLong()) // 1 second, in milliseconds





        newOrdersViewModel.mutableLiveData.observe(viewLifecycleOwner, this)
        newOrdersViewModel.newOrdersAdapter.newOrderItemLiveData.observe(viewLifecycleOwner, Observer {
            (requireActivity() as HomeActivity).orderClickLiveData.value = it
            (requireActivity() as HomeActivity).orderClickLiveData.value = null
        })

        binding.rvNewOrders.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            var lastX = 0
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> lastX = e.x.toInt()
                    MotionEvent.ACTION_MOVE -> {
                        val isScrollingRight = e.x < lastX
                        if (isScrollingRight && (binding.rvNewOrders.layoutManager as LinearLayoutManager).
                            findLastCompletelyVisibleItemPosition() == binding.rvNewOrders.adapter!!.itemCount - 1 ||
                            !isScrollingRight && (binding.rvNewOrders.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0
                        ) {
                            //  binding.p.setUserInputEnabled(true)
                        } else {
                            //  viewPager.setUserInputEnabled(false)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        lastX = 0
                        // viewPager.setUserInputEnabled(true)
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        it.let {
            if (it is Order)
            {
                findNavController().navigate(R.id.new_orders_to_order_details)
            }
            if (it is Int)
            {
                when (it) {
                    Codes.RATING_SUCCESS ->
                    {
                        // do what you want
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mGoogleApiClient!!.connect();
    }

    override fun onLocationChanged(p0: Location) {
        currentLatitude=p0.latitude
        currentLongitude=p0.longitude
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        val location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
            )
        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.latitude
            currentLongitude = location.longitude
        }
        newOrdersViewModel!!.obsLat.set(currentLatitude)
        newOrdersViewModel!!.obsLang.set(currentLongitude)
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        if (p0.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                p0.startResolutionForResult(
                    requireActivity(),
                    CONNECTION_FAILURE_RESOLUTION_REQUEST
                )
                /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (e: IntentSender.SendIntentException) {
                // Log the error
                e.printStackTrace()
            }
        } else {
            /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e(
                "Error",
                "Location services connection failed with code " + p0.getErrorCode()
            )
        }
    }
}