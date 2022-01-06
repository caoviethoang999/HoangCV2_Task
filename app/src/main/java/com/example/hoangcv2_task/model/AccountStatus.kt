package com.example.hoangcv2_task.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tblAccountStatus")
class AccountStatus(
    @PrimaryKey
    @ColumnInfo(name = "statusID")
    @SerializedName("statusID")
    var statusID: Int?,
    @ColumnInfo(name = "statusName")
    @SerializedName("statusName")
    var statusName:String
        ): Serializable