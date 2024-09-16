package com.brandsin.driver.ui.main.coomonquestions

import com.brandsin.driver.database.BaseViewModel
import com.brandsin.driver.network.requestCall
import com.brandsin.driver.model.menu.commonquest.CommonQuesResponse
import com.brandsin.driver.model.menu.commonquest.CommonQuestionItem
import com.brandsin.driver.utils.PrefMethods
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommonQuestionViewModel : BaseViewModel()
{
    var quesAdapter = CommonQuesAdapter()

    init {
        getCommonQues()
    }

    fun getCommonQues()
    {
        obsIsEmpty.set(false)
        obsIsFull.set(false)
        obsIsLoading.set(true)
        requestCall<CommonQuesResponse?>({ withContext(Dispatchers.IO) { return@withContext getApiRepo().getCommonQues("faq",
                PrefMethods.getLanguage()) } })
        { res ->
            when (res!!.isSuccess) {
                true -> {
                    res.let {
                        when {
                            it.questionsList!!.isNotEmpty() -> {
                                obsIsLoading.set(false)
                                obsIsFull.set(true)
                                quesAdapter.updateList(res.questionsList as ArrayList<CommonQuestionItem>)
                            }
                            else -> {
                                obsIsFull.set(false)
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}