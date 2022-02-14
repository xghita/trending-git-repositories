package com.test.gittrendingrepo.ui.repo

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.test.gittrendingrepo.R
import com.test.gittrendingrepo.databinding.ActivityRepoBinding
import com.test.gittrendingrepo.ui.repo.detail.RepoDetailFragment
import com.test.gittrendingrepo.ui.repo.list.RepoListFragment
import com.test.gittrendingrepo.ui.repo.list.RepoListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoActivity : AppCompatActivity() {

    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }
    private lateinit var binding: ActivityRepoBinding
    private val repoListViewModel by viewModel<RepoListViewModel>()
    private var hideMenuItem = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil
            .inflate(
                layoutInflater,
                R.layout.activity_repo,
                null,
                false
            )
        setContentView(binding.root)

        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            invalidateOptionsMenu()

            when (destination.label) {
                RepoListFragment::class.java.simpleName -> {
                    toolbarMenuConfig(false)
                    supportActionBar?.title = getString(R.string.repo_list_title)
                }
                RepoDetailFragment::class.java.simpleName -> {
                    toolbarMenuConfig(true)
                    supportActionBar?.title =
                        arguments?.getString("repo_name") ?: getString(R.string.repo_detail_title)
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (hideMenuItem) {
            super.onCreateOptionsMenu(menu)
            return !hideMenuItem
        }

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav -> repoListViewModel.sortByFavorite()
            R.id.trending -> repoListViewModel.sortByTrending()
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun toolbarMenuConfig(hide: Boolean) {
        hideMenuItem = hide
        supportActionBar?.setDisplayHomeAsUpEnabled(hide)
    }
}