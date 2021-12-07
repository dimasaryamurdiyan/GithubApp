package com.trial.github_android.ui.favorite

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
import com.trial.github_android.data.entities.FollowingEntity
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.databinding.FragmentFavoriteBinding
import com.trial.github_android.databinding.FragmentUsersBinding
import com.trial.github_android.ui.user.UserAdapter
import com.trial.github_android.ui.user.UserViewModel
import com.trial.github_android.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var  _fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val binding get() = _fragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.favorites?.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    Timber.d(" ${it.data}")
                    if (!it.data.isNullOrEmpty()) adapter.setItems(it.data as ArrayList<UserEntity>)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding?.progressBar?.visibility = View.VISIBLE
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter()
        binding?.apply {
            rvFavorites.layoutManager = LinearLayoutManager(requireContext())
            rvFavorites.adapter = adapter
        }
    }
}