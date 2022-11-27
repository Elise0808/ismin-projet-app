package com.example.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Adapter that manages the fragments of the view pager.
 */
class MyViewPagerAdapter(fragmentActivity: FragmentActivity, private val stations : ArrayList<Station>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoFragment.newInstance()
            1 -> ListFragment.newInstance(stations)
            else -> MapFragment.newInstance(stations)
        }
    }

}