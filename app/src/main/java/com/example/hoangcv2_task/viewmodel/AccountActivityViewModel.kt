package com.example.hoangcv2_task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hoangcv2_task.database.AccountActivityRepository
import com.example.hoangcv2_task.model.AccountActivity
import java.util.*

class AccountActivityViewModel(private val accountActivityRepository: AccountActivityRepository) :
    ViewModel() {

    var accountActivityList = MutableLiveData<MutableList<AccountActivity>>()
    var accountActivityListByDate = MutableLiveData<MutableList<AccountActivity>>()

    fun insertAccountActivity(accountActivity: AccountActivity) {
        accountActivityRepository.insertAccountActivity(accountActivity)
    }

    fun selectAllAccountActivity() {
        accountActivityList = accountActivityRepository.getAllAccountActivity()
    }

    fun selectAllAccountActivityByDate(accountDate: Date) {
        accountActivityListByDate =
            accountActivityRepository.getAllAccountActivityByDate(accountDate)
    }
}