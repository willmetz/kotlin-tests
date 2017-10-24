package com.slapshotapps.myfirstkotlinproject.viewmodels

import com.slapshotapps.network.response.Page
import com.slapshotapps.network.response.RelatedPagesModel


class RelatedPagesItem(val relatedPage: Page) {

    public fun getTitle(): String{
        return relatedPage.displaytitle
    }

    public fun getDescription() : String{
        return relatedPage.description
    }

    public fun getThumbnailUrl() :String{
        if(relatedPage.thumbnail == null || relatedPage.thumbnail.source == null){
            return ""
        }else{
            return relatedPage.thumbnail.source
        }
    }
}