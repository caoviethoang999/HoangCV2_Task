package com.example.hoangcv2_task.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hoangcv2_task.R
import com.example.hoangcv2_task.databinding.ItemAccountActivityBinding
import com.example.hoangcv2_task.model.AccountActivity
import com.example.hoangcv2_task.model.AccountTest
import kotlinx.android.synthetic.main.item_account_activity.view.*

class AccountActivityPagingAdapter:PagingDataAdapter<AccountActivity, AccountActivityPagingAdapter.AccountActivityViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AccountActivity>(){

            override fun areItemsTheSame(oldItem: AccountActivity, newItem: AccountActivity): Boolean {
                return oldItem.accountID == newItem.accountID
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: AccountActivity, newItem: AccountActivity): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountActivityViewHolder {
        val binding =
            ItemAccountActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountActivityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountActivityViewHolder, position: Int) {
        Log.d("TEST POSITION",position.toString())
        val accountActivity = getItem(position)
        if (accountActivity != null) {
            holder.bind(accountActivity)
        }
    }

    class AccountActivityViewHolder(val binding: ItemAccountActivityBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(accountActivity: AccountActivity) {
        binding.accountName.text = accountActivity.accountName
        binding.accountID.text = accountActivity.accountID
    }
        }
}