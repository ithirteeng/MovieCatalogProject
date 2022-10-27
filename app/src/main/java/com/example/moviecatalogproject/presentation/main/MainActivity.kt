package com.example.moviecatalogproject.presentation.main

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.moviecatalogproject.R
import com.example.moviecatalogproject.databinding.ActivityMainBinding
import com.example.moviecatalogproject.presentation.main.movie.MovieFragment
import com.example.moviecatalogproject.presentation.main.profile.ProfileFragment
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(this.layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceToMovieFragment()
        onTabsSelected()
    }

    private fun onTabsSelected() {
        colorizeTab(
            binding.tabLayout.getTabAt(0)!!,
            resources.getColor(R.color.accent, theme)
        )
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab) {
                when (tab.position) {
                    0 -> replaceToMovieFragment()
                    1 -> replaceToProfileFragment()
                }
                colorizeTab(tab, resources.getColor(R.color.accent, theme))
            }

            override fun onTabUnselected(tab: Tab) {
                colorizeTab(tab, resources.getColor(R.color.gray, theme))
            }

            override fun onTabReselected(tab: Tab?) {
            }

        })
    }

    private fun colorizeTab(tab: Tab, color: Int) {
        tab.icon?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    private fun replaceToMovieFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, MovieFragment())

        }
    }

    private fun replaceToProfileFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, ProfileFragment())

        }
    }
}