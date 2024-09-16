package com.brandsin.driver.ui.auth.authways

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.brandsin.driver.R
import com.brandsin.driver.databinding.AuthFragmentLoginWaysBinding
import com.brandsin.driver.ui.activity.auth.BaseAuthFragment

class LoginWaysFragment : BaseAuthFragment()
{
    lateinit var binding: AuthFragmentLoginWaysBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{

        binding = DataBindingUtil.inflate(inflater, R.layout.auth_fragment_login_ways, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.ways_to_login)
        }

        binding.btnRegisterStore.setOnClickListener {
            val action = LoginWaysFragmentDirections.waysToRegister("1")
            findNavController().navigate(action)
//            findNavController().navigate(R.id.ways_to_register)
        }

        binding.btnRegisterSingle.setOnClickListener {
            val action = LoginWaysFragmentDirections.waysToRegister("0")
            findNavController().navigate(action)
//            findNavController().navigate(R.id.ways_to_register)
        }
    }
}