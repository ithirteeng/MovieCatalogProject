package com.example.moviecatalogproject.presentation.main

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
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

    private val movieFragment = MovieFragment {
        binding.progressBar.visibility = View.GONE
    }


    private val profileFragment = ProfileFragment {
        binding.progressBar.visibility = View.GONE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onTabsSelected()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragmentContainerView, movieFragment)
        }

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
        binding.progressBar.visibility = View.VISIBLE
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, movieFragment)
        }
    }

    private fun replaceToProfileFragment() {
        binding.progressBar.visibility = View.VISIBLE
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, profileFragment)

        }
    }
}