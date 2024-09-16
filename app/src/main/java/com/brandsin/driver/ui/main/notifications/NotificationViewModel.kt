package com.brandsin.driver.ui.main.notifications

import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.network.requestCall
import com.brandsin.driver.model.menu.notifications.NotificationItem
import com.brandsin.driver.model.menu.notifications.NotificationResponse
import com.brandsin.driver.model.menu.notifications.ReadNotificationRequest
import com.brandsin.driver.model.menu.notifications.ReadNotificationResponse
import com.brandsin.driver.utils.PrefMethods
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationViewModel : BaseViewModel()
{
    var notificationAdapter  = NotificationsAdapter()
    var notificationsList : ArrayList<NotificationItem> = ArrayList()

    init {
        getUserStatus()
    }

    fun getUserStatus() {
        getNotifications()
        obsIsLogin.set(true)
    }

    fun onLoginClicked() {
        setValue(Codes.LOGIN_CLICKED)
    }

    private fun getNotifications()
    {
        obsIsEmpty.set(false)
        obsIsFull.set(false)
        obsIsLoading.set(true)
        requestCall<NotificationResponse?>({
            withContext(Dispatchers.IO) {
                return@withContext getApiRepo().getNotifications(30 , 0, PrefMethods.getUserData()!!.id!!)
            }
        })
        { res ->

            obsIsLoading.set(false)
            when (res!!.isSuccess) {
                true -> {
                    res.let {
                        when {
                            it.notificationsList!!.isNotEmpty() -> {
                                obsIsFull.set(true)
                                obsIsLoadingStores.set(false)
                                obsHideRecycler.set(true)
                                notificationsList = res.notificationsList as ArrayList<NotificationItem>
                                notificationAdapter.updateList(notificationsList)
                            }
                            else -> {
                                obsIsEmpty.set(true)
                                obsIsFull.set(false)
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }

    fun makeReadNotification(item : NotificationItem)
    {
        val readRequest = ReadNotificationRequest(item.id, item.orderId, item.userId)
        requestCall<ReadNotificationResponse?>({ withContext(Dispatchers.IO) { return@withContext getApiRepo().readNotification(readRequest) } })
        { res ->

            obsIsLoading.set(false)
            when (res!!.isSuccess) {
                true -> {
                }

                else -> {}
            }
        }
    }
}