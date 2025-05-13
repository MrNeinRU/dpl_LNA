package ru.malygin.anytoany

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.malygin.anytoany.data.local_database.createTokenDatabase
import ru.malygin.anytoany.data.local_database.getRoomDatabase
import ru.malygin.anytoany.data.permissions.NetAnalysePermission
import ru.malygin.anytoany.data.wifi_analyser.WifiManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val generateDatabase = getRoomDatabase(createTokenDatabase(this.applicationContext))todo fix error
        /**
         * получение разрешения на сеть и геопозицию
         */
        NetAnalysePermission.getPermission(this)

//        //todo проверка получения разрешений
//        if (true){
//            WifiManager(this.applicationContext).addReceiver()
//            WifiManager(this.applicationContext).startScan()
//        }
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}