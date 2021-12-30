package com.example.hoangcv2_task.database

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.hoangcv2_task.model.AccountActivity
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class AccountActivityRepository(private val accountActivityDatabase: AccountActivityDatabase) {

    var accountActivityList = MutableLiveData<MutableList<AccountActivity>>()
    var accountActivityListByDate = MutableLiveData<MutableList<AccountActivity>>()

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
    fun getAllAccountActivityByDate(accountDate: Date): MutableLiveData<MutableList<AccountActivity>> {
        accountActivityDatabase.AccountActivityDAO().getAllAccountActivityByDate(accountDate)
            .subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                Log.d(TAG, "accept: Test")
                accountActivityListByDate.postValue(t)
            }
        return accountActivityListByDate
    }
}

