package com.fossil.duy.stackoverflow.userdetail.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.fossil.duy.stackoverflow.base.BaseFragment
import com.fossil.duy.stackoverflow.common.hide
import com.fossil.duy.stackoverflow.common.setTitle
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
    private val args: UserDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(context!!)
        viewModel.userId = args.userId

        val binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        context ?: return binding.root
        binding.recyclerView.adapter = adapter

        args.userName?.let { setTitle(it) }
        observeCurrentUser(binding)
        updateUIByObserve(binding, adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun observeCurrentUser(binding: UserDetailFragmentBinding?) {

    }

    private fun updateUIByObserve(
        binding: UserDetailFragmentBinding,
        adapter: UserDetailsAdapter
    ) {
        viewModel.userDetails.observe(viewLifecycleOwner) {
            binding.progressBar.hide()
            adapter.submitList(it)
        }
    }


}
