package uz.creater.adiblar

import android.os.Bundle
import androidx.navigation.findNavController
import uz.creater.adiblar.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp()
    }
}