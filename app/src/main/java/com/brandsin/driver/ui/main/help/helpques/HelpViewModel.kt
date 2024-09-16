package com.brandsin.driver.ui.main.help.helpques

import androidx.databinding.ObservableField
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.network.requestCall
import com.brandsin.driver.model.menu.help.HelpQuesItem
import com.brandsin.driver.model.menu.help.HelpQuesResponse
import com.brandsin.driver.model.menu.settings.PhoneNumberResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class HelpViewModel : BaseViewModel()
{
    var helpAdapter  = HelpAdapter()
    var obsHelpMsg = ObservableField<String>()
    var phoneNumber : String? = null

    private var isFirstTime = true

    init {
        when {
            isFirstTime -> {
                isFirstTime = false
                getHelpQues()
                getPhoneNumber()
            }
        }
    }

    fun getHelpQues()
    {
        obsIsFull.set(false)
        obsIsLoading.set(true)
        requestCall<HelpQuesResponse?>({ withContext(Dispatchers.IO) { return@withContext getApiRepo().getHelpQues("page",
                PrefMethods.getLanguage()) } })
        { res ->
            when (res!!.isSuccess) {
                true -> {
                    res.let {
                        when {
                            it.quesList!!.isNotEmpty() -> {
                                obsIsLoading.set(false)
                                obsIsFull.set(true)
                                helpAdapter.updateList(res.quesList as ArrayList<HelpQuesItem>)
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }

    fun getPhoneNumber()
    {
        requestCall<PhoneNumberResponse?>({ withContext(Dispatchers.IO) { return@withContext getApiRepo().getPhoneNumber("phone_number" , PrefMethods.getLanguage()) } })
        { res ->
            when (res!!.isSuccess) {
                true -> {
                    phoneNumber = res.phoneNumber.toString()
                }

                else -> {}
            }
        }
    }

    fun onSendMessageClicked()
    {
        when {
            obsHelpMsg.get() == null -> {
                setValue(Codes.EMPTY_MESSAGE)
            }
            else -> {
                obsIsVisible.set(true)

                /*
                *
                *
                * This api replaces with send message api
                *
                * */
                requestCall<HelpQuesResponse?>({ withContext(Dispatchers.IO) { return@withContext getApiRepo().getHelpQues("page",
                        PrefMethods.getLanguage()) } })
                { res ->
                    obsIsVisible.set(true)
                    when (res!!.isSuccess) {
                        true -> {
                            res.let {
                                when {
                                    it.quesList!!.isNotEmpty() -> {
                                        obsIsLoading.set(false)
                                        obsIsFull.set(true)
                                        helpAdapter.updateList(res.quesList as ArrayList<HelpQuesItem>)
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}