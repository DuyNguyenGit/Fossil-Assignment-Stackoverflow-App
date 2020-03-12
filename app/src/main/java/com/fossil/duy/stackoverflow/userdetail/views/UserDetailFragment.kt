package com.fossil.duy.stackoverflow.userdetail.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fossil.duy.stackoverflow.base.BaseFragment
import com.fossil.duy.stackoverflow.common.hide
import com.fossil.duy.stackoverflow.databinding.UserDetailFragmentBinding
import com.fossil.duy.stackoverflow.di.injectViewModel
import com.fossil.duy.stackoverflow.userdetail.models.UserDetailEntity
import com.fossil.duy.stackoverflow.userdetail.viewmodels.UserDetailViewModel
import javax.inject.Inject

class UserDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            UserDetailFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        val binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        context ?: return binding.root

        val adapter = UserDetailsAdapter()
        binding.recyclerView.adapter = adapter

        updateUIByObserve(binding, adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun updateUIByObserve(
        binding: UserDetailFragmentBinding,
        adapter: UserDetailsAdapter
    ) {
        binding.progressBar.hide()
        adapter.submitList(listOf(UserDetailEntity("A", "B", 1, 2, 3, 4)))

    }


}
