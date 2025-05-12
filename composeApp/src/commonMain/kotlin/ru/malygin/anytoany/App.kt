package ru.malygin.anytoany

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.malygin.anytoany.data.constants.__Fake__database_DAO
import ru.malygin.anytoany.data.routing.HomeScreen
import ru.malygin.anytoany.data.routing.LoginScreen
import ru.malygin.anytoany.data.routing.getScreenRoute
import ru.malygin.anytoany.ui.cmp.modalDrawerContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutine = rememberCoroutineScope()

    MaterialTheme {
        Navigator(
            if (__Fake__database_DAO.getStorageToken() != null)
                HomeScreen()
            else
                LoginScreen()
        ){navigator ->
            ModalNavigationDrawer(
                gesturesEnabled = false,
                drawerContent = {
                    modalDrawerContent(
                        drawerState = drawerState,
                        navigator = navigator
                    )
                },
                drawerState = drawerState
            ){
                Scaffold(
                    topBar = {
                        if (__Fake__database_DAO.getStorageToken() != null)
                            TopAppBar(
                                expandedHeight = 52.dp,
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                title = { Text(navigator.lastItem.getScreenRoute().titleRu) },
                                navigationIcon = {
                                    if (navigator.canPop) {
                                        IconButton(onClick = { navigator.pop() }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Back",
                                            )
                                        }
                                    }else{
                                        IconButton(
                                            onClick = {
                                                coroutine.launch { drawerState.open() }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.List,
                                                contentDescription = null,
                                            )
                                        }
                                    }
                                },
                            )
                    }
                ){pdV->
                    Box(Modifier.padding(pdV)) {
                        CurrentScreen()
                    }
                }
            }
        }
    }
}