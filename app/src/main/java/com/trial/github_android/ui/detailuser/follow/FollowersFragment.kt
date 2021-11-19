package com.trial.github_android.ui.detailuser.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.trial.github_android.R
import com.trial.github_android.data.entities.FollowersEntity
import com.trial.github_android.data.remote.response.UserResponse.User
import com.trial.github_android.databinding.FragmentFollowersBinding
import com.trial.github_android.databinding.FragmentFollowingBinding
import com.trial.github_android.ui.detailuser.DetailUserViewModel
import com.trial.github_android.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FollowersFragment : Fragment() {
    private var _fragmentFollowersBinding : FragmentFollowersBinding? = null
    private val binding get() = _fragmentFollowersBinding
    private val viewModel: DetailUserViewModel by viewModels(ownerProducer = {requireParentFragment()})
    private lateinit var myAdapter: FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentFollowersBinding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        myAdapter = FollowerAdapter()
        binding?.apply {
            rvFollowers.layoutManager = LinearLayoutManager(requireContext())
            rvFollowers.adapter = myAdapter
        }
    }

    private fun setupObservers() {
        viewModel.userFollowers.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    Timber.d("Users-Followers : ${it.data}")
                    if (!it.data.isNullOrEmpty()) myAdapter.setItems(it.data as ArrayList<FollowersEntity>)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding?.progressBar?.visibility = View.VISIBLE
            }
        })
    }



}