package com.brandsin.driver.ui.main.home.completedorders

import android.Manifest
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.brandsin.driver.R
import com.brandsin.driver.databinding.HomeFragmentCompletedOrdersBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.ui.activity.auth.AuthActivity
import com.brandsin.driver.ui.activity.home.HomeActivity
import com.brandsin.driver.utils.PrefMethods
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class CompletedOrdersFragment : BaseHomeFragment(), Observer<Any?>,
    GoogleApiClient.ConnectionCallbacks,
    OnConnectionFailedListener, LocationListener
{
    private val MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION: Int=600
    private lateinit var viewModel: CompletedOrdersViewModel
    private lateinit var binding : HomeFragmentCompletedOrdersBinding
    private var currentLatitude = 0.0
    private var currentLongitude = 0.0
    private val CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null


    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            } else -> {
            // No location access granted.
        }
        }
    }

    // ...

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_fragment_completed_orders,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompletedOrdersViewModel::class.java)
        binding.viewModel = viewModel

        (requireActivity() as HomeActivity).customizeToolbar("", false)



        ///////////////////lng lat//////////////
//        viewModel!!.obsLat.set(currentLatitude)
//        viewModel!!.obsLang.set(currentLongitude)

        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = false
            viewModel.getUserStatus()
            viewModel.apiupdateLocation()
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


        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)
        viewModel.completedOrdersAdapter.completedOrderItemLiveData.observe(
            viewLifecycleOwner,
            Observer {
               // findNavController().navigate(R.id.completed_orders_to_order_details)
                (requireActivity() as HomeActivity).orderClickLiveData.value = it
                (requireActivity() as HomeActivity).orderClickLiveData.value = null
            })

        binding.rvOrders.addOnItemTouchListener(object : OnItemTouchListener {
            var lastX = 0
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> lastX = e.x.toInt()
                    MotionEvent.ACTION_MOVE -> {
                        val isScrollingRight = e.x < lastX
                        if (isScrollingRight && (binding.rvOrders.layoutManager as LinearLayoutManager).
                            findLastCompletelyVisibleItemPosition() == binding.rvOrders.adapter!!.itemCount - 1 ||
                            !isScrollingRight && (binding.rvOrders.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0
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
            when (it) {
                Codes.LOGIN_CLICKED -> {
                    PrefMethods.saveIsAskedToLogin(true)
                    requireActivity().startActivity(Intent(requireActivity(),
                            AuthActivity::class.java))
                }
            }
        }
    }

    fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //ho i permessi, scrivo nella casella di testo che va tutto bene
            Log.d("Location", "The permission is granted")
            true
        } else {
            //non ho i permessi, devo richiederli
            Log.d("Location", "Ask for permission")
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            )
            false
        }
    }
    override fun onResume() {
        super.onResume()
        when {
            PrefMethods.getIsAskedToLogin() -> {
                viewModel.getUserStatus()
                PrefMethods.saveIsAskedToLogin(false)
            }
        }
        mGoogleApiClient!!.connect();
    }

    override fun onConnected(p0: Bundle?) {
        if(checkLocationPermission()) {
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
            viewModel!!.obsLat.set(currentLatitude)
            viewModel!!.obsLang.set(currentLongitude)
        }
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
            } catch (e: SendIntentException) {
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

    override fun onLocationChanged(p0: Location) {
        currentLatitude=p0.latitude
        currentLongitude=p0.longitude
        viewModel!!.obsLat.set(currentLatitude)
        viewModel!!.obsLang.set(currentLongitude)
    }
}