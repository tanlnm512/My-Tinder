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

        println(solveProblem(10, 5, 6, 4, 8))  // Output: 10
        println(solveProblem(10, 5, 6, 4, 9))  // Output: 16
        println(solveProblem(5, 3, 7, 4, 6))   // Output: 7
        println(solveProblem(8, 7, 7, 9, 6))   // Output: 0
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    //<editor-fold desc="Logic Test">
    /**
     * You found two items in a treasure chest! The first item weighs weight1 and is worth value1, and the second item weighs weight2 and is worth value2. What is the total maximum value of the items you can take with you, assuming that your max weight capacity is maxW and you can't come back for the items later?
     *
     * Note that there are only two items and you can't bring more than one item of each type, i.e. you can't take two first items or two second items.
     *
     * Example
     *
     * For value1 = 10, weight1 = 5, value2 = 6, weight2 = 4, and maxW = 8, the output should be
     * solution(value1, weight1, value2, weight2, maxW) = 10.
     *
     * You can only carry the first item.
     *
     * For value1 = 10, weight1 = 5, value2 = 6, weight2 = 4, and maxW = 9, the output should be
     * solution(value1, weight1, value2, weight2, maxW) = 16.
     *
     * You're strong enough to take both of the items with you.
     *
     * For value1 = 5, weight1 = 3, value2 = 7, weight2 = 4, and maxW = 6, the output should be
     * solution(value1, weight1, value2, weight2, maxW) = 7.
     *
     * You can't take both items, but you can take any of them.
     */

    private fun solveProblem(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
        return when {
            weight1 + weight2 <= maxW -> {
                value1 + value2
            }

            maxW in weight1..<weight2 -> {
                value1
            }

            maxW in weight2..<weight1 -> {
                value2
            }

            weight1 <= maxW -> {
                maxOf(value1, value2)
            }

            else -> 0
        }
    }
    //</editor-fold>
}