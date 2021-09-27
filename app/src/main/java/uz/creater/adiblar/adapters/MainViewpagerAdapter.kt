package uz.creater.adiblar.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.creater.adiblar.fragments.SavedFragment
import uz.creater.adiblar.fragments.SettingsFragment
import uz.creater.adiblar.fragments.WritersFragment

class MainViewpagerAdapter(
    fragmentManager: FragmentManager
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                WritersFragment()
            }
            1 -> {
                SavedFragment()
            }
            else -> {
                SettingsFragment()
            }
        }
    }
}