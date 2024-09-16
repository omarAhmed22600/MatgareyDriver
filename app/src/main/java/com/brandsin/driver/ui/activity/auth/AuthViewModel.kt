package com.brandsin.driver.ui.activity.auth

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.brandsin.driver.database.BaseViewModel

class AuthViewModel : BaseViewModel()
{
    val obsShowToolbar = ObservableBoolean()
    val obsTitle = ObservableField<String>()
}