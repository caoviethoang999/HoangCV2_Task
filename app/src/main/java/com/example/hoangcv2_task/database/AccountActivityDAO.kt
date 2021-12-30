package com.example.hoangcv2_task.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hoangcv2_task.model.AccountActivity
import io.reactivex.Flowable
import java.util.*

@Dao
interface AccountActivityDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccountActivity(accountActivity: AccountActivity)

    @Query("SELECT * FROM tblAccountActivity WHERE accountDate=:accountDate")
    fun getAllAccountActivityByDate(accountDate: Date): Flowable<MutableList<AccountActivity>>

    @Query("SELECT * FROM tblAccountActivity")
    fun getAllAccountActivity(): Flowable<MutableList<AccountActivity>>
}
