package com.example.app

/**
 * Receive callbacks from the listFragment
 */
interface ListCallBack {
    fun onFavorite(id: Int, fav: Boolean)
    fun onDetails(id: Int, position : Int)
    fun onSearch(search: String)
    fun onLeaveSearch()
}