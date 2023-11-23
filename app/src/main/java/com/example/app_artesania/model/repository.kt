package com.example.app_artesania.model

object DataRepository {
    private var user: User? = null

    fun setUser(newuser: User){
        user = newuser
    }

    fun getUser(): User?{
        return user
    }
}