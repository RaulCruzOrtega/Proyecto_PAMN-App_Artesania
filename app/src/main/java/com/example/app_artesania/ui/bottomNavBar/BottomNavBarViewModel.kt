package com.example.app_artesania.ui.bottomNavBar

import androidx.lifecycle.ViewModel
import com.example.app_artesania.model.ItemsBottomNav

class BottomNavBarViewModel : ViewModel() {
    val items = listOf(
        ItemsBottomNav.homeIcon,
        ItemsBottomNav.favsIcon,
        ItemsBottomNav.ordersIcon,
        ItemsBottomNav.userIcon
    )
}