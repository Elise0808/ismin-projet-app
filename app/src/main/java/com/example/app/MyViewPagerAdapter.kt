package com.example.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Adapter that manages the fragments of the view pager.
 * @param dataPath : path to the json data file
 * @param clickedId : id of the station that was clicked
 */
class MyViewPagerAdapter(fragmentActivity: FragmentActivity, private var dataPath : String, private var clickedId : Int = 0) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InfoFragment.newInstance()
            1 -> ListFragment.newInstance(dataPath)
            else -> MapFragment.newInstance(dataPath, clickedId)
        }
    }

}