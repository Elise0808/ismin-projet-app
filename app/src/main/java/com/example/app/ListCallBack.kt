package com.example.app

interface ListCallBack {
    fun onFavorite(id: Int, fav: Boolean)
    fun onDetails(id: Int)
}