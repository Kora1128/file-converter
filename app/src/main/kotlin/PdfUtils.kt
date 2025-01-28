import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

object PdfUtils {
    fun convertToPdf(inputFile: File): File {
        val outputPdfFile = File(inputFile.parent, "${inputFile.nameWithoutExtension}.pdf")
        println("Converting ${inputFile.name} to PDF as ${outputPdfFile.name}")

        try {
            val document =
                Document(
                    PdfDocument(PdfWriter(outputPdfFile)),
                )
            val imageData =
                ImageDataFactory
                    .create(inputFile.absolutePath)
            val pdfImage = Image(imageData)
            document.add(pdfImage)
            document.close()
        } catch (e: Exception) {
            throw IOException("Failed to convert file to PDF: ${e.message}")
        }

        return outputPdfFile
    }

    fun compressPDF(
        inputFile: File,
        imageQuality: Float = 0.5f,
    ): File {
        val outputFile = File(inputFile.parent, "compressed_${inputFile.name}")

        PDDocument.load(inputFile).use { document ->
            val compressedDoc = PDDocument()

            for (pageIndex in 0 until document.numberOfPages) {
                val page = document.getPage(pageIndex)
                val newPage = PDPage(page.mediaBox)
                compressedDoc.addPage(newPage)

                val renderer = PDFRenderer(document)
                val image = renderer.renderImageWithDPI(pageIndex, 150f, ImageType.RGB)
                val compressedImage = ImageScaler.scaleImage(image)
                val pdImage = JPEGFactory.createFromImage(compressedDoc, compressedImage, imageQuality)

                PDPageContentStream(compressedDoc, newPage).use { contentStream ->
                    contentStream.drawImage(pdImage, 0f, 0f, page.mediaBox.width, page.mediaBox.height)
                }
            }

            compressedDoc.save(outputFile)
            compressedDoc.close()
        }

        println("Size of file is ${outputFile.length()}")
        return outputFile
    }

    fun convertToPdfusingStream(inputFile: ByteArray): ByteArray {
        val inputStream = ByteArrayInputStream(inputFile)
        val outputStream = ByteArrayOutputStream()

        try {
            val document =
                Document(
                    PdfDocument(PdfWriter(outputStream)),
                )
            val imageData =
                ImageDataFactory
                    .create(inputStream.readBytes())
            val pdfImage = Image(imageData)
            document.add(pdfImage)
            document.close()
        } catch (e: Exception) {
            throw IOException("Failed to convert file to PDF: ${e.message}")
        }

        return outputStream.toByteArray()
    }

    fun compressPdfUsingStream(
        fileData: ByteArray,
        imageQuality: Float = 0.5f,
    ): ByteArray {
        val inputStream = ByteArrayInputStream(fileData)
        val outputStream = ByteArrayOutputStream()

        PDDocument.load(inputStream).use { document ->
            val compressedDoc = PDDocument()

            for (pageIndex in 0 until document.numberOfPages) {
                val page = document.getPage(pageIndex)
                val newPage = PDPage(page.mediaBox)
                compressedDoc.addPage(newPage)

                val renderer = PDFRenderer(document)
                val image = renderer.renderImageWithDPI(pageIndex, 150f, ImageType.RGB)
                val compressedImage = ImageScaler.scaleImage(image)
                val pdImage = JPEGFactory.createFromImage(compressedDoc, compressedImage, imageQuality)

                PDPageContentStream(compressedDoc, newPage).use { contentStream ->
                    contentStream.drawImage(pdImage, 0f, 0f, page.mediaBox.width, page.mediaBox.height)
                }
            }

            compressedDoc.save(outputStream)
            compressedDoc.close()
        }

        return outputStream.toByteArray()
    }
}
