package com.example.hoangcv2_task

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoangcv2_task.database.AccountActivityDatabase
import com.example.hoangcv2_task.database.AccountActivityRepository
import com.example.hoangcv2_task.databinding.FragmentAccountActivityBinding
import com.example.hoangcv2_task.model.AccountActivity
import com.example.hoangcv2_task.viewmodel.AccountActivityViewModel
import com.example.hoangcv2_task.viewmodel.AccountActivityViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AccountActivityFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: AccountActivityViewModel
    private lateinit var binding: FragmentAccountActivityBinding
    private lateinit var accountActivityAdapter: AccountActivityAdapter
    private var list: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    private var listCheckDate: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    private var list10DaySelected: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    private var list30DaySelected: MutableList<AccountActivity> = ArrayList<AccountActivity>()
    private var list90DaySelected: MutableList<AccountActivity> = ArrayList<AccountActivity>()

    @SuppressLint("SimpleDateFormat")
    val timeStamp = SimpleDateFormat("yyyy/MM/dd")
    @SuppressLint("SimpleDateFormat")
    private val timeStampDateUi = SimpleDateFormat("dd MMM yyyy")
    private val date: Date = Calendar.getInstance().time
    private val dateTest: String = timeStamp.format(date)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        addData()
        fetchData()
        displayData()
        binding.txt10days.setOnClickListener(this)
        binding.txt30days.setOnClickListener(this)
        binding.txt90days.setOnClickListener(this)
    }

    private fun addData() {
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567A",
                "PENDING",
                timeStamp.parse("2021/12/28")!!,
                "ID#151320430"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567B",
                "PENDING",
                timeStamp.parse("2021/12/27")!!,
                "ID#151320431"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567C",
                "PENDING",
                timeStamp.parse("2021/12/23")!!,
                "ID#151320432"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567D",
                "PENDING",
                timeStamp.parse("2021/12/24")!!,
                "ID#151320433"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567E",
                "PENDING",
                timeStamp.parse("2021/12/28")!!,
                "ID#151320434"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567F",
                "PENDING",
                timeStamp.parse("2021/12/30")!!,
                "ID#151320436"
            )
        )
    }

    private fun setupUi() {
        binding.txtAccountDate.text = timeStampDateUi.format(date)
        binding.recyclerViewAccountActivity.layoutManager = LinearLayoutManager(requireContext())
        accountActivityAdapter = AccountActivityAdapter()
    }

    private fun fetchData() {
        viewModel.selectAllAccountActivity()
        viewModel.accountActivityList.observe(requireActivity(), {
            list = it
        })
    }

    private fun displayData() {
        viewModel.selectAllAccountActivityByDate(timeStamp.parse(dateTest)!!)
        viewModel.accountActivityListByDate.observe(requireActivity(), {
            listCheckDate = it
            accountActivityAdapter.getAll(listCheckDate)
            binding.recyclerViewAccountActivity.adapter = accountActivityAdapter
        })
    }

    private fun selectDataDaysSelected(daySelected: Enum.DaySelected) {
        when (daySelected) {
            Enum.DaySelected.TEN_DAYS -> {
                for (i in 0 until list.size) {
                    val dateConvert: String = timeStamp.format(list[i].accountDate)
                    if (getDay(dateConvert) < 10 && binding.txt10days.isSelected) {
                        list10DaySelected.add(
                            AccountActivity(
                                list[i].accountName,
                                list[i].accountStatus,
                                list[i].accountDate,
                                list[i].accountID
                            )
                        )
                    }
                    list90DaySelected.removeAll(list90DaySelected)
                    list30DaySelected.removeAll(list30DaySelected)
                    accountActivityAdapter.getAll(list10DaySelected)
                    binding.recyclerViewAccountActivity.adapter = accountActivityAdapter
                }
            }
            Enum.DaySelected.THIRTY_DAYS -> {
                for (i in 0 until list.size) {
                    val dateConvert: String = timeStamp.format(list[i].accountDate)
                    if (getDay(dateConvert) in 11..29 && binding.txt30days.isSelected) {
                        list30DaySelected.add(
                            AccountActivity(
                                list[i].accountName,
                                list[i].accountStatus,
                                list[i].accountDate,
                                list[i].accountID
                            )
                        )
                    }
                    list90DaySelected.removeAll(list90DaySelected)
                    list10DaySelected.removeAll(list10DaySelected)
                    accountActivityAdapter.getAll(list30DaySelected)
                    binding.recyclerViewAccountActivity.adapter = accountActivityAdapter
                }
            }
            Enum.DaySelected.NINETY_DAYS -> {
                for (i in 0 until list.size) {
                    val dateConvert: String = timeStamp.format(list[i].accountDate)
                    if (getDay(dateConvert) in 29..89 && binding.txt90days.isSelected) {
                        list90DaySelected.add(
                            AccountActivity(
                                list[i].accountName,
                                list[i].accountStatus,
                                list[i].accountDate,
                                list[i].accountID
                            )
                        )
                    }
                    list30DaySelected.removeAll(list30DaySelected)
                    list10DaySelected.removeAll(list10DaySelected)
                    accountActivityAdapter.getAll(list90DaySelected)
                    binding.recyclerViewAccountActivity.adapter = accountActivityAdapter
                }
            }
        }
    }

    private fun selectDay() {
        if (binding.txt10days.isSelected) {
            binding.txt10days.setBackgroundResource(R.drawable.background_textlayoutselected)
            binding.txt10days.setTextColor(resources.getColor(R.color.dark_electric_green))
        } else {
            binding.txt10days.setBackgroundResource(R.drawable.background_textlayout)
            binding.txt10days.setTextColor(resources.getColor(R.color.dark_grey))
        }

        if (binding.txt30days.isSelected) {
            binding.txt30days.setBackgroundResource(R.drawable.background_textlayoutselected)
            binding.txt30days.setTextColor(resources.getColor(R.color.dark_electric_green))
        } else {
            binding.txt30days.setBackgroundResource(R.drawable.background_textlayout)
            binding.txt30days.setTextColor(resources.getColor(R.color.dark_grey))
        }

        if (binding.txt90days.isSelected) {
            binding.txt90days.setBackgroundResource(R.drawable.background_textlayoutselected)
            binding.txt90days.setTextColor(resources.getColor(R.color.dark_electric_green))
        } else {
            binding.txt90days.setBackgroundResource(R.drawable.background_textlayout)
            binding.txt90days.setTextColor(resources.getColor(R.color.dark_grey))
        }
    }

    private fun getDay(cn: String): Int {
        val timeSpilt = cn.split("/").toTypedArray()
        return timeSpilt[2].toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val accountActivityRepository =
            AccountActivityRepository(AccountActivityDatabase(requireContext()))
        val factory = AccountActivityViewModelFactory(accountActivityRepository)
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(AccountActivityViewModel::class.java)
        binding = FragmentAccountActivityBinding.inflate(inflater, container, false)
//        RxJavaPlugins.setErrorHandler { e ->
//            if (e is UndeliverableException) {
//            } else {
//                Thread.currentThread().also { thread ->
//                    thread.uncaughtExceptionHandler.uncaughtException(thread, e)
//                }
//            }
//        }
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt10days -> {
                binding.txt10days.isSelected = true
                binding.txt30days.isSelected = false
                binding.txt90days.isSelected = false
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.TEN_DAYS)

            }
            R.id.txt30days -> {
                binding.txt10days.isSelected = false
                binding.txt30days.isSelected = true
                binding.txt90days.isSelected = false
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.THIRTY_DAYS)
            }
            R.id.txt90days -> {
                binding.txt10days.isSelected = false
                binding.txt30days.isSelected = false
                binding.txt90days.isSelected = true
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.NINETY_DAYS)
            }
        }
    }
}