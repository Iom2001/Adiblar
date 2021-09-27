package uz.creater.adiblar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import uz.creater.adiblar.adapters.MainViewpagerAdapter
import uz.creater.adiblar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewpagerAdapter: MainViewpagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        mainViewpagerAdapter = MainViewpagerAdapter(childFragmentManager)
        binding.mainViewPager.adapter = mainViewpagerAdapter
        binding.bottomBar.onItemSelected = { position ->
            when (position) {
                0 -> {
                    binding.mainViewPager.currentItem = 0
                }
                1 -> {
                    binding.mainViewPager.currentItem = 1
                }
                else -> {
                    binding.mainViewPager.currentItem = 2
                }
            }
        }

        binding.mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.bottomBar.itemActiveIndex = 0
                    }
                    1 -> {
                        binding.bottomBar.itemActiveIndex = 1
                    }
                    else -> {
                        binding.bottomBar.itemActiveIndex = 2
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        return binding.root
    }
}