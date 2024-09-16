package com.brandsin.driver.ui.main.reports.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brandsin.driver.R
import com.brandsin.driver.databinding.ReportsFragmentPersonalBinding
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.ui.activity.BaseHomeFragment
import com.brandsin.driver.ui.dialogs.dailytime.DialogDailyTimeFragment
import com.brandsin.driver.ui.dialogs.monthstime.DialogReportTimeMonthlyFragment
import com.brandsin.driver.utils.Utils

class ReportsPersonalFragment: BaseHomeFragment(), Observer<Any?>
{
    private lateinit var viewModel: ReportsPersonalViewModel
    private lateinit var binding : ReportsFragmentPersonalBinding
    var isDaily : Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.reports_fragment_personal, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReportsPersonalViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.mutableLiveData.observe(viewLifecycleOwner, this)

        viewModel.showProgress().observe(viewLifecycleOwner, { aBoolean ->
            if (!aBoolean!!) {
                binding.progressLayout.visibility = View.GONE
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            } else {
                binding.progressLayout.visibility = View.VISIBLE
                requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        })

        // Get radio group selected item using on checked change listener
        binding.rbChoices.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = requireActivity().findViewById(checkedId)
            if (radio.id == R.id.rb_daily)
            {
                isDaily = 1
                viewModel.type = "daily"
                viewModel.getReports()
            }
            if (radio.id == R.id.rb_monthly)
            {
                isDaily = 2
                viewModel.type = "monthly"
                viewModel.getReports()
            }
        })
    }

    override fun onChanged(it: Any?)
    {
        if(it == null) return
        it.let {
            if (it is Int) {
                when (it) {
                    Codes.SHOW_CALENDAR_DIALOG -> {
                        if (isDaily == 1) {
                            val bundle = Bundle()
                            Utils.startDialogActivity(requireActivity(), DialogDailyTimeFragment::class.java.name , Codes.SHOW_DAILY_FILTER , bundle)
                        } else {
                            val bundle = Bundle()
                            Utils.startDialogActivity(requireActivity(), DialogReportTimeMonthlyFragment::class.java.name , Codes.SHOW_MONTH_FILTER , bundle)
                        }
                    }
                }
            }
        }
    }

}