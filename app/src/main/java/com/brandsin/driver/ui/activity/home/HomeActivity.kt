package com.brandsin.driver.ui.activity.home

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import bolts.Task.delay
import com.brandsin.driver.R
import com.brandsin.driver.databinding.ActivityHomeBinding
import com.brandsin.driver.databinding.NavHeaderMainBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.network.observe
import com.brandsin.driver.ui.activity.ParentActivity
import com.brandsin.driver.ui.activity.auth.AuthActivity
import com.brandsin.driver.ui.main.home.Order
import com.brandsin.driver.utils.SingleLiveEvent
import com.google.android.material.navigation.NavigationView
import timber.log.Timber


class HomeActivity : ParentActivity(), Observer<Any?> {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    var viewModel: MainViewModel? = null
    lateinit var navController: NavController
    val orderClickLiveData = SingleLiveEvent<Any?>()
    var switch_action_activation: SwitchCompat? = null
    private lateinit var networkConnectionManager: ConnectivityManager
    private lateinit var networkConnectionCallback: ConnectivityManager.NetworkCallback

    private fun initConnectivityManager() {
        networkConnectionManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        networkConnectionCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // there is internet
                binding.noWifi.visibility = View.GONE
            }

            override fun onLost(network: Network) {
                // there is no internet
                lifecycleScope.launchWhenResumed {
                    delay(1000)
                    binding.noWifi.visibility = View.VISIBLE
                }
            }
        }
        networkConnectionManager.registerDefaultNetworkCallback(networkConnectionCallback)
    }
    var orderId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initConnectivityManager()

        //init view model
        initViewModel()
        binding.viewModel = viewModel
//        binding.lifecycleOwner = this

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_home_host_fragment)
        viewModel?.mutableLiveData!!.observe(this, this)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_reports,
                R.id.nav_help,
                R.id.nav_about,
                R.id.nav_contact
            ), drawerLayout
        )
        // setupActionBarWithNavController(navController, appBarConfiguration)

        /*Tovuti.from(this).monitor { connectionType, isConnected, isFast ->
            if (isConnected) {
                binding.noWifi.visibility = View.GONE
            } else {
                binding.noWifi.visibility = View.VISIBLE
            }
        }*/

        setUpToolbarAndStatusBar()
        navView.setupWithNavController(navController)
        setupNavHeader()



        binding.ibBack.setOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.nav_home -> {
                    finishAffinity()
                }
                R.id.nav_order_details -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finishAffinity()
                }
                else -> {
                    navController.navigateUp()
                }
            }
        }

        observe(orderClickLiveData) { order ->
            if (order != null) {
                when (order) {
                    is Order -> {
                        val orderId = order.id
                        if (orderId != null) { // Check if orderId is not null
                            val bundle = Bundle()
                            bundle.putInt("order_id", orderId.toInt()) // Use the correct key from your nav_graph
                            orderClickLiveData.value = null
                            navController.navigate(R.id.nav_order_details, bundle)
                        } else {
                            // Handle the case when orderId is null if necessary
                            Timber.e("OrderClick Order ID is null")
                        }
                    }
                }
            }
        }





        /*switch_action_activation =
            navView.menu.findItem(R.id.nav_action_activation).actionView?.findViewById(R.id.switch_action_activation)*/
       /* if (PrefMethods.getUserData() != null) {
            if (PrefMethods.getUserData()!!.driver != null) {
                switch_action_activation!!.isChecked =
                    PrefMethods.getUserData()!!.driver!!.isWorking == true
            }else{
                switch_action_activation!!.isChecked = false
            }
            switch_action_activation!!.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewModel!!.setActive(1)
                } else {
                    viewModel!!.setActive(0)
                }
            }
        }*/

        //data from NotificationOpenedHandler
        orderId = intent.getIntExtra("order_id", -1)
        val bundel = intent.extras
        if (bundel != null) {
            orderId = bundel.getInt("order_id", -1)
        }

        if (orderId != -1) {
            orderId = intent.getIntExtra("order_id", -1)
            if (orderId != -1) {
                navController.navigate(R.id.nav_notifications)
            }
        }


    }


    private fun setUpToolbarAndStatusBar() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.nav_home -> {
                    //customBarColor(ContextCompat.getColor(this, R.color.white))
                    viewModel?.obsShowToolbar!!.set(false)
                }
                R.id.nav_contact -> {
                    customBarColor(ContextCompat.getColor(this, R.color.payment_color))
                    viewModel?.obsShowToolbar!!.set(true)
                }
                else -> {
                    viewModel?.obsShowToolbar!!.set(true)
                    //customBarColor(ContextCompat.getColor(this, R.color.white))
                }
            }
        }
    }

    private fun customBarColor(color: Int) {
        binding.toolbar.setBackgroundColor(color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

    private fun initViewModel() {
        if (viewModel == null) {
            viewModel = ViewModelProvider(this@HomeActivity).get(MainViewModel::class.java)
        }
    }

    private fun setupNavHeader() {
        val header: View = navView.getHeaderView(0)
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE)
        val binding = NavHeaderMainBinding.inflate(inflater as LayoutInflater, navView, true);
        binding.viewModel = viewModel

        val btnEdit = header.findViewById<View>(R.id.btn_login) as TextView

        btnEdit.setOnClickListener {
            navController.navigate(R.id.nav_profile)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun customizeToolbar(
        title: String,
        show: Boolean = true,
        toolBarColor: Int = ContextCompat.getColor(this, R.color.white)
    ) {
        initViewModel()
        viewModel?.obsTitle!!.set(title)
        viewModel?.obsShowToolbar!!.set(show)
        binding.toolbar.setBackgroundColor(toolBarColor)
    }

    fun customStatusBar(statusBarColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = statusBarColor
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_home_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun showDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    fun lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    override fun onChanged(it: Any?) {
        if (it == null) return
        it.let {
            if (it is Int) {
                when (it) {
                    Codes.BUTTON_LOGIN_CLICKED -> {
                        startActivity(Intent(this, AuthActivity::class.java))
                    }
                    Codes.LOGOUT_CLICK -> {
                        startActivity(Intent(this, AuthActivity::class.java))
                        finishAffinity()
                    }
                    Codes.EDIT_CLICKED -> {
                        navController.navigate(R.id.home_to_profile)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}