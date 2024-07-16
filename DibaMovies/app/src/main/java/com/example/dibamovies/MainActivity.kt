package com.example.dibamovies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dibamovies.databinding.ActivityMainBinding
import com.example.dibamovies.presentation.ui.favorite.FavoriteFragment
import com.example.dibamovies.presentation.ui.favorite.FavoriteModule
import com.example.dibamovies.presentation.ui.homescreen.HomeFragment
import com.example.dibamovies.presentation.ui.search.SearchFragment
import com.example.dibamovies.shared_component.UiUtils

class MainActivity : AppCompatActivity() {
    //region Properties
    private lateinit var binding: ActivityMainBinding
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiUtils.removeHeader(this)
        FavoriteModule.initialize(applicationContext)
        initialBinding()
        setupBottomNavigationView()

        if (savedInstanceState == null) {
            loadFragment(SearchFragment())
        }
    }
    //endregion

    //region Methods
    private fun initialBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> selectedFragment = HomeFragment()
                R.id.navigation_search -> selectedFragment = SearchFragment()
                R.id.navigation_favorites -> selectedFragment = FavoriteFragment()
            }
            if (selectedFragment != null) {
                loadFragment(selectedFragment)
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    //endregion
}