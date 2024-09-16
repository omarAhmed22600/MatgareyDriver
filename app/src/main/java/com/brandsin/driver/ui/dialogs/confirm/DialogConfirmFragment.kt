package com.brandsin.driver.ui.dialogs.confirm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.brandsin.driver.databinding.DialogConfirmBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.constants.Params

class DialogConfirmFragment  : DialogFragment()
{
    lateinit  var  binding: DialogConfirmBinding
    var message : String = ""
    var positiveBtn : String = ""
    var negativeBtn : String = ""
    var cartId = 0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        if (arguments != null)
        {
            if (requireArguments().containsKey(Params.DIALOG_CONFIRM_MESSAGE))
            {
                message = requireArguments().getString(Params.DIALOG_CONFIRM_MESSAGE, null)
                positiveBtn = requireArguments().getString(Params.DIALOG_CONFIRM_POSITIVE, null)
                negativeBtn = requireArguments().getString(Params.DIALOG_CONFIRM_NEGATIVE, null)
                cartId = requireArguments().getInt(Params.DIALOG_CART_ID, 0)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DialogConfirmBinding.inflate(inflater, container, false)

        binding.tvMessage.text = message
        binding.btnConfirm.text = positiveBtn
        binding.btnIgnore.text = negativeBtn

        binding.btnIgnore.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Params.DIALOG_CLICK_ACTION, 0)
            requireActivity().setResult(Codes.DIALOG_CONFIRM_REQUEST, intent)
            requireActivity().finish()
        }

        binding.btnConfirm.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Params.DIALOG_CLICK_ACTION, 1)
            intent.putExtra(Params.DIALOG_CART_ID, cartId)
            requireActivity().setResult(Codes.DIALOG_CONFIRM_REQUEST, intent)
            requireActivity().finish()
        }
        return binding.root
    }
}