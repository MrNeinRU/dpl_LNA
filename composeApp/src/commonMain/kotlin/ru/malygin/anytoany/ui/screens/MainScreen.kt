package ru.malygin.anytoany.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.malygin.anytoany.data.adapters.NetworkingAdapter
import ru.malygin.anytoany.data.data_cls.UserInformation
import ru.malygin.anytoany.data.dateTime.getTodayDate
import ru.malygin.anytoany.data.enum_cls.OsType
import ru.malygin.anytoany.data.utils.ImgReader
import ru.malygin.anytoany.getPlatform

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
            personalInfo(
                icon = img,
                name = userInformation?.name,
                post = userInformation?.post
            )
            LazyColumn {

                item {
                    Spacer(
                        modifier = Modifier.height(30.dp)
                    )
                    fakeInfoBlock()
                }
                item {
                    Spacer(
                        modifier = Modifier.height(30.dp)
                    )
                    fakeInfoBlock(
                        title = "Задачи",
                        itemName = "n-задача",
                        itemDescription = "текст задачи"
                    )
                }
            }
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
                    min = 52.dp
                )
                .fillMaxWidth()
                .fillMaxHeight(.1f)
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                icon?.let { rI->
                    Image(
                        modifier = Modifier
                            .clip(
                                shape = RoundedCornerShape(100)
                            )
                            .border(2.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(100))
                            .padding(2.dp),
                        bitmap = rI,
                        contentDescription = null
                    )
                } ?: run{
                    CircularProgressIndicator()
                }

                name?.let {
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp)
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

    @Composable
    private fun fakeInfoBlock(
        title: String = "Обращения",
        itemName: String = "в n-отдел",
        itemDescription: String = "текст обращения"
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(.88f)
                    /*.widthIn(max = 322.dp)*/
                    .padding(vertical = 1.dp)
                    .background(Color.White)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = title)
                        Icon(
                            modifier = Modifier
                                .clickable {  },
                            imageVector = Icons.Default.ArrowOutward,
                            contentDescription = null
                        )
                    }
                    HorizontalDivider()

                    (1..3).forEach {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth().padding(horizontal = 8.dp)
                        ) {
                            Text(
                                fontSize = 16.sp,
                                text = itemName,
                            )
                            Text(
                                fontSize = 12.sp,
                                text = itemDescription
                            )
                        }
                    }

                }
            }
        }
    }
}