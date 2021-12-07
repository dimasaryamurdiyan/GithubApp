package com.trial.github_android.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.trial.github_android.R
import com.trial.github_android.databinding.FragmentSettingsBinding
import com.trial.github_android.databinding.FragmentUsersBinding
import com.trial.github_android.utils.PreferenceImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _fragmentSettingsBinding: FragmentSettingsBinding? = null
    private val binding get() = _fragmentSettingsBinding
    @Inject
    lateinit var preferenceImpl: PreferenceImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentSettingsBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.switchBtn?.setOnCheckedChangeListener { button, b ->
            preferenceImpl.setTheme(b)
            switchBtn.isChecked = b
            checkTheme()
        }

        checkTheme()
    }


    private fun checkTheme(){
        when(preferenceImpl.getTheme()){
            true ->  {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.switchBtn?.isChecked = true
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.switchBtn?.isChecked = false
            }
        }
    }
}