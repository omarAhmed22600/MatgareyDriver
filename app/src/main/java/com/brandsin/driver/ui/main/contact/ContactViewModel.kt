package com.brandsin.driver.ui.main.contact

import androidx.databinding.ObservableField
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.network.requestCall
import com.brandsin.driver.model.menu.settings.PhoneNumberResponse
import com.brandsin.driver.model.menu.settings.SocialLinks
import com.brandsin.driver.model.menu.settings.SocialLinksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactViewModel : BaseViewModel()
{
    var socialLinks = SocialLinks()
    var obsPhoneNumber = ObservableField<String>()

    fun getSocialLinks()
    {
        obsIsFull.set(false)
        obsIsLoading.set(true)
        requestCall<SocialLinksResponse?>({ withContext(Dispatchers.IO) { return@withContext getApiRepo().getSocialLinks("social_links" , PrefMethods.getLanguage()) } })
        { res ->
            when (res!!.isSuccess) {
                true -> {
                    obsIsFull.set(true)
                    obsIsLoading.set(false)
                    socialLinks = res.socialLinks!!
                    setValue(Codes.SHOW_SOCIAL)
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
                    obsPhoneNumber.set(res.phoneNumber.toString())
                }

                else -> {}
            }
        }
    }

    fun onPhoneClicked()
    {
        setValue(Codes.PHONE_CLICKED)
    }

    fun onFaceClicked()
    {
        setValue(Codes.FACE_CLICKED)
    }

    fun onTikTokClicked()
    {
        setValue(Codes.TIKTOK_CLICKED)
    }

    fun onWhatsClicked()
    {
        setValue(Codes.WHATSAPP_CLICKED)
    }

    fun onTwitterClicked()
    {
        setValue(Codes.TWITTER_CLICKED)
    }

    fun onGmailClicked()
    {
        setValue(Codes.GMAIL_CLICKED)
    }

    init {
        getSocialLinks()
        getPhoneNumber()
    }
}