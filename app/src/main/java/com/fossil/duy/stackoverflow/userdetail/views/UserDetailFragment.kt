package com.fossil.duy.stackoverflow.userdetail.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.fossil.duy.stackoverflow.R
import com.fossil.duy.stackoverflow.base.BaseFragment
import com.fossil.duy.stackoverflow.common.changeTextByCondition
import com.fossil.duy.stackoverflow.common.getFormattedDate
import com.fossil.duy.stackoverflow.common.hide
import com.fossil.duy.stackoverflow.common.loadCircleImage
import com.fossil.duy.stackoverflow.databinding.UserDetailFragmentBinding
import com.fossil.duy.stackoverflow.di.injectViewModel
import com.fossil.duy.stackoverflow.userdetail.viewmodels.UserDetailViewModel
import com.fossil.duy.stackoverflow.util.ConnectivityUtil
import javax.inject.Inject

class UserDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            UserDetailFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserDetailViewModel
    private val adapter: UserDetailsAdapter by lazy { UserDetailsAdapter() }
    private val userId by lazy { navArgs<UserDetailFragmentArgs>().value.userId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(context!!)
        viewModel.userId = userId
        val binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        binding.userDetailViewModel = viewModel

        context ?: return binding.root
        binding.recyclerView.adapter = adapter

        viewModel.loadUser()
        handleClickListener(binding)
        updateUIByObserve(binding, adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun handleClickListener(binding: UserDetailFragmentBinding) {
        binding.bookMarkBtn.setOnClickListener { viewModel.bookmark() }
    }

    private fun updateUIByObserve(
        binding: UserDetailFragmentBinding,
        adapter: UserDetailsAdapter
    ) {
        viewModel.getUser().observe(viewLifecycleOwner) {
            binding.userName.text = it.name
            binding.reputation.text = it.reputation.toString()
            binding.location.text = it.location
            binding.lastAccess.text = it.lastAccessDate.getFormattedDate()
            binding.bookMarkBtn.changeTextByCondition(
                it.isBookmarked,
                resources.getString(R.string.deBookmark), resources.getString(R.string.bookmark)
            )
            binding.userProfileImage.loadCircleImage(it.profileImageUrl)
        }
        viewModel.userDetails.observe(viewLifecycleOwner) {
            binding.progressBar.hide()
            adapter.submitList(it)
        }
    }


}
