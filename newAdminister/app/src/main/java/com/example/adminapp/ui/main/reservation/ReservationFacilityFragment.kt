package com.example.adminapp.ui.main.reservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.adminapp.base.BaseSessionFragment
import com.example.adminapp.data.model.*
import com.example.adminapp.databinding.FragmentReservationChildTypesBinding
import com.example.adminapp.ui.main.MainFragmentDirections

class ReservationFacilityFragment : BaseSessionFragment<FragmentReservationChildTypesBinding, ReservationViewModel>() {

    override lateinit var viewbinding: FragmentReservationChildTypesBinding
    override val viewmodel: ReservationViewModel by viewModels()
    private lateinit var reservationFacilityRVAdapter: ReservationFacilityRVAdapter
    private var facilityLogList : List<ReservationFacilityLog> = listOf()
    private var facilityDataBundleList : List<ReservationFacilityBundle> = listOf()
    private var facilityDataBundle: ReservationFacilityBundle? = null

    override fun initViewbinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewbinding = FragmentReservationChildTypesBinding.inflate(inflater, container, false)
        return viewbinding.root
    }

    override fun initViewStart(savedInstanceState: Bundle?) {
        viewbinding.reservationChildText.text = "예약하기"
        setRecyclerView() }

    override fun initDataBinding(savedInstanceState: Bundle?) { }

    override fun initViewFinal(savedInstanceState: Bundle?) {
        viewmodel.getReservationFacilitySettingDataList().observe(viewLifecycleOwner){ it ->
            if (facilityLogList.isNotEmpty()) facilityLogList.forEach { logData -> facilityDataBundleList = it.map { settingData -> checkFacilitySettingData(logData, settingData) }  }
            else facilityDataBundleList = it.map { settingData ->  makeFacilitySettingDataToBundleData(settingData) }
            showRVView(facilityDataBundleList)
        }
        viewmodel.getReservationFacilityLogList().observe(viewLifecycleOwner){
            facilityLogList = it
            facilityLogList.forEach { logData -> facilityDataBundleList = facilityDataBundleList.map { bundleData -> checkUsingFacilityData(logData, bundleData) } }
            showRVView(facilityDataBundleList)
        }
    }

    private fun setRecyclerView() {
        reservationFacilityRVAdapter = ReservationFacilityRVAdapter(requireContext(), viewmodel, object : ReservationFacilityRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, facilityData: ReservationFacilityBundle) {
                facilityDataBundle = facilityData
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToReservationDetailFacilityFragment(facilityBundle = facilityDataBundle))
            }
        })
        viewbinding.reservationRv.adapter = reservationFacilityRVAdapter
    }

    private fun showRVView(list : List<ReservationFacilityBundle>){
        if (list.isEmpty()){ showEmptyView() }
        else showRV(list)
    }
    private fun showEmptyView(){
        viewbinding.apply {
            reservationChildEmptyView.visibility = View.VISIBLE
            reservationRv.visibility = View.GONE
        }
    }
    private fun showRV(list : List<ReservationFacilityBundle>){
        viewbinding.run{
            reservationChildEmptyView.visibility  = View.GONE
            reservationRv.visibility = View.VISIBLE
            reservationFacilityRVAdapter.submitList(list)
        }
    }

    private fun makeFacilitySettingDataToBundleData(settingData : ReservationFacilitySettingData) : ReservationFacilityBundle {
        return ReservationFacilityBundle(false, settingData.name, null, settingData)
    }

    private fun checkFacilitySettingData(logData : ReservationFacilityLog, settingData : ReservationFacilitySettingData) : ReservationFacilityBundle{
        return if (logData.name == settingData.name) ReservationFacilityBundle(true, logData.name, logData, null)
        else ReservationFacilityBundle(false, settingData.name, null, settingData)
    }

    private fun checkUsingFacilityData(logData : ReservationFacilityLog, bundleData : ReservationFacilityBundle) : ReservationFacilityBundle{
        return if (logData.name == bundleData.name) ReservationFacilityBundle(true, logData.name, logData, null)
        else bundleData
    }
}
