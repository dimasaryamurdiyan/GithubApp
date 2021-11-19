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
import com.trial.github_android.data.entities.FollowingEntity
import com.trial.github_android.data.remote.response.UserResponse.User
import com.trial.github_android.databinding.FragmentFollowingBinding
import com.trial.github_android.ui.detailuser.DetailUserViewModel
import com.trial.github_android.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FollowingFragment : Fragment() {
    private var _fragmentFollowingBinding : FragmentFollowingBinding? = null
    private val binding get() = _fragmentFollowingBinding
    private val viewModel: DetailUserViewModel by viewModels(ownerProducer = {requireParentFragment()})
    private lateinit var myAdapter: FollowingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentFollowingBinding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("tes debug masuk")
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        myAdapter = FollowingAdapter()
        binding?.apply {
            rvFollowing.layoutManager = LinearLayoutManager(requireContext())
            rvFollowing.adapter = myAdapter
        }
    }

    private fun setupObservers() {
        Timber.d("tes")
        viewModel.userFollowing.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) myAdapter.setItems(it.data as ArrayList<FollowingEntity>)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding?.progressBar?.visibility = View.VISIBLE
            }
        })
    }


}