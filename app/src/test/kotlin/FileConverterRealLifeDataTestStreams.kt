import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileConverterRealLifeDataTestStreams {
    private lateinit var testResourcesDir: File

    @BeforeAll
    fun setup() {
        testResourcesDir = File("src/test/resources/files")
    }

    @Test
    fun `test jpeg compression with target size receipt`() {
        // Given
        val targetSize = 9 * 1024L * 1024L // 9MB
        val supportedTypes = listOf("jpg", "jpeg", "png", "pdf")

        assertTrue(testResourcesDir.exists() && testResourcesDir.isDirectory)

        println(System.currentTimeMillis())

        testResourcesDir.listFiles()?.forEach { inputFile ->
            if (inputFile.isFile) {
                println("Processing file: ${inputFile.name}, size: ${inputFile.length()} bytes")

                // When
                val fileResult = FileConverter.processFileUsingStreams(supportedTypes, inputFile.readBytes(), inputFile.extension)

                val result = byteArrayToFile(fileResult, "src/test/resources/files/compressed_${inputFile.name}")

                assertNotNull(result) { "Result should not be null for file: ${inputFile.name}" }
                assertTrue(result!!.exists()) { "Processed file does not exist: ${inputFile.name}" }
                assertTrue(result.length() <= targetSize) { "Processed file exceeds target size: ${inputFile.name}" }
                assertTrue(supportedTypes.contains(result.extension.lowercase())) {
                    "File extension not in supported types for file: ${inputFile.name}"
                }
            }
        }

        println(System.currentTimeMillis())
    }

    fun byteArrayToFile(
        byteArray: ByteArray,
        outputFilePath: String,
    ): File {
        val outputFile = File(outputFilePath)
        outputFile.outputStream().use { it.write(byteArray) }
        return outputFile
    }
}
