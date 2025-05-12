package ru.malygin.anytoany.ui.cmp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ){
            RoutingPaths.entries.forEachIndexed { ind, rp->
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        coroutine.launch{
                            navigator.push(rp.screen)
                            drawerState.close()
                        }
                    }
                ){
                    Text(rp.nameOfScreen)
                }
                if (ind != RoutingPaths.entries.size - 1)
                    Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}