package com.example.hoangcv2_task.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hoangcv2_task.database.AccountActivityRepository

class AccountActivityViewModelFactory(
    private val repository: AccountActivityRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor =
                modelClass.getDeclaredConstructor(AccountActivityRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message.toString())
        }
        return super.create(modelClass)
    }
}