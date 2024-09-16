package com.brandsin.driver.ui.profile

import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.constants.Codes

class ProfileViewModel : BaseViewModel()
{


    fun onLanguageClicked()
    {
        setValue(Codes.LANGUAGE_CLICKED)
    }

    fun onProfileClicked()
    {
        setValue(Codes.EDIT_CLICKED)
    }

    fun onVehicleClicked()
    {
        setValue(Codes.VEHICLE_CLICKED)
    }

    fun onChangePassClicked()
    {
        setValue(Codes.CHANGE_PASS_CLICKED)
    }
}