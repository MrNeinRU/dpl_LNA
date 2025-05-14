package ru.malygin.anytoany.ui.platform

//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material.Button
//import androidx.compose.material.Text
//import androidx.compose.material.TextField
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.unit.dp
//import dev.zt64.compose.pdf.LocalPdfState
//import dev.zt64.compose.pdf.PdfState
//import dev.zt64.compose.pdf.component.PdfColumn
//import dev.zt64.compose.pdf.component.PdfPage
//import kotlinx.coroutines.launch
//import me.saket.telephoto.zoomable.rememberZoomableState
//import me.saket.telephoto.zoomable.zoomable
//import ru.malygin.anytoany.data.dtos.PDFDto
//import ru.malygin.anytoany.data.events.createPDFile
//import ru.malygin.anytoany.data.exp.LocalPdfStateModified
//import ru.malygin.anytoany.data.pdf.PDFWorker
//
//
//@Composable
//actual fun pdfViewerPlatform() {
//    var textFieldText by remember { mutableStateOf("") }
//    var pdf: PdfState? by remember {
//        mutableStateOf(null, referentialEqualityPolicy())
//    }
//    val coroutine = rememberCoroutineScope()
//
//
//    Column(
//        Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        TextField(
//            value = textFieldText,
//            onValueChange = {
//                textFieldText = it
//                            },
//            placeholder = {
//                Text("File name")
//            }
//        )
//
//        Row {
//            Button(
//                modifier = Modifier,
//                onClick = {
//                    coroutine.launch {
//                        createPDFile(
//                            pdfDto = PDFDto(
//                                fileName = textFieldText
//                            )
//                        )
//                    }
//                }
//            ) {
//                Text("Create pdf")
//            }
//
//            Button(
//                modifier = Modifier,
//                onClick = {
//                    coroutine.launch {
//                        pdf = LocalPdfStateModified(
//                            PDFWorker(
//                                PDFDto(
//                                    fileName = textFieldText
//                                )
//                            ).getFileLocation()
//                        )
//                    }
//                }
//            ) {
//                Text("View pdf")
//            }
//        }
//
//        AnimatedVisibility(
//            visible = pdf != null
//        ){
//            pdfView(pdf = pdf!!)
//        }
//
//        AnimatedVisibility(
//            visible = pdf == null
//        ){
//            pdfViewIfNull()
//        }
//
//    }
//}
//
//@Composable
//private fun pdfView(
//    pdf: PdfState
//){
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        val lazyListState = rememberLazyListState()
//
//        PdfColumn(
//            state = pdf,
//            modifier = Modifier
//                .fillMaxSize(),
//            lazyListState = lazyListState,
//            page = {i: Int ->
//                PdfPage(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .zoomable(rememberZoomableState())
//                        .border(1.dp, Color.Black),
//                    index = i,
//                    state = pdf,
//                    contentScale = ContentScale.FillWidth
//                )
//            }
//        )
//
//        val currentPage by remember {
//            derivedStateOf { lazyListState.firstVisibleItemIndex + 1 }
//        }
//
//        Text(
//            modifier = Modifier
//                .background(Color.Cyan)
//                .padding(
//                    horizontal = 8.dp,
//                    vertical = 4.dp
//                )
//                .align(Alignment.BottomCenter),
//            text = "Страница $currentPage из ${pdf.pageCount}"
//        )
//    }
//}
//
//@Composable
//private fun pdfViewIfNull(){
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Text(
//            modifier = Modifier
//                .align(Alignment.Center),
//            text = "No PDF"
//        )
//    }
//}