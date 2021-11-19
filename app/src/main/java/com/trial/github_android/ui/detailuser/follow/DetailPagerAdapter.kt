package com.trial.github_android.ui.detailuser.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.trial.github_android.ui.detailuser.DetailUserFragment

class DetailPagerAdapter(fm: FragmentManager?, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(
    fm!!, lifecycle){
    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // # Movie Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Following")
                val myFollowingFragment = FollowingFragment()
                myFollowingFragment.arguments = bundle
                return myFollowingFragment
            }
            1 -> {
                // # TvShow Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Follower")
                val myFollowersFragment = FollowersFragment()
                myFollowersFragment.arguments = bundle
                return myFollowersFragment
            }
            else -> return DetailUserFragment()
        }
    }


}