package com.slapshotapps.myfirstkotlinproject.viewmodels

import com.slapshotapps.network.WikiRestApiService
import com.slapshotapps.network.response.Page
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class RelatedPagesViewModel(wikiApiService: WikiRestApiService) {

    interface RelatedPagesListener {
        fun onDataLoaded(items: List<RelatedPagesItem>)
        fun onError()
    }

    val wikiApiService: WikiRestApiService
    var listener: RelatedPagesListener? = null

    init {
        this.wikiApiService = wikiApiService
    }


    fun onViewAttached(listener: RelatedPagesListener, term: String) {
        this.listener = listener

        wikiApiService.relatedPages(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ relatedPages ->

                val relatedPagesItems = ArrayList<RelatedPagesItem>()

                for (model: Page in relatedPages.pages) {
                    relatedPagesItems.add(RelatedPagesItem(model))
                }

                listener.onDataLoaded(relatedPagesItems)
            }, {
                listener.onError()
            })
    }

    fun onViewDetached() {
        listener = null
    }
}