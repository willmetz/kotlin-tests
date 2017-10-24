package com.slapshotapps.myfirstkotlinproject


class MyFirstKotlinClass (favoriteCookie: String){
    private val favoriteCookie = favoriteCookie.toUpperCase()


    fun getBestCookieEver() : String{
        return favoriteCookie
    }
}


