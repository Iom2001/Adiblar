package uz.creater.adiblar.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.suke.widget.SwitchButton
import uz.creater.adiblar.MainActivity
import uz.creater.adiblar.R
import uz.creater.adiblar.databinding.FragmentSettingsBinding
import uz.creater.adiblar.databinding.SelectLanBinding
import uz.creater.adiblar.utils.Settings


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferencesTheme: SharedPreferences
    private lateinit var sharedPreferencesLan: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        sharedPreferencesTheme =
            activity?.getSharedPreferences(Settings.PREF_NAME, Context.MODE_PRIVATE)!!
        sharedPreferencesLan =
            activity?.getSharedPreferences(Settings.PREF_LAN_NAME, Context.MODE_PRIVATE)!!
        binding.switchBtn.toggle(true)
        binding.switchBtn.setShadowEffect(true)
        binding.switchBtn.isEnabled = true
        binding.switchBtn.setEnableEffect(true)
        checkNightModeActivity()
        binding.switchBtn.setOnCheckedChangeListener(SwitchButton.OnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveNightMode(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveNightMode(false)
                // activity?.recreate()
            }
        })

        binding.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val imageUri = "https://play.google.com/store/apps/details?id=uz.mobiler.adiblar"
            val shareSubject = if (Settings.isKiril) {
                "Aдиблар ҳаёти ва ижоди"
            } else {
                "Adiblar hayoti va ijodi"
            }
            intent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
            intent.putExtra(Intent.EXTRA_TEXT, imageUri)
            startActivity(Intent.createChooser(intent, imageUri))
        }

        binding.infoApp.setOnClickListener {
            val alertDialog = context?.let { AlertDialog.Builder(it) }
            val dialog = alertDialog!!.create()
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogView: View = layoutInflater.inflate(
                R.layout.app_info_dialog,
                null,
                false
            )
            dialog.setView(dialogView)
//            val bindDialog = AppInfoDialogBinding.bind(dialogView)
            dialog.show()
        }

        binding.lanLayout.setOnClickListener {
            val alertDialog = context?.let { AlertDialog.Builder(it) }
            val dialog = alertDialog!!.create()
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogView: View = layoutInflater.inflate(
                R.layout.select_lan,
                null,
                false
            )
            dialog.setView(dialogView)
            val bindDialog = SelectLanBinding.bind(dialogView)
            if (Settings.isKiril) {
                bindDialog.radioGrp.check(R.id.radio_ciril)
            } else {
                bindDialog.radioGrp.check(R.id.radio_latin)
            }
            bindDialog.okTv.setOnClickListener {
                if (bindDialog.radioLatin.isChecked && Settings.isKiril) {
                    Settings.isKiril = false
                    saveLan()
                    requireActivity().recreate()
                } else if (bindDialog.radioCiril.isChecked && !Settings.isKiril) {
                    Settings.isKiril = true
                    saveLan()
                    requireActivity().recreate()
                }
                dialog.dismiss()
            }
            bindDialog.cancelTv.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        return binding.root
    }

    private fun saveNightMode(b: Boolean) {
        var editor = sharedPreferencesTheme.edit()
        editor.putBoolean(Settings.NIGHT_MODE_KEY, b)
        editor.apply()
    }

    private fun saveLan() {
        var editor = sharedPreferencesLan.edit()
        editor.putBoolean(Settings.LAN_KEY, Settings.isKiril)
        editor.apply()
    }

    private fun checkNightModeActivity() {
        binding.switchBtn.isChecked =
            sharedPreferencesTheme.getBoolean(Settings.NIGHT_MODE_KEY, false)
    }
}