package ru.malygin.anytoany.data.dtos

data class PDFDto(
    val fileName: String = "unknown"
){

    fun toPDFDtoUi(): PDFDtoUI {
        return PDFDtoUI(
            fileName = this.fileName
        )
    }
}
