package ru.malygin.anytoany.data.dtos

data class PDFDtoUI(
    val fileName: String = "unknown"
){

    fun toPDFDto(): PDFDto {
        return PDFDto(
            fileName = this.fileName
        )
    }
}