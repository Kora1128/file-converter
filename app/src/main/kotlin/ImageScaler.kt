import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

object ImageScaler {
    fun reduceImageFile(
        inputFile: File,
        targetFileSize: Long,
        fileType: String,
    ): File {
        val outputFile = File(inputFile.parent, "compressed_${inputFile.name}")
        println("Reducing image file size for ${inputFile.name}, ${inputFile.length()} bytes to meet $targetFileSize bytes")

        try {
            val bufferedImage = ImageIO.read(inputFile)
            var quality = 1.0f

            do {
                val scaledImage = scaleImage(bufferedImage, quality)
                ImageIO.write(scaledImage, fileType, outputFile)
                quality -= 0.1f
                println("Each iteration output file size: ${outputFile.length()}")
                scaledImage.flush()
            } while (outputFile.length() > targetFileSize && quality > 0.1f)
        } catch (e: Exception) {
            throw IOException("Failed to reduce image file size: ${e.message}")
        }

        return outputFile
    }

    fun reduceImageFileusingStreams(
        inputFile: ByteArray,
        fileType: String,
    ): ByteArray {
        val inputStream = ByteArrayInputStream(inputFile)
        val outputStream = ByteArrayOutputStream()

        try {
            val bufferedImage = ImageIO.read(inputStream)
            var quality = 0.55f

            val scaledImage = scaleImage(bufferedImage, quality)
            ImageIO.write(scaledImage, fileType, outputStream)
            scaledImage.flush()
        } catch (e: Exception) {
            throw IOException("Failed to reduce image file size: ${e.message}")
        }

        return outputStream.toByteArray()
    }

    fun scaleImage(
        image: BufferedImage,
        scale: Float = 1f,
    ): BufferedImage {
        val width = (image.width * scale).toInt()
        val height = (image.height * scale).toInt()
        val scaledImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = scaledImage.createGraphics()
        graphics.drawImage(image, 0, 0, width, height, null)
        graphics.dispose()
        return scaledImage
    }
}
