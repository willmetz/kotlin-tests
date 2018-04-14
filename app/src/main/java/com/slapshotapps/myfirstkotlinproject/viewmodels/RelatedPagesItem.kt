package com.slapshotapps.myfirstkotlinproject.viewmodels

import com.slapshotapps.network.response.Page
import com.slapshotapps.network.response.RelatedPagesModel


class RelatedPagesItem(private val relatedPage: Page) {

    fun getTitle(): String{
        return relatedPage.displaytitle
    }

    fun getDescription() : String{
        return relatedPage.description
    }

    fun getThumbnailUrl() :String{
        if(relatedPage.thumbnail?.source == null){
            return ""
        }else{
            return relatedPage.thumbnail.source
        }
    }
}