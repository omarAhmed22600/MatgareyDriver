package com.brandsin.driver.ui.dialogs.finishorder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.brandsin.driver.databinding.DialogFinishOrderBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.constants.Params

class DialogFinishOrderFragment : DialogFragment(), Observer<Any?>
{
    lateinit  var  binding: DialogFinishOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when {
            arguments != null -> {
                when {

                }
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = DialogFinishOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirm.setOnClickListener {
            val intent = Intent()
            intent.putExtra(Params.DIALOG_CLICK_ACTION, 1)
            requireActivity().setResult(Codes.DIALOG_FINISH_ORDER_CODE, intent)
            requireActivity().finish()
        }

    }

    override fun onChanged(it: Any?) {
        when (it) {
            null -> return
            else -> when (it) {

            }
        }
    }
}