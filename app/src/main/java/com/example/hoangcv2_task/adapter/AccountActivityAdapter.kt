package com.example.hoangcv2_task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hoangcv2_task.OnItemClickListener
import com.example.hoangcv2_task.R
import com.example.hoangcv2_task.databinding.ItemAccountActivityBinding
import com.example.hoangcv2_task.model.AccountTest
import kotlin.collections.ArrayList


class AccountActivityAdapter(private var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AccountActivityAdapter.ItemViewHolder>() {
    private var list: MutableList<AccountTest>
    fun getAll(list: MutableList<AccountTest>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemAccountActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var account: AccountTest = list[position]
            holder.binding.status.text = account.accountStatus.statusName
        if(account.accountStatus.statusName.equals("pending",true)){
            holder.binding.constraintLayoutStatus.setBackgroundResource(R.drawable.background_status_pending)
        }else if(account.accountStatus.statusName.equals("success",true)){
            holder.binding.constraintLayoutStatus.setBackgroundResource(R.drawable.background_status_success)
        }else if(account.accountStatus.statusName.equals("failed",true)) {
            holder.binding.constraintLayoutStatus.setBackgroundResource(R.drawable.background_status_failed)
        }
            holder.binding.accountName.text = account.accountActivity.accountName
            holder.binding.accountID.text = account.accountActivity.accountID
        holder.binding.viewDetail.setOnClickListener {
//            val dialog: Dialog = Dialog(holder.itemView.context)
//            dialog.setContentView(R.layout.custom_dialog)
//            dialog.show()
//            val window: Window? = dialog.window
//            window?.setLayout(
//                ActionBar.LayoutParams.MATCH_PARENT,
//                ActionBar.LayoutParams.WRAP_CONTENT
//            );
//            window?.setGravity(Gravity.BOTTOM)
            onItemClickListener.onItemClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder(val binding: ItemAccountActivityBinding) :
        RecyclerView.ViewHolder(binding.root)

    init {
        list = ArrayList<AccountTest>()
    }
}