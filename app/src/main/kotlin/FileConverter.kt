import java.io.File
import java.io.IOException

object FileConverter {
    fun processFile(
        targetFileSize: Long, // Target file size in bytes
        supportedFileTypes: List<String>,
        inputFile: File,
    ): File? {
        if (!inputFile.exists() || inputFile.isDirectory) {
            throw IllegalArgumentException("Invalid input file")
        }
        val inputFileExtension = inputFile.extension.lowercase()

        val processedFile =
            if (supportedFileTypes.contains(inputFileExtension)) {
                inputFile
            } else {
                // Convert unsupported file types to PDF
                PdfUtils.convertToPdf(inputFile)
            }

        // Step 2: Reduce file size based on type
        return if (processedFile.extension in listOf("jpeg", "png", "gif", "jpg")) {
            ImageScaler.reduceImageFile(processedFile, targetFileSize, inputFileExtension)
        } else if (processedFile.extension == "pdf") {
            PdfUtils.compressPDF(processedFile)
        } else {
            throw IOException("File processing failed: Unsupported file format")
        }
    }

    fun processFileUsingStreams(
        supportedFileTypes: List<String>,
        inputFile: ByteArray,
        extension: String,
    ): ByteArray {
        val processedFile =
            if (supportedFileTypes.contains(extension)) {
                inputFile
            } else {
                // Convert unsupported file types to PDF
                PdfUtils.convertToPdfusingStream(inputFile)
            }

        // Step 2: Reduce file size based on type
        return if (extension in listOf("jpeg", "png", "gif", "jpg")) {
            ImageScaler.reduceImageFileusingStreams(processedFile, extension)
        } else if (extension == "pdf") {
            PdfUtils.compressPdfUsingStream(processedFile)
        } else {
            throw IOException("File processing failed: Unsupported file format")
        }
    }
}
