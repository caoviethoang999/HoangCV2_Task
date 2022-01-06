package com.example.hoangcv2_task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hoangcv2_task.databinding.FragmentCreditBalanceRequestBinding

class CreditBalanceRequestFragment : Fragment() {

    private lateinit var binding: FragmentCreditBalanceRequestBinding
    private var accountName: String? = null
    private var statusName: String? = null
    private var accountID: String? = null
    private var accountDate: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun passData() {
        val bundle = this.arguments
        if (bundle != null) {
            accountName = bundle.getString("accountName")
            statusName = bundle.getString("statusName")
            accountID = bundle.getString("accountID")
            accountDate = bundle.getString("accountDate")
        }
    }
    fun setUpUi(){
        passData()
        binding.txtAccountName.text=accountName
        binding.status.text=statusName
        binding.txtAccountID.text=accountID
        if(statusName.equals("pending",true)){
            binding.layoutStatus.setBackgroundResource(R.drawable.background_status_pending)
            binding.layoutInformation.visibility=View.GONE
            binding.layoutCheckOutDate.visibility=View.GONE
        }else if(statusName.equals("success",true)){
            binding.txtDate.text=accountDate
            binding.layoutStatus.setBackgroundResource(R.drawable.background_status_success)
            binding.layoutInformation.visibility=View.GONE
        }else if(statusName.equals("failed",true)) {
            binding.layoutStatus.setBackgroundResource(R.drawable.background_status_failed)
            binding.layoutInformation.setBackgroundResource(R.drawable.background_layouinformation_failed)
            binding.layoutCheckOutDate.visibility=View.GONE
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentCreditBalanceRequestBinding.inflate(inflater, container, false)
        return binding.root
    }
}