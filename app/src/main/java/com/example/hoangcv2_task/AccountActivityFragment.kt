package com.example.hoangcv2_task

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoangcv2_task.database.AccountActivityDatabase
import com.example.hoangcv2_task.database.AccountActivityRepository
import com.example.hoangcv2_task.databinding.FragmentAccountActivityBinding
import com.example.hoangcv2_task.model.AccountActivity
import com.example.hoangcv2_task.model.AccountStatus
import com.example.hoangcv2_task.model.AccountTest
import com.example.hoangcv2_task.viewmodel.AccountActivityViewModel
import com.example.hoangcv2_task.viewmodel.AccountActivityViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AccountActivityFragment : Fragment(), View.OnClickListener, OnItemClickListener {

    private lateinit var viewModel: AccountActivityViewModel
    private lateinit var binding: FragmentAccountActivityBinding
    private lateinit var accountActivityAdapter: AccountActivityAdapter
    private var networkConfig=NetworkConfig()
    private var listAccountActivity: MutableList<AccountTest> = ArrayList<AccountTest>()
    private var listCheckDate: MutableList<AccountStatus> = ArrayList<AccountStatus>()
    private var list10DaySelected: MutableList<AccountTest> = ArrayList<AccountTest>()
    private var list30DaySelected: MutableList<AccountTest> = ArrayList<AccountTest>()
    private var list90DaySelected: MutableList<AccountTest> = ArrayList<AccountTest>()

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
        displayData()
        binding.txt10days.setOnClickListener(this)
        binding.txt30days.setOnClickListener(this)
        binding.txt90days.setOnClickListener(this)
    }

    private fun addData() {
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567A",
                3,
                timeStamp.parse("2021/12/28")!!,
                "ID#151320430"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567B",
                1,
                timeStamp.parse("2021/12/27")!!,
                "ID#151320431"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567C",
                1,
                timeStamp.parse("2021/12/23")!!,
                "ID#151320432"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567D",
                2,
                timeStamp.parse("2021/12/24")!!,
                "ID#151320433"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567E",
                3,
                timeStamp.parse("2021/12/28")!!,
                "ID#151320434"
            )
        )
        viewModel.insertAccountActivity(
            AccountActivity(
                "Acc 1.1234567F",
                2,
                timeStamp.parse("2021/12/30")!!,
                "ID#151320436"
            )
        )
        if (checkDataAccountStatus().isEmpty()){
            addDataFromRemote()
        }
    }

    private fun addDataFromRemote(){
        viewModel.selectAllAccountStatusRemote()
        viewModel.accountStatusRemoteList.observe(viewLifecycleOwner,{
            for (i in 0 until it.size) {
                viewModel.insertAccountStatus(
                    AccountStatus(
                        it[i].statusID,
                        it[i].statusName
                    )
                )
            }
        })
    }
    private fun checkDataAccountStatus():MutableList<AccountStatus>{
        var list:MutableList<AccountStatus> = ArrayList<AccountStatus>()
        viewModel.selectAllAccountStatus()
        viewModel.accountStatusList.observe(viewLifecycleOwner,{
            list=it
        })
        return list
    }
    private fun setupUi() {
        binding.txtAccountDate.text = timeStampDateUi.format(date)
        binding.recyclerViewAccountActivity.layoutManager = LinearLayoutManager(requireContext())
        accountActivityAdapter = AccountActivityAdapter(this)
    }


    private fun displayData() {
        viewModel.getAllDataTest()
        viewModel.accountTestList.observe(viewLifecycleOwner, {
            listAccountActivity = it
            accountActivityAdapter.getAll(listAccountActivity)
            binding.recyclerViewAccountActivity.adapter = accountActivityAdapter
        })
    }


    private fun selectDataDaysSelected(daySelected: Enum.DaySelected) {
        when (daySelected) {
            Enum.DaySelected.TEN_DAYS -> {
                for (i in 0 until listAccountActivity.size) {
                    val dateConvert: String = timeStamp.format(listAccountActivity[i].accountActivity.accountDate)
                    if (getDay(dateConvert) < 10 && binding.txt10days.isSelected) {
                        list10DaySelected.add(
                            AccountTest(AccountActivity(
                                listAccountActivity[i].accountActivity.accountName,
                                listAccountActivity[i].accountActivity.accountWithStatusID,
                                listAccountActivity[i].accountActivity.accountDate,
                                listAccountActivity[i].accountActivity.accountID
                            ),
                                AccountStatus(
                                    listAccountActivity[i].accountStatus.statusID,
                                    listAccountActivity[i].accountStatus.statusName
                                )
                        ))
                    }
                    list90DaySelected.removeAll(list90DaySelected)
                    list30DaySelected.removeAll(list30DaySelected)
                    accountActivityAdapter.getAll(list10DaySelected)
                    binding.recyclerViewAccountActivity.adapter = accountActivityAdapter
                }
            }
            Enum.DaySelected.THIRTY_DAYS -> {
                for (i in 0 until listAccountActivity.size) {
                    val dateConvert: String = timeStamp.format(listAccountActivity[i].accountActivity.accountDate)
                    if (getDay(dateConvert) in 11..29 && binding.txt30days.isSelected) {
                        list30DaySelected.add(
                            AccountTest(AccountActivity(
                                listAccountActivity[i].accountActivity.accountName,
                                listAccountActivity[i].accountActivity.accountWithStatusID,
                                listAccountActivity[i].accountActivity.accountDate,
                                listAccountActivity[i].accountActivity.accountID
                            ),
                                AccountStatus(
                                    listAccountActivity[i].accountStatus.statusID,
                                    listAccountActivity[i].accountStatus.statusName
                                )
                            ))
                    }
                    list90DaySelected.removeAll(list90DaySelected)
                    list10DaySelected.removeAll(list10DaySelected)
                    accountActivityAdapter.getAll(list30DaySelected)
                    binding.recyclerViewAccountActivity.adapter = accountActivityAdapter
                }
            }
            Enum.DaySelected.NINETY_DAYS -> {
                for (i in 0 until listAccountActivity.size) {
                    val dateConvert: String = timeStamp.format(listAccountActivity[i].accountActivity.accountDate)
                    if (getDay(dateConvert) in 29..89 && binding.txt90days.isSelected) {
                        list90DaySelected.add(
                            AccountTest(AccountActivity(
                                listAccountActivity[i].accountActivity.accountName,
                                listAccountActivity[i].accountActivity.accountWithStatusID,
                                listAccountActivity[i].accountActivity.accountDate,
                                listAccountActivity[i].accountActivity.accountID
                            ),
                                AccountStatus(
                                    listAccountActivity[i].accountStatus.statusID,
                                    listAccountActivity[i].accountStatus.statusName
                                )
                            ))
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
            AccountActivityRepository(AccountActivityDatabase(requireContext()), networkConfig.getInstance())
        val factory = AccountActivityViewModelFactory(accountActivityRepository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(AccountActivityViewModel::class.java)
        binding = FragmentAccountActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt10days -> {
                binding.txt10days.isSelected = true
                binding.txt30days.isSelected = false
                binding.txt90days.isSelected = false
                binding.txt10days.isClickable=false
                binding.txt30days.isClickable=true
                binding.txt90days.isClickable=true
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.TEN_DAYS)

            }
            R.id.txt30days -> {
                binding.txt10days.isSelected = false
                binding.txt30days.isSelected = true
                binding.txt90days.isSelected = false
                binding.txt30days.isClickable=false
                binding.txt10days.isClickable=true
                binding.txt90days.isClickable=true
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.THIRTY_DAYS)
            }
            R.id.txt90days -> {
                binding.txt10days.isSelected = false
                binding.txt30days.isSelected = false
                binding.txt90days.isSelected = true
                binding.txt90days.isClickable=false
                binding.txt10days.isClickable=true
                binding.txt30days.isClickable=true
                selectDay()
                selectDataDaysSelected(Enum.DaySelected.NINETY_DAYS)
            }
        }
    }

    override fun onItemClick(position: Int) {
        val fragment = CreditBalanceRequestFragment()
        val bundle = Bundle()
        if(list30DaySelected.size>0 && binding.txt30days.isSelected){
            bundle.putString("accountName", list30DaySelected[position].accountActivity.accountName)
            bundle.putString("accountID", list30DaySelected[position].accountActivity.accountID)
            bundle.putString("statusName", list30DaySelected[position].accountStatus.statusName)
            bundle.putString("accountDate", timeStampDateUi.format(list30DaySelected[position].accountActivity.accountDate))
        }else if (list10DaySelected.size>0 && binding.txt10days.isSelected){
            bundle.putString("accountName", list10DaySelected[position].accountActivity.accountName)
            bundle.putString("accountID", list10DaySelected[position].accountActivity.accountID)
            bundle.putString("statusName", list10DaySelected[position].accountStatus.statusName)
            bundle.putString("accountDate", timeStampDateUi.format(list10DaySelected[position].accountActivity.accountDate))
        }else if(list90DaySelected.size>0 && binding.txt90days.isSelected){
            bundle.putString("accountName", list90DaySelected[position].accountActivity.accountName)
            bundle.putString("accountID", list90DaySelected[position].accountActivity.accountID)
            bundle.putString("statusName", list90DaySelected[position].accountStatus.statusName)
            bundle.putString("accountDate", timeStampDateUi.format(list90DaySelected[position].accountActivity.accountDate))
        }else if(listAccountActivity.size>0){
            bundle.putString("accountName", listAccountActivity[position].accountActivity.accountName)
            bundle.putString("accountID", listAccountActivity[position].accountActivity.accountID)
            bundle.putString("statusName", listAccountActivity[position].accountStatus.statusName)
            bundle.putString("accountDate", timeStampDateUi.format(listAccountActivity[position].accountActivity.accountDate))
        }
        fragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.addToBackStack(null)?.replace(R.id.fragment_container, fragment)?.commit()
    }
}