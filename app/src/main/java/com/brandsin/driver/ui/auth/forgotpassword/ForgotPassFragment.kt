package com.brandsin.driver.ui.auth.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.brandsin.driver.R
import com.brandsin.driver.databinding.AuthFragmentForgotBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.activity.auth.BaseAuthFragment

class ForgotPassFragment : BaseAuthFragment(), Observer<Any?> {
    lateinit var binding: AuthFragmentForgotBinding
    lateinit var viewModel: ForgotViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.auth_fragment_forgot, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForgotViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)

        setBarName(getString(R.string.forgot_password))

        viewModel.showProgress().observe(viewLifecycleOwner, { aBoolean ->
            if (!aBoolean!!) {
                binding.progressLayout.visibility = View.GONE
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } else {
                binding.progressLayout.visibility = View.VISIBLE
                requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        })
    }

    override fun onChanged(it: Any?) {
        if (it == null) return
        when (it) {
            Codes.EMPTY_PHONE -> {
                showToast(getString(R.string.empty_phone), 1)
            }
            Codes.INVALID_PHONE -> {
                showToast(getString(R.string.invalid_phone), 1)
            }
            Codes.FORGOT_SUCCESS -> {
                showToast(viewModel.code, 2)

                val action = ForgotPassFragmentDirections
                    .forgotToVerify(
                        viewModel.request.phone_email.toString(),
                        viewModel.userId,
                        "forgot"
                    )
                findNavController().navigate(action)
                viewModel.setShowProgress(false)
            }
            else -> {
                if (it is String) {
                    showToast(it.toString(), 1)
                }
                viewModel.setShowProgress(false)
            }
        }
    }
}
