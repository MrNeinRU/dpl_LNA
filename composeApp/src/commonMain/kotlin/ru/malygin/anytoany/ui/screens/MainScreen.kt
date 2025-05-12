package ru.malygin.anytoany.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.malygin.anytoany.data.adapters.NetworkingAdapter
import ru.malygin.anytoany.data.data_cls.UserInformation
import ru.malygin.anytoany.data.dateTime.getTodayDate
import ru.malygin.anytoany.data.utils.ImgReader

object MainScreen {

    @Composable
    fun mainScreen() {
        val coroutine = rememberCoroutineScope()
        var userInformation: UserInformation? by remember { mutableStateOf(null) }
        var img: ImageBitmap? by remember { mutableStateOf(null) }


        coroutine.launch {
            userInformation = NetworkingAdapter().getUserInformation()
            userInformation?.let { rI ->
                img = ImgReader.readImageFrom(path = rI.image)
            }
        }

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "_${getTodayDate()}_",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            personalInfo(
                icon = img,
                name = userInformation?.name,
                post = userInformation?.post
            )
        }
    }

    @Composable
    private fun personalInfo(
        icon: ImageBitmap?,
        name: String?,
        post: String?
    ){
        Box(
            modifier = Modifier
                .heightIn(
                    max = 52.dp
                )
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                icon?.let { rI->
                    Canvas(
                        modifier = Modifier
                    ){
                        drawImage(
                            image = rI,
                            dstSize = IntSize(52, 52)
                        )
                    }
//                    Icon(
//                        bitmap = rI,
//                        contentDescription = null
//                    )
                } ?: run{
                    CircularProgressIndicator()
                }

                name?.let {
                    Column(
                        modifier = Modifier.padding(start = 52.dp)
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            text = name
                        )
                        Text(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            text = post!!
                        )
                    }
                }
            }
        }
    }
}