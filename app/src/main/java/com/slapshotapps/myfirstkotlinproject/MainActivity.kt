package com.slapshotapps.myfirstkotlinproject

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.slapshotapps.myfirstkotlinproject.databinding.ActivityMainBinding
import com.slapshotapps.myfirstkotlinproject.viewmodels.MainViewModel
import com.slapshotapps.network.WikiApiService

class MainActivity : AppCompatActivity(), MainViewModel.MainViewModelInterface {

    private val viewModel = MainViewModel(WikiApiService.create())
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.termSearchButton.setOnClickListener({
            viewModel.onGetSearchCount(binding.termInputField.text.toString())
        })

        binding.halloweenPhraseButton.setOnClickListener({
            viewModel.onGetHalloweenPhrase()
        })

        viewModel.setListener(this)

        val menu = binding.bottomNavMenu.menu

        menu.findItem(R.id.action_home)?.setEnabled(false);
        menu.findItem(R.id.action_headlines)?.setEnabled(true);

        binding.bottomNavMenu.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {

                //using the when clause here, which is essentially the switch statement
                override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId){
                    R.id.action_headlines -> navigateToHeadlines()
                    else -> false
                }
            })
    }

    override fun onStop() {
        super.onStop()

        viewModel.onDisconnect()
    }

    override fun showHalloweenMsg(msg: String) {
        AlertDialog.Builder(this)
                .setTitle("Halloween Message")
                .setMessage(msg)
                .setCancelable(true)
                .show()
    }

    override fun showMessage(msg: String?) {
        binding.messageText.setText(msg);
    }

    private fun navigateToHeadlines() : Boolean{

        val term = binding.termInputField.text.toString()

        //term can't be null here, so just check if it is blank
        if(!term.isBlank()) {
            startActivity(
                RelatedPagesActivity.newIntent(binding.termInputField.text.toString(), this))
        }else{
            AlertDialog.Builder(this)
                .setTitle("Action Needed")
                .setMessage("Please add a term first")
                .setCancelable(true)
                .show()
        }
        return true;
    }
}
