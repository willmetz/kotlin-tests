package com.slapshotapps.myfirstkotlinproject.viewmodels

import com.slapshotapps.network.WikiApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class MainViewModel(wikiApiService: WikiApiService) {

    interface MainViewModelInterface {
        fun showMessage(msg: String?)
    }

    private var disposable: Disposable? = null
    private var listener: MainViewModelInterface? = null
    private val service = wikiApiService

    public fun setListener(listener: MainViewModelInterface) {
        this.listener = listener
    }

    public fun onGetSearchCount(term: String?) {

        if (term != null && !term.isNullOrEmpty()) {
            searchTerm(term)
        } else {
            listener?.showMessage("Please enter a search term.")
        }
    }

    public fun onDisconnect() {
        disposable?.dispose()
    }

    private fun searchTerm(term: String) {
        disposable = service.hitCountCheck("query", "json", "search", term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                run {
                    val msg = String.format(Locale.US, "%s was found %d times",
                        term, result.query.searchinfo.totalhits)
                    listener?.showMessage(msg);
                }
            },
                { error ->
                    run {
                        listener?.showMessage(error?.message);
                    }
                });
    }


}