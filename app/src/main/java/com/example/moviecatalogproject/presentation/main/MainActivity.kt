package com.example.moviecatalogproject.presentation.main

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
        changeProgressBarVisibility(it)
    }


    private val profileFragment = ProfileFragment(
        setTableLayoutClickability = {
            setTableLayoutClickability(it)
        },
        changeProgressBarVisibility = {
            changeProgressBarVisibility(it)
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onTabsSelected()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragmentContainerView, movieFragment)
        }

        setTableLayoutClickability(true)

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
            replace(R.id.fragmentContainerView, movieFragment)
        }
    }

    private fun replaceToProfileFragment() {
        supportFragmentManager.commit {

            setReorderingAllowed(true)
            replace(R.id.fragmentContainerView, profileFragment)

        }
    }

    private fun changeProgressBarVisibility(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setTableLayoutClickability(state: Boolean) {
        if (state) {
            enableTabLayout()
        } else {
            disableTabLayout()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun disableTabLayout() {
        val tabStrip = binding.tabLayout.getChildAt(0) as LinearLayout
        for (i in 0 until tabStrip.childCount) {
            tabStrip.getChildAt(i).setOnTouchListener { _, _ -> true }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun enableTabLayout() {
        val tabStrip = binding.tabLayout.getChildAt(0) as LinearLayout
        for (i in 0 until tabStrip.childCount) {
            tabStrip.getChildAt(i).setOnTouchListener { _, _ -> false }
        }
    }
}