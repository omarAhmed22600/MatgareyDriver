package com.brandsin.driver.ui.main.about

import androidx.databinding.ObservableField
import com.brandsin.driver.R
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.menu.about.AboutItem
import com.brandsin.driver.network.requestCall
import com.brandsin.driver.utils.MyApp.Companion.context
import com.brandsin.driver.utils.PrefMethods
import com.brandsin.driver.model.menu.settings.SocialLinks
import com.brandsin.driver.model.menu.settings.SocialLinksResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AboutViewModel : BaseViewModel()
{
    var aboutAdapter  = AboutAdapter()
    var obsVersion = ObservableField<String>()
    var socialLinks = SocialLinks()

    private fun getAboutItems() {
        getSocialLinks()
        val aboutList = arrayListOf(
            AboutItem(1, context.getString(R.string.common_questions)),
           AboutItem(2, context.getString(R.string.share_your_rate_about_app))
        )
        aboutAdapter.updateList(aboutList)
    }

    init {
        getAboutItems()
    }

    fun onFaceClicked() {
        setValue(Codes.OPEN_FACE)
    }

    fun onInstaClicked() {
        setValue(Codes.OPEN_INSTA)
    }

    fun onTwitterClicked() {
        setValue(Codes.OPEN_TWITTER)
    }


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
                }

                else -> {}
            }
        }
    }
}