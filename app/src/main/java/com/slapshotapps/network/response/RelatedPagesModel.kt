package com.slapshotapps.network.response


class RelatedPagesModel(val pages: List<Page>)

class Page(val displaytitle: String, val description: String, val thumbnail: ThumbnailImage?)

class ThumbnailImage(val source: String?, val width: Int, val height: Int, val original: String)