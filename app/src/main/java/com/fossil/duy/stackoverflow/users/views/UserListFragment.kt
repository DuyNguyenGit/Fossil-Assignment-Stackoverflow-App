package com.fossil.duy.stackoverflow.users.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.fossil.duy.stackoverflow.base.BaseFragment
import com.fossil.duy.stackoverflow.common.SharedPreference
import com.fossil.duy.stackoverflow.common.SharedPreference.Companion.DISPLAY_ONLY_BOORMARK_USER
import com.fossil.duy.stackoverflow.common.hide
import com.fossil.duy.stackoverflow.common.show
import com.fossil.duy.stackoverflow.database.DataResult
import com.fossil.duy.stackoverflow.databinding.UserListFragmentBinding
import com.fossil.duy.stackoverflow.di.injectViewModel
import com.fossil.duy.stackoverflow.users.viewmodels.UserListViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

class UserListFragment : BaseFragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    @Inject
    lateinit var sharedPreference: SharedPreference
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        val binding = UserListFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        context ?: return binding.root

        val adapter = UserListAdapter()
        binding.recyclerView.adapter = adapter

        updateUIByObserve(binding, adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun updateUIByObserve(binding: UserListFragmentBinding, adapter: UserListAdapter) {
        viewModel.users.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                DataResult.Status.SUCCESS -> {
                    binding.progressBar.hide()
                    result.data?.let {
                        Timber.i(it.size.toString())
                        val isChecked: Boolean =
                            sharedPreference.getValueBoolien(DISPLAY_ONLY_BOORMARK_USER, false)
                        Timber.d("DUY: isChecked = $isChecked")
                        adapter.submitList(if (isChecked) it.filter { it.isBookmarked == true } else it)
                    }
                }
                DataResult.Status.LOADING -> binding.progressBar.show()
                DataResult.Status.ERROR -> {
                    binding.progressBar.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
