package com.brandsin.driver.ui.main.reports.personal

import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.model.constants.Codes
import com.brandsin.driver.model.main.reports.DataItem
import com.brandsin.driver.model.main.reports.OrderReportsResponse
import com.brandsin.driver.network.requestCall
import com.brandsin.driver.utils.PrefMethods
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReportsPersonalViewModel : BaseViewModel()
{
    var reportsAdapter  = ReportsAdapter()
    var reportsList= ArrayList<DataItem>()
    var type = "daily" // monthly
    var from = ""
    var to = ""

    fun getReports()
    {
        setShowProgress(true)
        obsIsFull.set(false)
        obsIsEmpty.set(false)
        requestCall<OrderReportsResponse?>({ withContext(Dispatchers.IO) {
            return@withContext getApiRepo()
                    .getReports(PrefMethods.getUserData()!!.driver!!.id!!,50,0,type,from, to) }
        })
        { res ->
            when  {
                res!!.data!!.isNotEmpty() -> {
                    obsIsFull.set(true)
                    obsIsEmpty.set(false)
                    setShowProgress(false)

                    reportsList = res.data as ArrayList<DataItem>
                    reportsAdapter.updateList(reportsList)
                    reportsAdapter.notifyDataSetChanged()

                }else -> {
                    obsIsFull.set(false)
                    obsIsEmpty.set(true)
                    setShowProgress(false)
                }
            }
        }
    }

    init {
        getReports()
    }

    fun onCalendarClicked()
    {
        setValue(Codes.SHOW_CALENDAR_DIALOG)
    }
}