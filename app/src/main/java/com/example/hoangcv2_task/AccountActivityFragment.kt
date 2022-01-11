package com.example.hoangcv2_task

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.*


class AccountActivityFragment : Fragment(), View.OnClickListener, OnItemClickListener{

    private lateinit var viewModel: AccountActivityViewModel
    private lateinit var binding: FragmentAccountActivityBinding
    private lateinit var accountActivityAdapter: AccountActivityAdapter
    private var networkConfig=NetworkConfig()
    private var listAccountActivity: MutableList<AccountTest> = ArrayList<AccountTest>()
    private var listCheckDate: MutableList<AccountStatus> = ArrayList<AccountStatus>()
    private var list10DaySelected: MutableList<AccountTest> = ArrayList<AccountTest>()
    private var list30DaySelected: MutableList<AccountTest> = ArrayList<AccountTest>()
    private var list90DaySelected: MutableList<AccountTest> = ArrayList<AccountTest>()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 0

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
        getCurrentLocation()
        binding.txt10days.setOnClickListener(this)
        binding.txt30days.setOnClickListener(this)
        binding.txt90days.setOnClickListener(this)
    }

    fun getCurrentLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        RequestPermission()
        getLastLocation()
    }
    @SuppressLint("MissingPermission")
    fun getLastLocation(){
        if(CheckPermission()){
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
                    var location:Location? = task.result
                    if(location == null){
                        NewLocationData()
                    }else{
                        Log.d("Debug:" ,"Your Location:"+ location.longitude)
                        binding.txtLocation.text = "You Current Location is : Long: "+ location.longitude + " , Lat: " + location.latitude + "\n" + getCityName(location.latitude,location.longitude)
                    }
                }
            }else{
                Toast.makeText(requireActivity(),"Please Turn on Your device Location", Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }


    @SuppressLint("MissingPermission")
    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback, Looper.myLooper()
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:","your last last location: "+ lastLocation.longitude.toString())
            binding.txtLocation.text = "You Last Location is : Long: "+ lastLocation.longitude + " , Lat: " + lastLocation.latitude + "\n" + getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }

    private fun CheckPermission():Boolean{
        if(
            ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false

    }

    fun RequestPermission(){
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    fun isLocationEnabled():Boolean{
        var locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:","You have the Permission")
            }
        }
    }

    private fun getCityName(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(requireContext(), Locale.getDefault())
        var address = geoCoder.getFromLocation(lat,long,1)

        // StreetAddress -> StreetAddress Number -> Postal Code -> City -> Country
        cityName = address[0].getAddressLine(0)
        //split string cityName after "," to get string of city
        var getcity:List<String> = cityName.split(", ")
        countryName = address[0].countryName
        Log.d("Debug:", "Your City: $cityName ; your Country $countryName")
        return cityName
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

        binding = FragmentAccountActivityBinding.inflate(inflater, container, false)
        val accountActivityRepository =
            AccountActivityRepository(AccountActivityDatabase(requireContext()), networkConfig.getInstance(),binding)
        val factory = AccountActivityViewModelFactory(accountActivityRepository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(AccountActivityViewModel::class.java)
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
                getCurrentLocation()
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