package io.tanlnm.my.tinder

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import io.tanlnm.my.tinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding
        get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.bottomNav.setOnItemSelectedListener {
            val selectedFragment = when (it.itemId) {
                R.id.home -> HomeFragment.newInstance()

                R.id.category -> SimpleFragment.newInstance("Categories")

                R.id.fav -> SimpleFragment.newInstance("Favourites")

                R.id.chat -> SimpleFragment.newInstance("Chat")

                R.id.profile -> SimpleFragment.newInstance("Profile")

                else -> null
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, selectedFragment)
                    .commit()
            }
            true
        }

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment.newInstance())
                .commit()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}