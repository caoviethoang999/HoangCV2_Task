package com.example.hoangcv2_task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.hoangcv2_task.database.AccountActivityDatabase
import com.example.hoangcv2_task.database.AccountActivityRepository
import com.example.hoangcv2_task.model.AccountActivity
import com.example.hoangcv2_task.model.AccountStatus
import com.example.hoangcv2_task.model.AccountTest
import java.util.*

class AccountActivityViewModel(private val accountActivityRepository: AccountActivityRepository) :
    ViewModel() {

    var accountActivityList = MutableLiveData<MutableList<AccountActivity>>()
    var accountStatusList = MutableLiveData<MutableList<AccountStatus>>()
    var accountTestList = MutableLiveData<MutableList<AccountTest>>()
    var accountActivityRemoteList = MutableLiveData<MutableList<AccountActivity>>()
    var accountStatusRemoteList = MutableLiveData<MutableList<AccountStatus>>()
    fun insertAccountActivity(accountActivity: AccountActivity) {
        accountActivityRepository.insertAccountActivity(accountActivity)
    }

    fun insertAccountStatus(accountStatus: AccountStatus) {
        accountActivityRepository.insertAccountStatus(accountStatus)
    }

    fun selectAllAccountActivity() {
        accountActivityList = accountActivityRepository.getAllAccountActivity()
    }

    fun selectAllAccountActivityRemote() {
        accountActivityRemoteList = accountActivityRepository.getAllAccountActivityRemote()
    }


    fun selectAllAccountStatus() {
        accountStatusList = accountActivityRepository.getAllAccountStatus()
    }

    fun selectAllAccountStatusRemote() {
        accountStatusRemoteList = accountActivityRepository.getAllAccountStatusRemote()
    }
    fun getAllDataTest() {
        accountTestList=accountActivityRepository.getAllData()
    }

    val productList=accountActivityRepository.productList
}