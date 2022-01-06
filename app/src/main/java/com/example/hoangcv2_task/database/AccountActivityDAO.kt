package com.example.hoangcv2_task.database

import androidx.room.*
import com.example.hoangcv2_task.model.AccountActivity
import com.example.hoangcv2_task.model.AccountStatus
import io.reactivex.Flowable
import java.util.*

@Dao
interface AccountActivityDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccountActivity(accountActivity: AccountActivity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccountStatus(accountStatus: AccountStatus)

    @Query("SELECT * FROM tblAccountActivity WHERE accountDate=:accountDate")
    fun getAllAccountActivityByDate(accountDate: Date): Flowable<MutableList<AccountActivity>>

    @Query("SELECT * FROM tblAccountActivity")
    fun getAllAccountActivity(): Flowable<MutableList<AccountActivity>>

    @Query("SELECT * FROM tblAccountStatus")
    fun getAllAccountStatus(): Flowable<MutableList<AccountStatus>>

    @Query("SELECT * FROM tblAccountActivity")
    fun getAllAccountActivity2(): Flowable<List<AccountActivity>>

    @Query("SELECT * FROM tblAccountStatus")
    fun getAllAccountStatus2(): Flowable<List<AccountStatus>>
}
