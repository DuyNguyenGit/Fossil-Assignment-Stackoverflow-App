package com.fossil.duy.stackoverflow.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fossil.duy.stackoverflow.base.BaseFragment
import com.fossil.duy.stackoverflow.databinding.SettingFragmentBinding
import com.fossil.duy.stackoverflow.di.injectViewModel
import javax.inject.Inject


class SettingFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            SettingFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        val binding = SettingFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        context ?: return binding.root

        viewModel.init()
        binding.displaySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.updateOnlyBookmarkDisplayFlag(isChecked)
        }

        setHasOptionsMenu(true)
        return binding.root
    }


}
