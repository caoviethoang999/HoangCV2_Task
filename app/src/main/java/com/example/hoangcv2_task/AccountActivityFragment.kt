package com.example.hoangcv2_task

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoangcv2_task.databinding.FragmentAccountActivityBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AccountActivityFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAccountActivityBinding
    private lateinit var accountActivityAdapter: AccountActivityAdapter
    private var list: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    private var listCheckDate: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    private var list10DaySelected: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    private var list30DaySelected: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    private var list90DaySelected: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    @SuppressLint("SimpleDateFormat")
    val timeStamp = SimpleDateFormat("yyyy/MM/dd")
    val date: Date = Calendar.getInstance().getTime()
    val dateTest: String = timeStamp.format(date)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtAccountDate.text=dateTest
        addData()
        binding.linearLayoutDaySelect.setBackgroundResource(R.drawable.background_tablayout)
        binding.txt10days.setOnClickListener(this)
        binding.txt30days.setOnClickListener(this)
        binding.txt90days.setOnClickListener(this)
    }

    fun addData(){
        binding.recyclerViewAccountActivity.layoutManager = LinearLayoutManager(requireContext())
        accountActivityAdapter = AccountActivityAdapter()
        list.add(AccountActivity("Acc 1.1234567A","PENDING",timeStamp.parse("2021/12/28")!!,"ID#151320430"))
        list.add(AccountActivity("Acc 1.1234567B","PENDING",timeStamp.parse("2021/12/27")!!,"ID#151320431"))
        list.add(AccountActivity("Acc 1.1234567C","PENDING",timeStamp.parse("2021/12/23")!!,"ID#151320432"))
        list.add(AccountActivity("Acc 1.1234567D","PENDING",timeStamp.parse("2021/12/24")!!,"ID#151320433"))
        list.add(AccountActivity("Acc 1.1234567E","PENDING",timeStamp.parse("2021/12/28")!!,"ID#151320434"))
        for (i in 0 until list.size) {
            val dateConvert:String=timeStamp.format(list[i].date)
            if (dateConvert.equals(dateTest,true)){
                listCheckDate.add(AccountActivity(list[i].accountName,list[i].status,list[i].date,list[i].accountID))
                accountActivityAdapter.getAll(listCheckDate)
                binding.recyclerViewAccountActivity.adapter=accountActivityAdapter
            }
        }
    }
    private fun selectDataDaysSelected(daySelected: Enum.DaySelected){
        when (daySelected) {
            Enum.DaySelected.TEN_DAYS ->{
                for (i in 0 until list.size) {
                    val dateConvert: String = timeStamp.format(list[i].date)
                    if (getDay(dateConvert) < 10 && binding.txt10days.isSelected) {
                        list10DaySelected.add(AccountActivity(list[i].accountName, list[i].status, list[i].date, list[i].accountID))
                    }
                    list90DaySelected.removeAll(list30DaySelected)
                    list30DaySelected.removeAll(list30DaySelected)
                    accountActivityAdapter.getAll(list10DaySelected)
                    binding.recyclerViewAccountActivity.adapter = accountActivityAdapter
                }
            }
            Enum.DaySelected.THIRTY_DAYS ->{
                for (i in 0 until list.size) {
                    val dateConvert:String=timeStamp.format(list[i].date)
                    if(getDay(dateConvert) in 11..29 && binding.txt30days.isSelected){
                        list30DaySelected.add(AccountActivity(list[i].accountName,list[i].status,list[i].date,list[i].accountID))
                    }
                    list90DaySelected.removeAll(list30DaySelected)
                    list10DaySelected.removeAll(list30DaySelected)
                    accountActivityAdapter.getAll(list30DaySelected)
                    binding.recyclerViewAccountActivity.adapter=accountActivityAdapter
                }
            }
            Enum.DaySelected.NINETY_DAYS ->{
                for (i in 0 until list.size) {
                    val dateConvert:String=timeStamp.format(list[i].date)
                    if(getDay(dateConvert) in 29..89 && binding.txt90days.isSelected){
                        list90DaySelected.add(AccountActivity(list[i].accountName,list[i].status,list[i].date,list[i].accountID))
                    }
                    list30DaySelected.removeAll(list30DaySelected)
                    list10DaySelected.removeAll(list30DaySelected)
                    accountActivityAdapter.getAll(list90DaySelected)
                    binding.recyclerViewAccountActivity.adapter=accountActivityAdapter
                }
            }
        }
    }
    fun selectDay(){
        if(binding.txt10days.isSelected){
            binding.txt10days.setBackgroundResource(R.drawable.background_textlayoutselected)
            binding.txt10days.setTextColor(resources.getColor(R.color.dark_electric_green))
        }else{
            binding.txt10days.setBackgroundResource(R.drawable.background_textlayout)
            binding.txt10days.setTextColor(resources.getColor(R.color.dark_grey))
        }

        if(binding.txt30days.isSelected){
            binding.txt30days.setBackgroundResource(R.drawable.background_textlayoutselected)
            binding.txt30days.setTextColor(resources.getColor(R.color.dark_electric_green))
        }else{
            binding.txt30days.setBackgroundResource(R.drawable.background_textlayout)
            binding.txt30days.setTextColor(resources.getColor(R.color.dark_grey))
        }

        if(binding.txt90days.isSelected){
            binding.txt90days.setBackgroundResource(R.drawable.background_textlayoutselected)
            binding.txt90days.setTextColor(resources.getColor(R.color.dark_electric_green))
        }else{
            binding.txt90days.setBackgroundResource(R.drawable.background_textlayout)
            binding.txt90days.setTextColor(resources.getColor(R.color.dark_grey))
        }
    }
    private fun getDay(cn: String): Int {
        val time_spilt = cn.split("/").toTypedArray()
        return time_spilt[2].toInt()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentAccountActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt10days -> {
                binding.txt10days.isSelected=true
                binding.txt30days.isSelected=false
                binding.txt90days.isSelected=false
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.TEN_DAYS)
            }
            R.id.txt30days -> {
                binding.txt10days.isSelected=false
                binding.txt30days.isSelected=true
                binding.txt90days.isSelected=false
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.THIRTY_DAYS)
            }
            R.id.txt90days -> {
                binding.txt10days.isSelected=false
                binding.txt30days.isSelected=false
                binding.txt90days.isSelected=true
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.NINETY_DAYS)
            }
        }
    }
}