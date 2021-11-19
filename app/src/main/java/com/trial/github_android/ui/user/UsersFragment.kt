package com.trial.github_android.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trial.github_android.R
import com.trial.github_android.databinding.FragmentUsersBinding
import com.trial.github_android.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_app_bar.*
import timber.log.Timber

@AndroidEntryPoint
class UsersFragment : Fragment(), UserAdapter.UserItemListener {
    private var _fragmentUsersBinding: FragmentUsersBinding? = null
    private val binding get() = _fragmentUsersBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentUsersBinding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupToolbar() {
        binding?.apply {
            appBar.tvTitle.text = "Users"
            ivFavorite.setOnClickListener {
                findNavController().navigate(
                    R.id.action_usersFragment_to_favoriteFragment,
                )
            }
        }
    }

    override fun onClicked(username: String) {
        findNavController().navigate(
            R.id.action_usersFragment_to_detailUserFragment,
            bundleOf("username" to username)
        )
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(this)
        binding?.apply {
            rvUsers.layoutManager = LinearLayoutManager(requireContext())
            rvUsers.adapter = adapter
        }
    }

    private fun setupObservers() {
        viewModel.users?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val a = it.data
                    binding?.progressBar?.visibility = View.GONE
                    Timber.d("Users : ${it.data}")
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding?.progressBar?.visibility = View.VISIBLE
            }
        })
    }




}