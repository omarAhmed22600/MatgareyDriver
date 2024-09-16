package com.brandsin.driver.ui.main.help.helpques

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.brandsin.driver.R
import com.brandsin.driver.databinding.MenuFragmentHelpBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.store.network.Status
import com.brandsin.driver.network.observe
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.model.menu.help.HelpQuesItem
import timber.log.Timber

class HelpFragment : BaseHomeFragment(), Observer<Any?>
{
    private lateinit var viewModel: HelpViewModel
    private lateinit var binding: MenuFragmentHelpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.menu_fragment_help, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        binding.viewModel = viewModel

       setBarName(getString(R.string.help))

        viewModel.helpAdapter.helpLiveData.observe(viewLifecycleOwner, this)

        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = false
            viewModel.getHelpQues()
            viewModel.getPhoneNumber()
        }

        observe(viewModel.apiResponseLiveData) {
            when (it.status) {
                Status.ERROR_MESSAGE -> {
                    showToast(it.message.toString(), 1)
                }
                Status.SUCCESS_MESSAGE -> {
                    showToast(it.message.toString(), 2)
                }
                else -> {
                    Timber.e(it.message)
                }
            }
        }
    }

    override fun onChanged(it: Any?) {
        if(it == null) return
        when (it) {
            is HelpQuesItem -> {
                val action = HelpFragmentDirections.helpToAnswers(viewModel.phoneNumber.toString(), it)
                findNavController().navigate(action)
            }
            Codes.EMPTY_MESSAGE -> {
                showToast(getString(R.string.please_enter_your_message), 1)
            }
        }
    }
}