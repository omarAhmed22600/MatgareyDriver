package com.brandsin.driver.ui.main.rateapp

import androidx.databinding.ObservableField
import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.constants.Codes

class RateAppViewModel : BaseViewModel()
{
    val obsRate = ObservableField<Float>()
    val obsMsg = ObservableField<String>()

    init {
        obsRate.set(5f)
    }

    fun onRateClicked()
    {
        setValue(Codes.RATING_SUCCESS)
    }
}