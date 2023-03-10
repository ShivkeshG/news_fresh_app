package com.example.newsfresh.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsfresh.R
import com.example.newsfresh.databinding.ActivityNewsBinding
import com.example.newsfresh.db.ArticleDatabase
import com.example.newsfresh.repository.NewsRepository
import com.example.newsfresh.util.ConnectionState

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentNewsContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.breakingNewsFragment -> showBottomNav()
                R.id.searchFragment -> showBottomNav()
                R.id.savedNewsFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]
    }

    private fun updateUI(networkState: Boolean) {

    }

    private fun hideBottomNav() {
        binding.bottomNav.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.bottomNav.visibility = View.VISIBLE
    }
}