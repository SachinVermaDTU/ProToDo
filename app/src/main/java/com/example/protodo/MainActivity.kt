package com.example.protodo

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf


import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.compose.material3.Icon as MaterialIcon
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.findNavController
import com.example.protodo.ui.theme.ProToDoTheme

import androidx.navigation.compose.rememberNavController
import com.example.protodo.design.Home
import com.example.protodo.design.Setting
import com.example.protodo.design.chat

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews :Boolean,
    val badgeCount: Int? = null

)
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProToDoTheme {
                val navController = rememberNavController()
                val items = listOf(
                    BottomNavigationItem(
                        title = "Home",
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = "Chat",
                        selectedIcon = Icons.Filled.Email,
                        unselectedIcon = Icons.Outlined.Email,
                        hasNews = false,
                        badgeCount = 45
                    ),
                    BottomNavigationItem(
                        title = "Setting",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        hasNews = false,
                        badgeCount = 45
                )
                )
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   Scaffold(
                       bottomBar = {
                           NavigationBar {
                               items.forEachIndexed { index, item->
                                   NavigationBarItem(
                                       selected = selectedItemIndex==index,
                                       onClick = {
                                                 selectedItemIndex = index
                                           navigateToDestination(navController, index)
                                           // navController.navigate(item.title)
                                       },
                                       icon = {
                                           //The first if statement checks if the badgeCount property of the item is not null.
                                           // If it's not null, it means there is a specific count to display on the badge.
                                           // In this case, a Badge composable is created, and a Text composable is placed inside it to
                                           // display the badgeCount converted to a string.
                                           //The else if statement checks if the hasNews property of the item is true. If it's true,
                                           // it means there is news or new content available. In this case, an empty Badge composable is created,
                                           // indicating the presence of news without displaying a specific count.
                                           BadgedBox(badge = {
                                               if(item.badgeCount != null){
                                                   Badge {
                                                       Text(text = item.badgeCount.toString())
                                                   }}
                                                   else if(item.hasNews){
                                                       Badge()
                                                   }

                                           }) {
                                               Icon(
                                                   imageVector = if(index== selectedItemIndex){
                                                   item.selectedIcon }
                                                   else item.unselectedIcon,
                                                   contentDescription = item.title
                                               )
                                           }
                                       }
                                   )
                               }
                           }
                       }
                   ) {
                       NavHost(navController = navController, startDestination = "home") {
                           composable("home") {
                               Home()
                           }
                           composable("chat") {
                               chat()
                           }
                           composable("settings") {
                               Setting()
                           }
                       }
                   }

                   }
                }


        }}

    private fun navigateToDestination(navController: NavHostController, index: Int) {

            when (index) {
                0 -> navController.navigate("home")
                1 -> navController.navigate("chat")
                2 -> navController.navigate("settings")
            }

    }
}





