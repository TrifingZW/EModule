package com.trifingzw.emodule.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.trifingzw.emodule.fragment.BtlFragment

class BtlFragmentStateAdapter(fragmentActivity: FragmentActivity, private val fragments: Array<BtlFragment>) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}