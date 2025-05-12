package ru.malygin.anytoany.ui.cmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun trustedBlock(
    isTrusted: Boolean,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val tr = "Доверенное"
    val nTr = "Угроза"
    val vP = 4
    val hP = vP * 3
    val mod = Modifier.padding(paddingValues)
        .clip(
            shape = RoundedCornerShape(10.dp)
        )

    if (isTrusted) {
        Box(
            modifier = Modifier.then(mod)
                .background(Color(2, 156, 20)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = Color.White,
                modifier = Modifier
                    .padding(
                        vertical = vP.dp,
                        horizontal = hP.dp
                    ),
                textAlign = TextAlign.Center,
                text = tr
            )
        }
    }else {
        Box(
            modifier = Modifier.then(mod)
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = Color.White,
                modifier = Modifier
                    .padding(
                        vertical = vP.dp,
                        horizontal = hP.dp
                    ),
                textAlign = TextAlign.Center,
                text = nTr
            )
        }
    }
}