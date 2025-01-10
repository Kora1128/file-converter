import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileConverterTest {
    private lateinit var testResourcesDir: File

    @BeforeAll
    fun setup() {
        testResourcesDir = File("src/test/resources")
    }

    @Test
    fun `test jpeg compression with target size receipt`() {
        // Given
        val inputFile = File(testResourcesDir, "Sample-jpg-image-15mb.jpeg")
        val targetSize = 9 * 1024L * 1024L // 9MB
        val supportedTypes = listOf("jpg", "jpeg", "png", "pdf")
        println(inputFile.length())

        // When
        val result = FileConverter.processFile(targetSize, supportedTypes, inputFile)

        // Then
        assertNotNull(result)
        assertTrue(result!!.exists())
        assertTrue(result.length() <= targetSize)
        assertEquals("jpeg", result.extension.lowercase())
    }

    @Test
    fun `test png compression with target size satillite`() {
        // Given
        val inputFile = File(testResourcesDir, "f19d2c44-64ad-4a6b-b775-da53f74ea21a-RU5QiJ.org.png")
        val targetSize = 9 * 1024L * 1024L // 9MB
        val supportedTypes = listOf("jpg", "jpeg", "png", "pdf")
        println(inputFile.length())

        // When
        val result = FileConverter.processFile(targetSize, supportedTypes, inputFile)

        // Then
        assertNotNull(result)
        assertTrue(result!!.exists())
        assertTrue(result.length() <= targetSize)
        assertEquals("png", result.extension.lowercase())
    }

    @Test
    fun `test jpg compression with target size`() {
        // Given
        val inputFile = File(testResourcesDir, "22mb.jpg")
        val targetSize = 9 * 1024L * 1024L // 9MB
        val supportedTypes = listOf("jpg", "jpeg", "png", "pdf")
        println(inputFile.length())

        // When
        val result = FileConverter.processFile(targetSize, supportedTypes, inputFile)

        // Then
        assertNotNull(result)
        assertTrue(result!!.exists())
        assertTrue(result.length() <= targetSize)
        assertEquals("jpg", result.extension.lowercase())
    }

    @Test
    fun `test gif compression with target size`() {
        // Given
        val inputFile = File(testResourcesDir, "consultancy.gif")
        val targetSize = 9 * 1024L * 1024L // 9MB
        val supportedTypes = listOf("jpg", "jpeg", "png", "pdf")
        println(inputFile.length())

        // When
        val result = FileConverter.processFile(targetSize, supportedTypes, inputFile)

        // Then
        assertNotNull(result)
        assertTrue(result!!.exists())
        assertTrue(result.length() <= targetSize)
        assertEquals("pdf", result.extension.lowercase())
    }

    @Test
    fun `test PDF compression with target size`() {
        // Given
        val inputFile = File(testResourcesDir, "10840-002.pdf")
        val targetSize = 9 * 1024L * 1024L // 9MB
        val supportedTypes = listOf("jpg", "jpeg", "png", "pdf")

        // When
        val result = FileConverter.processFile(targetSize, supportedTypes, inputFile)

        // Then
        assertNotNull(result)
        assertTrue(result!!.exists())
        assertTrue(result.length() <= targetSize)
        assertEquals("pdf", result.extension.lowercase())
    }
}
