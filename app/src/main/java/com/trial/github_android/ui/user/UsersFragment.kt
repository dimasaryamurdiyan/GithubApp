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
import com.trial.github_android.data.entities.UserEntity
import com.trial.github_android.databinding.FragmentUsersBinding
import com.trial.github_android.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
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
        setupRecyclerView()
        setupObservers()
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
        viewModel.users.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
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
        viewModel.usersData.observe(viewLifecycleOwner, Observer{
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    Timber.d("Users : ${it.data}")
                    if (!it.data?.data.isNullOrEmpty()){
                        val list = ArrayList<UserEntity>()
                        for(response in it.data?.data!!){
                            val user = UserEntity(
                                response.id,
                                response.login,
                                response.nodeId,
                                response.avatarUrl,
                                response.gravatarId,
                                response.url,
                                response.htmlUrl,
                                response.followersUrl,
                                response.followingUrl,
                                response.gistsUrl,
                                response.starredUrl,
                                response.subscriptionsUrl,
                                response.organizationsUrl,
                                response.reposUrl,
                                response.eventsUrl,
                                response.receivedEventsUrl,
                                response.type,
                                response.siteAdmin,
                                response.name,
                                response.company,
                                response.blog,
                                response.location,
                                response.email,
                                response.hireable,
                                response.bio,
                                response.twitterUsername,
                                response.publicRepos,
                                response.publicGists,
                                response.followers,
                                response.following,
                                response.createdAt,
                                response.updatedAt,
                            )
                            list.add(user)
                        }
                        adapter.setItems(ArrayList(list))
                    }

                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding?.progressBar?.visibility = View.VISIBLE
            }
        })
    }


}