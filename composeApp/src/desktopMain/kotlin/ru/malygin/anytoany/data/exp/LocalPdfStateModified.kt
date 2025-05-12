package ru.malygin.anytoany.data.exp

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import dev.zt64.compose.pdf.PdfState
import org.icepdf.core.pobjects.Document
import org.icepdf.core.pobjects.Page
import org.icepdf.core.util.GraphicsRenderingHints
import java.awt.image.BufferedImage
import java.io.File

@Stable
class LocalPdfStateModified(private val document: Document) : PdfState {
    override val pageCount: Int = document.numberOfPages

    constructor(file: File) : this(
        document = Document().apply {
            setFile(file.absolutePath)
        }
    )

    override fun renderPage(index: Int): Painter {
        //TODO("Not yet implemented")
        val image = document.getPageImage(
            // pageNumber =
            index,
            // renderHintType =
            GraphicsRenderingHints.SCREEN,//отвечает за качество + userZoom (2.0f)
            // pageBoundary =
            Page.BOUNDARY_CROPBOX,
            // userRotation =
            0f,
            // userZoom =
            1.6f//отвечает за качество + GraphicsRenderingHints.PRINT (значительно лучше качество)
        ) as BufferedImage

        val bmp = image.toComposeImageBitmap()//todo check this

        image.flush()//todo check this

        return BitmapPainter(bmp)
    }

    override fun close() {
        document.dispose()
    }
}
