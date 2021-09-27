package uz.creater.adiblar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.creater.adiblar.R
import uz.creater.adiblar.adapters.ViewpagerAdapter
import uz.creater.adiblar.databinding.FragmentWritersBinding
import uz.creater.adiblar.databinding.ItemTabBinding
import uz.creater.adiblar.utils.Settings
import java.util.*
import kotlin.collections.ArrayList


class WritersFragment : Fragment() {

    private lateinit var binding: FragmentWritersBinding
    private val categoryList: ArrayList<String> =
        arrayListOf("Mumtoz adabiyot", "O'zbek adabiyoti", "Jahon adabiyoti")
    private val categoryListKiril: ArrayList<String> =
        arrayListOf("Мумтоз адабиёт", "Ўзбек адабиёти", "Жаҳон адабиёти")
    private lateinit var viewpagerAdapter: ViewpagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritersBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setTabs() {
        var tabCount = binding.tabLayout.tabCount

        for (i in 0 until tabCount) {
            val tabBinding = ItemTabBinding.inflate(LayoutInflater.from(context))
            val tabAt = binding.tabLayout.getTabAt(i)
            tabAt?.customView = tabBinding.root
            if (Settings.isKiril) {
                tabBinding.titleTv.text = categoryListKiril[i]
            } else {
                tabBinding.titleTv.text = categoryList[i]
            }
            if (i == 0) {
                tabBinding.layoutTv.setBackgroundResource(R.drawable.selected_tab_back)
                tabBinding.titleTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            } else {
                tabBinding.layoutTv.setBackgroundResource(R.drawable.unselected_tab_back)
                tabBinding.titleTv.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.unselected_tab_text_color
                    )
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewpagerAdapter = ViewpagerAdapter(categoryList, requireActivity())
        binding.viewPager2.adapter = viewpagerAdapter
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.findViewById<ConstraintLayout>(R.id.layout_tv)
                    ?.setBackgroundResource(R.drawable.selected_tab_back)
                customView?.findViewById<TextView>(R.id.title_tv)
                    ?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.findViewById<ConstraintLayout>(R.id.layout_tv)
                    ?.setBackgroundResource(R.drawable.unselected_tab_back)
                customView?.findViewById<TextView>(R.id.title_tv)
                    ?.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.unselected_tab_text_color
                        )
                    )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            if (Settings.isKiril) {
                tab.text = categoryListKiril[position]
            } else {
                tab.text = categoryList[position]
            }
        }.attach()
        setTabs()
        binding.searchCard.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }
}