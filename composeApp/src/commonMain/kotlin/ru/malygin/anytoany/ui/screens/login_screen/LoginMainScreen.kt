package ru.malygin.anytoany.ui.screens.login_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.launch
import ru.malygin.anytoany.data.adapters.AuthorizationRequestState
import ru.malygin.anytoany.data.adapters.NetworkingAdapter
import ru.malygin.anytoany.data.view_models.LoginModel

class LoginMainScreen(
    val screenModel: LoginModel
) {

    @Composable
    fun loginScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(.1f))
                loginTitle()
                loginMainBox()
            }
        }
    }

    @Composable
    private fun loginTitle(
        title: String = "Авторизация пользователя"
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(70.dp, 130.dp)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ){
            Text(
                color = Color.White,
                text = title
            )
        }
    }

    @Composable
    private fun loginMainBox(

    ){
        var idfText by remember { mutableStateOf("") }
        var pasText by remember { mutableStateOf("") }
        val crt = rememberCoroutineScope()

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.DarkGray),
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    loginTextUpdate(
                        title = "Идентификатор",
                        text = idfText,
                        onUpdate = {
                            idfText = it
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    loginTextUpdate(
                        title = "Пароль",
                        text = pasText,
                        onUpdate = {
                            pasText = it
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    loginButton(
                        onClick = {
                            screenModel.onLogin(idfText, pasText)
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        modifier = Modifier
                            .clickable {

                            }.align(Alignment.Start),
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline,
                        text = "Обратится за помощью →"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

    @Composable
    private fun loginTextUpdate(
        title: String = "undefined",
        text: String,
        onUpdate: (String) -> Unit
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                color = Color.White,
                fontSize = 12.sp,
                text = title
            )

            OutlinedTextField(
                singleLine = true,
                colors = TextFieldDefaults.colors(),
                modifier = Modifier
                    .fillMaxWidth(),
                value = text,
                onValueChange = {
                    onUpdate(it)
                }
            )
        }
    }

    @Composable
    private fun loginButton(
        onClick: () -> Unit
    ){
        Button(
            enabled = screenModel.loginState.collectAsState().value !is AuthorizationRequestState.Processing,
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.Gray
            ),
            onClick = {onClick()}
        ){
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                text = "Вход"
            )
        }
    }
}