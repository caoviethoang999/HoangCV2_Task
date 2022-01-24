package com.example.hoangcv2_task.database

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.viewbinding.ViewBinding
import com.example.hoangcv2_task.CustomProgressBar
import com.example.hoangcv2_task.NetworkConfig
import com.example.hoangcv2_task.api.AccountActivityService
import com.example.hoangcv2_task.databinding.FragmentAccountActivityBinding
import com.example.hoangcv2_task.model.AccountActivity
import com.example.hoangcv2_task.model.AccountStatus
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import com.example.hoangcv2_task.model.AccountTest
import io.reactivex.Flowable

import kotlin.collections.ArrayList


class AccountActivityRepository(private val accountActivityDatabase: AccountActivityDatabase,private val accountActivityService: AccountActivityService,private val binding: FragmentAccountActivityBinding) {

    var accountActivityList = MutableLiveData<MutableList<AccountActivity>>()
    var accountStatusList = MutableLiveData<MutableList<AccountStatus>>()
    var accountActivityRemoteList = MutableLiveData<MutableList<AccountActivity>>()
    var accountStatusRemoteList = MutableLiveData<MutableList<AccountStatus>>()
    var accountTestList = MutableLiveData<MutableList<AccountTest>>()

    fun insertAccountActivity(accountActivity: AccountActivity) {
        Completable.fromAction {
            accountActivityDatabase.AccountActivityDAO().insertAccountActivity(accountActivity)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                        Log.d(TAG, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message);
                }

            })
    }
    fun insertAccountStatus(accountStatus: AccountStatus) {
        Completable.fromAction {
            accountActivityDatabase.AccountActivityDAO().insertAccountStatus(accountStatus)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe: Called")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: Called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message);
                }

            })
    }

    @SuppressLint("CheckResult")
    fun getAllAccountActivity(): MutableLiveData<MutableList<AccountActivity>> {
        accountActivityDatabase.AccountActivityDAO().getAllAccountActivity()
            .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                Log.d(TAG, "accept: Called")
                accountActivityList.postValue(t)
            }
        return accountActivityList
    }

    @SuppressLint("CheckResult")
    fun getAllAccountStatus(): MutableLiveData<MutableList<AccountStatus>> {
        accountActivityDatabase.AccountActivityDAO().getAllAccountStatus()
            .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                Log.d(TAG, "accept: Called")
                accountStatusList.postValue(t)
            }
        return accountStatusList
    }

    @SuppressLint("CheckResult")
    fun getAllData():MutableLiveData<MutableList<AccountTest>> {
        val listObservable: Flowable<MutableList<AccountStatus>> = accountActivityDatabase.AccountActivityDAO().getAllAccountStatus()
        val listObservable2: Flowable<MutableList<AccountActivity>> = accountActivityDatabase.AccountActivityDAO().getAllAccountActivity()
        Flowable.zip(listObservable, listObservable2,
            { t1, t2 ->
                val listAccountTest:MutableList<AccountTest> = ArrayList<AccountTest>()
                for(accountStatus in t1){
                    for(accountActivity in t2){
                        if(accountStatus.statusID == accountActivity.accountWithStatusID){
                            listAccountTest.add(AccountTest(
                                AccountActivity(accountActivity.accountName,accountActivity.accountWithStatusID,accountActivity.accountDate,accountActivity.accountID),AccountStatus(accountStatus.statusID,accountStatus.statusName)))
                        }
                    }
                }
                listAccountTest
            })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                accountTestList.postValue(t) }
        return accountTestList
    }
    @SuppressLint("CheckResult")
    fun getAllAccountStatusRemote(): MutableLiveData<MutableList<AccountStatus>> {
        accountActivityService.getAccountStatus()
            .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                Log.d(TAG, "accept: Called")
                accountStatusRemoteList.postValue(t)
            }
        return accountStatusRemoteList
    }
    @SuppressLint("CheckResult")
    fun getAllAccountActivityRemote(): MutableLiveData<MutableList<AccountActivity>> {
        accountActivityService.getAccountActivity()
            .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                Log.d(TAG, "accept: Called")
                accountActivityRemoteList.postValue(t)
            }
        return accountActivityRemoteList
    }

    val productList = Pager(
        PagingConfig(
            pageSize = 100,
            enablePlaceholders = true,
            maxSize = 1000
        )
    ) {
        accountActivityDatabase.AccountActivityDAO().getListAccountActivity()
    }.flow

}

