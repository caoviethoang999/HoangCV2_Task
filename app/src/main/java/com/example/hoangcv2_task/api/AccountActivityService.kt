package com.example.hoangcv2_task.api

import com.example.hoangcv2_task.model.AccountActivity
import com.example.hoangcv2_task.model.AccountStatus
import io.reactivex.Flowable
import retrofit2.http.*

interface AccountActivityService {
    @GET("/HoangCV2_Task/displayAccountActivity.php")
    fun getAccountActivity(): Flowable<MutableList<AccountActivity>>

    @GET("/HoangCV2_Task/displayAccountStatus.php")
    fun getAccountStatus(): Flowable<MutableList<AccountStatus>>

}