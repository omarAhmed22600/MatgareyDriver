package com.brandsin.driver.ui.dialogs.monthstime

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.brandsin.driver.databinding.DialogReportsMonthlyDateBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.constants.Params
import java.util.*

class DialogReportTimeMonthlyFragment(val date: Date = Date())  : DialogFragment()
{
    lateinit  var  binding: DialogReportsMonthlyDateBinding
    var month = ""
    var year = ""
    var from = ""
    var to = ""

    companion object {
        private const val MAX_YEAR = 2099
    }
    private var listener: DatePickerDialog.OnDateSetListener? = null

    fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = DialogReportsMonthlyDateBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cal: Calendar = Calendar.getInstance().apply { time = date }

        binding.pickerMonth.run {
            minValue = 1
            maxValue = 12
            value = cal.get(Calendar.MONTH)
            displayedValues = arrayOf("Jan","Feb","Mar","Apr","May","June","July", "Aug","Sep","Oct","Nov","Dec") }

        binding.pickerYear.run {
            val year = cal.get(Calendar.YEAR)
            minValue = year
            maxValue = MAX_YEAR
            value = year
        }

         AlertDialog.Builder(requireContext())
                .setView(binding.root)
                .create()

        binding.btnSearch.setOnClickListener {
            listener?.onDateSet(null, binding.pickerYear.value, binding.pickerMonth.value, 1)
            year = binding.pickerYear.value.toString()
            month = binding.pickerMonth.value.toString()
            if (month.length ==1){
                month = "0$month"
            }
            from = "$year-$month-01"
            to = "$year-$month-31"
            val intent = Intent()
            intent.putExtra(Params.DIALOG_CLICK_ACTION, 1)
            intent.putExtra("from", from)
            intent.putExtra("to", to)
            requireActivity().setResult(Codes.SHOW_MONTH_FILTER, intent)
            requireActivity().finish()
        }
    }
}