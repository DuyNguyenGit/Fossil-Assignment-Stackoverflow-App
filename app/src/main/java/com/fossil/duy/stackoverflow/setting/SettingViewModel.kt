package com.fossil.duy.stackoverflow.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fossil.duy.stackoverflow.common.SharedPreference
import com.fossil.duy.stackoverflow.common.SharedPreference.Companion.DISPLAY_ONLY_BOORMARK_USER
import com.fossil.duy.stackoverflow.users.data.UsersRepository
import javax.inject.Inject

class SettingViewModel @Inject constructor(usersRepository: UsersRepository) : ViewModel() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    val _isOnlyBookmarkDisplayed = MutableLiveData<Boolean>()
    val isOnlyBookmarkDisplayed: LiveData<Boolean> get() = _isOnlyBookmarkDisplayed

    fun updateOnlyBookmarkDisplayFlag(checked: Boolean) {
        sharedPreference.save(DISPLAY_ONLY_BOORMARK_USER, checked)
        _isOnlyBookmarkDisplayed.value = checked
    }

    fun init() {
        _isOnlyBookmarkDisplayed.value = sharedPreference.getValueBoolien(DISPLAY_ONLY_BOORMARK_USER, false)
    }

}
