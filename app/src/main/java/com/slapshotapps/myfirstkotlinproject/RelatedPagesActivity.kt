package com.slapshotapps.myfirstkotlinproject

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.slapshotapps.myfirstkotlinproject.adapters.RelatedPagesAdapter
import com.slapshotapps.myfirstkotlinproject.databinding.ActivityRelatedPagesBinding
import com.slapshotapps.myfirstkotlinproject.viewmodels.RelatedPagesItem
import com.slapshotapps.myfirstkotlinproject.viewmodels.RelatedPagesViewModel
import com.slapshotapps.network.WikiApiService
import com.slapshotapps.network.WikiRestApiService

import kotlinx.android.synthetic.main.activity_related_pages.*

class RelatedPagesActivity : AppCompatActivity(), RelatedPagesViewModel.RelatedPagesListener {

    lateinit var viewModel : RelatedPagesViewModel
    lateinit var binding : ActivityRelatedPagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_related_pages)

        viewModel = RelatedPagesViewModel(WikiRestApiService.create())

        val menu = binding.bottomNav.menu

        menu.findItem(R.id.action_home)?.setEnabled(true);
        menu.findItem(R.id.action_headlines)?.setEnabled(false);

        binding.bottomNav.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId){
                    R.id.action_home -> navigateHome()
                    else -> false
                }
            })
    }


    override fun onResume() {
        super.onResume()

        viewModel.onViewAttached(this, intent.getStringExtra(INTENT_TERM))
    }

    override fun onPause() {
        super.onPause()

        viewModel.onViewDetached()
    }

    override fun onDataLoaded(items: List<RelatedPagesItem>) {
        val myAdapter = RelatedPagesAdapter(items, this)

        binding.relatedPagesList.layoutManager = LinearLayoutManager(this)
        binding.relatedPagesList.adapter = myAdapter
    }

    override fun onError() {
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun navigateHome() : Boolean{
        return true
    }

    companion object {
        private val INTENT_TERM = "searchTerm"

        fun newIntent(term : String, context : Context) : Intent{
            val intent = Intent(context, RelatedPagesActivity::class.java)
            intent.putExtra(INTENT_TERM, term)

            return intent
        }
    }

}
