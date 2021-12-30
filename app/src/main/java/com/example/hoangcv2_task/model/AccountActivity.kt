package com.example.hoangcv2_task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tblAccountActivity")
class AccountActivity(
    @ColumnInfo(name = "accountName")
    var accountName: String,
    @ColumnInfo(name = "accountStatus")
    var accountStatus: String,
    @ColumnInfo(name = "accountDate")
    var accountDate: Date,
    @PrimaryKey
    @ColumnInfo(name = "accountID")
    var accountID: String
)