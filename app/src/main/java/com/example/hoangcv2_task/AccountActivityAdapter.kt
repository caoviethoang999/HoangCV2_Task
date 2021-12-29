package com.example.hoangcv2_task

import android.app.ActionBar
import android.app.Dialog
import android.graphics.drawable.BitmapDrawable
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.hoangcv2_task.databinding.ItemAccountActivityBinding
import java.util.*


class AccountActivityAdapter :
    RecyclerView.Adapter<AccountActivityAdapter.ItemViewHolder>() {
    private var list: MutableList<AccountActivity>
    fun getAll(list: MutableList<AccountActivity>?) {
        this.list = list!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemAccountActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val accountActivityList: AccountActivity = list[position]
        holder.binding.accountName.text = accountActivityList.accountName
        holder.binding.status.text = accountActivityList.status
        holder.binding.accountID.text = accountActivityList.accountID
        holder.binding.viewDetail.setOnClickListener {
            val dialog:Dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.custom_dialog)
            dialog.show()
            val window: Window? = dialog.window
            window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            window?.setGravity(Gravity.BOTTOM)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ItemViewHolder(val binding: ItemAccountActivityBinding) : RecyclerView.ViewHolder(binding.root)

    init {
        list = ArrayList<AccountActivity>()
    }
}