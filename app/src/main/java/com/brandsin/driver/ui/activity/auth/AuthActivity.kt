package com.brandsin.driver.ui.activity.auth

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.brandsin.driver.R
import com.brandsin.driver.databinding.ActivityAuthBinding
import com.brandsin.driver.model.constants.Const
import com.brandsin.driver.network.ConnectivityReceiver
import com.brandsin.driver.ui.activity.ParentActivity
import com.brandsin.driver.ui.activity.home.HomeActivity
import com.brandsin.driver.utils.PrefMethods
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthActivity : ParentActivity(),ConnectivityReceiver.ConnectivityReceiverListener
{
    lateinit var binding : ActivityAuthBinding
    var viewModel: AuthViewModel? = null
    private lateinit var navController: NavController
    private lateinit var connectivityReceiver: ConnectivityReceiver

    var orderId =-1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        //LocalUtil.changeLanguage(this)
        super.onCreate(savedInstanceState)
        connectivityReceiver = ConnectivityReceiver(this)
        registerReceiver(connectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        //init view model
        initViewModel()
        binding.viewModel = viewModel
        navController = findNavController(R.id.nav_auth_host_fragment)

        /*Tovuti.from(this).monitor { connectionType, isConnected, isFast ->
            if (isConnected) {
                binding.noWifi.visibility = View.GONE
            } else {
                binding.noWifi.visibility = View.VISIBLE
            }
        }*/


        //data from NotificationOpenedHandler
        if (intent.getIntExtra("order_id",-1)!=-1) {
            orderId = intent.getIntExtra("order_id",-1)
            val bundel=intent.extras
            if(bundel!=null){
                orderId= bundel.getInt("order_id",-1)
            }
        }
//        if (orderId!=-1) {
//            orderId = intent.getIntExtra("order_id",-1)
//            if(orderId != -1) {
//                navController.navigate(R.id.nav_notifications)
//            }
//        }

        binding.ibBack.setOnClickListener {
            navController.navigateUp()
        }

        viewModel!!.clickableLiveData.observe(this, {
            viewModel!!.obsIsClickable.set(false)
            lifecycleScope.launch {
                delay(2000)
                viewModel!!.clickableLiveData.value = false
                viewModel!!.obsIsClickable.set(true)
            }
        })

        when {
            intent.hasExtra(Const.ACCESS_LOGIN) -> {
                when {
                    intent.getBooleanExtra(Const.ACCESS_LOGIN, false) -> {

                        navController.navigate(R.id.navigation_login)
                    }
                }
            }
        }

        setUpToolbarAndStatusBar()
        startIntent()

    }

    private fun initViewModel() {
        if (viewModel == null) {
            viewModel = ViewModelProvider(this@AuthActivity).get(AuthViewModel::class.java)
        }
    }

    private fun setUpToolbarAndStatusBar() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_login_ways  ->
                {
                    //customBarColor(ContextCompat.getColor(this, R.color.white))
                    viewModel?.obsShowToolbar!!.set(false)
                }
                R.id.navigation_splash -> {
                    //customBarColor(ContextCompat.getColor(this, R.color.offers_bg_color))
                    viewModel?.obsShowToolbar!!.set(false)
                }
                else ->
                {
                    viewModel?.obsShowToolbar!!.set(true)
                    //customBarColor(ContextCompat.getColor(this, R.color.white))
                }
            }
        }
   }

    private fun customBarColor(color : Int) {
        binding.toolbar.setBackgroundColor(color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

    fun setBarName(title: String) {
        initViewModel()
        viewModel?.obsTitle!!.set(title)
    }
    private fun startIntent() {
        if (orderId != -1) {
            if (PrefMethods.getLoginState()) {
                var intent = Intent(this@AuthActivity, HomeActivity::class.java)
                intent.putExtra("order_id", orderId)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            binding.noWifi.visibility = View.GONE
        } else {
            binding.noWifi.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(connectivityReceiver)
    }
}