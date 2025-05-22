package ru.malygin.anytoany.ui.cmp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch
import ru.malygin.anytoany.data.enum_cls.RoutingPaths
import ru.malygin.anytoany.data.routing.DplWrk2LocalNetworkAnalise
import ru.malygin.anytoany.data.routing.DplWrkScreen
import ru.malygin.anytoany.data.routing.LoginScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun modalDrawerContent(
    drawerState: DrawerState,
    navigator: Navigator
) {
    val coroutine = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = Modifier
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(
                    top = 16.dp,
                    end = 16.dp
                ),
            onClick = { coroutine.launch { drawerState.close() } },
            content = {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ){
                RoutingPaths.entries.forEachIndexed { ind, rp->
                    Button(
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            coroutine.launch{
                                if (rp.isUsed){
                                    navigator.push(rp.screen!!)
                                    drawerState.close()
                                }
                            }
                        }
                    ){
                        Text(rp.nameOfScreen)
                    }
                    if (ind != RoutingPaths.entries.size - 1)
                        Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ){
                Button(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        coroutine.launch{
                            navigator.push(LoginScreen())
                            drawerState.close()
                        }
                    }
                ){
                    Icon(
                        Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null,
                    )
                    Text("Выход")
                }
            }
        }
    }
}