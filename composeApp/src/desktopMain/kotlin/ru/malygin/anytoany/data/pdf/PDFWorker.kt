package ru.malygin.anytoany.data.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.font.Standard14Fonts
import ru.malygin.anytoany.data.dtos.PDFDto
import ru.malygin.anytoany.data.exceptions.FileAlreadyExistExc
import java.io.File

class PDFWorker(
    val pdfDto: PDFDto
) {
    private val locationPDF = "output/"
    private val plusPDF = ".pdf"
    private var docName: String = "unknown"

    fun createPDF(dto: PDFDto = this.pdfDto){
        docName = dto.fileName
        existPDF()
        createPDFFile()
    }

    fun getFileLocation(): File{
        docName = pdfDto.fileName
        return nameOfPDF()
    }

    private fun existPDF() {
        if (nameOfPDF().exists())
            throw FileAlreadyExistExc()
    }

    private fun createPDFFile(){
        val document = PDDocument()

        val firstPageGenerator = FirstPageGenerator(pdfDto).generate(document)
        document.addPage(firstPageGenerator)
        document.save(nameOfPDF())

        document.close()
    }

    private fun nameOfPDF(): File = File(locationPDF + docName + plusPDF)



    private class FirstPageGenerator(
        val pdfDto: PDFDto
    ){

        fun generate(document: PDDocument): PDPage{
            val page = PDPage()

            val titleWidth = calculateTitleWidth()
            val horizontalOffset = calculateHorizontalOffset(page, titleWidth)
            val verticalOffset = calculateVerticalOffset(page)

            val contentStream = PDPageContentStream(document, page)
            editContent(contentStream, horizontalOffset, verticalOffset)

            return page
        }

        private fun editContent(
            contentStream: PDPageContentStream,
            horizontalOffset: Float,
            verticalOffset: Float
        ) {
            contentStream.beginText()
            contentStream.setFont(PDType1Font(FONT), FONT_SIZE.toFloat())
            contentStream.newLineAtOffset(horizontalOffset, verticalOffset)
            contentStream.showText(TITLE+"_${pdfDto.fileName}")
            contentStream.endText()
            contentStream.close()
        }

        private fun calculateVerticalOffset(page: PDPage): Float =
            page.mediaBox.height / 2

        private fun calculateHorizontalOffset(page: PDPage, titleWidth: Float): Float =
            (page.mediaBox.width - titleWidth) / 2

        private fun calculateTitleWidth(): Float =
            Standard14Fonts.getAFM(FONT.getName()).getCharacterWidth(TITLE) / 1000 * FONT_SIZE

        companion object{
            private const val FONT_SIZE = 50
            private const val TITLE = "TEST"

            private val FONT = Standard14Fonts.FontName.TIMES_BOLD
        }
    }
}