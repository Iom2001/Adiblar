package uz.creater.adiblar.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.creater.adiblar.fragments.CategoryFragment

class ViewpagerAdapter(private val categoryList: List<String>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = categoryList.size

    override fun createFragment(position: Int): Fragment {
        return CategoryFragment.newInstance(
            categoryList[position]
        )
    }
}