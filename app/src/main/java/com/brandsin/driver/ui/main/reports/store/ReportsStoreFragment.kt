package com.brandsin.driver.ui.main.reports.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brandsin.driver.R
import com.brandsin.driver.databinding.ReportsFragmentStoreBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.dialogs.dailytime.DialogDailyTimeFragment
import com.brandsin.driver.ui.dialogs.monthstime.DialogReportTimeMonthlyFragment
import com.brandsin.driver.utils.Utils

class ReportsStoreFragment : Fragment(), Observer<Any?>
{
    private lateinit var viewModel: ReportsStoreViewModel
    private lateinit var binding : ReportsFragmentStoreBinding
    var isDaily : Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.reports_fragment_store, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReportsStoreViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)

        // Get radio group selected item using on checked change listener
        binding.rbChoices.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = requireActivity().findViewById(checkedId)
            if (radio.id == R.id.rb_daily)
            {
                isDaily = 1
//                viewModel.getDailyReports()
            }
            if (radio.id == R.id.rb_monthly)
            {
                isDaily = 2
//                viewModel.getMonthlyReports()
            }
        })
    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        it.let {
            if (it is Int)
            {
                when (it)
                {
                    Codes.SHOW_CALENDAR_DIALOG ->
                    {
                        if (isDaily == 1)
                        {
                            Utils.startDialogActivity(requireActivity(), DialogDailyTimeFragment::class.java.name , Codes.SHOW_DAILY_FILTER , null)
                        }
                        else
                        {
                            Utils.startDialogActivity(requireActivity(), DialogReportTimeMonthlyFragment::class.java.name , Codes.SHOW_MONTH_FILTER , null)
                        }
                    }
                }
            }
        }
    }
}