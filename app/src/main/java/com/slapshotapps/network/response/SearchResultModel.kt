package com.slapshotapps.network.response



object SearchResultModel {

  data class Result(val query: Query)
  data class Query(val searchinfo: SearchInfo)
  data class SearchInfo(val totalhits: Int)
}