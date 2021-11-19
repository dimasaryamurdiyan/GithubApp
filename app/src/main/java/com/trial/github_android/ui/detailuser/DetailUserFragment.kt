package com.trial.github_android.ui.detailuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.trial.github_android.R
import com.trial.github_android.data.entities.FollowersEntity
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.databinding.FragmentDetailUserBinding
import com.trial.github_android.databinding.FragmentUsersBinding
import com.trial.github_android.ui.detailuser.follow.DetailPagerAdapter
import com.trial.github_android.ui.detailuser.follow.SectionPagerAdapter
import com.trial.github_android.ui.user.UserViewModel
import com.trial.github_android.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailUserFragment : Fragment() {
    private var _fragmentDetailUserBinding : FragmentDetailUserBinding ? = null
    private val binding get() = _fragmentDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentDetailUserBinding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        arguments?.getString("username")?.let { viewModel.selectUsername(it) }
        setupObservers()
        binding?.btnFab?.setOnClickListener {
            viewModel.setFavoriteUser()
        }
    }

    private fun setupPager() {
//        val sectionsPagerAdapter = context?.let { SectionPagerAdapter(it, childFragmentManager) }
//        binding?.viewPager?.adapter = sectionsPagerAdapter
//        binding?.tabs?.setupWithViewPager(binding?.viewPager)
        val viewPager = binding?.viewPager as ViewPager2
        val tabs = binding?.tabs as TabLayout
        val numberOfTabs = 2
        val adapter = DetailPagerAdapter(childFragmentManager, lifecycle, numberOfTabs)
        viewPager.adapter = adapter

        // Enable Swipe
        viewPager.isUserInputEnabled = true

        TabLayoutMediator(tabs, viewPager){tab, position ->
            when(position){
                0 -> {
                    tab.text = "Following"
                }
                1 -> {
                    tab.text = "Followers"
                }
            }
        }.attach()
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindCharacter(it.data!!)
                    Timber.d("detailUser: ${it.data}")
                    binding?.apply {
                        progressBar.visibility = View.GONE
                        clUser.visibility = View.VISIBLE
                    }
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding?.apply {
                        progressBar.visibility = View.VISIBLE
                        clUser.visibility = View.GONE
                    }
                }
            }
        })

        viewModel.userFollowers.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    Timber.d("Users-Following : asd")
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding?.progressBar?.visibility = View.VISIBLE
            }
        })
    }

    private fun bindCharacter(user: UserEntity) {
        binding?.apply {
            tvName.text = user.name
            tvFollowers.text = user.followers.toString()
            tvFollowing.text = user.following.toString()
            tvPublicRepo.text = user.publicRepos.toString()
            tvUserName.text = user.login
        }

        val state = user.isFavorite
        state?.let { setFavoriteState(it) }
        binding?.image?.let {
            Glide.with(this)
                .load(user.avatarUrl)
                .transform(CircleCrop())
                .into(it)
        }
    }

    private fun setFavoriteState(state: Boolean){
        if(state){
            binding?.btnFab?.setImageResource(R.drawable.ic_favorite)
        }else{
            binding?.btnFab?.setImageResource(R.drawable.ic_favorite_border)
        }
    }


}