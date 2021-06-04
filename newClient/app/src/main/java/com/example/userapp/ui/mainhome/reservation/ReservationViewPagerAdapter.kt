package com.example.userapp.ui.mainhome.reservation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ReservationViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return ReservationCurrentFragment()
            }
            1 -> {
                return ReservationEquipmentFragment()
            }
            2 -> {
                return ReservationFacilityFragment()
            }
            else -> {
                return ReservationCurrentFragment()
            }
        }
    }
}