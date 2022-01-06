package com.example.hoangcv2_task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "tblAccountActivity")
class AccountActivity(

    @ColumnInfo(name = "accountName")
    @SerializedName("accountName")
    var accountName: String,
    @ColumnInfo(name = "accountWithStatusID")
    @SerializedName("accountWithStatusID")
    var accountWithStatusID: Int,
    @ColumnInfo(name = "accountDate")
    @SerializedName("accountDate")
    var accountDate: Date,
    @PrimaryKey
    @ColumnInfo(name = "accountID")
    @SerializedName("accountID")
    var accountID: String
): Serializable