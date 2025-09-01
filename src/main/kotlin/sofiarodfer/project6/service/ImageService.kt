package sofiarodfer.project6.service

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import sofiarodfer.project6.dto.ImageDTO
import sofiarodfer.project6.entity.Image
import sofiarodfer.project6.mapper.toDTO
import sofiarodfer.project6.mapper.toEntity
import sofiarodfer.project6.repository.ImageRepository
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class ImageService(
    private val imageRepository: ImageRepository,
    private val pageService: PageService
) {
    private val uploadLocation: Path = Paths.get("uploads")

    init {
        if (!Files.exists(uploadLocation)) {
            Files.createDirectory(uploadLocation)
        }
    }

    fun saveLocalImage(
        file: MultipartFile,
        altText: String,
        identifierTag: String,
        pageId: Long
    ): ImageDTO {
        if (imageRepository.findByIdentifierTag(identifierTag).isPresent) {
            throw IllegalArgumentException("Image with identifier '$identifierTag' already exists.")
        }
        val page = pageService.findById(pageId)

        val uniqueFileName = "${UUID.randomUUID()}-${file.originalFilename}"
        val destinationFile = uploadLocation.resolve(uniqueFileName).toAbsolutePath()

        Files.copy(file.inputStream, destinationFile)

        val imageEntity = Image(
            fileName = uniqueFileName,
            contentType = file.contentType ?: "application/octet-stream",
            altText = altText,
            identifierTag = identifierTag,
            visible = true,
            page = page.toEntity()
        )
        return imageRepository.save(imageEntity).toDTO()
    }

    fun findImagesByPageId(pageId: Long): List<ImageDTO> {
        return imageRepository.findByPageId(pageId).map { img -> img.toDTO() }
    }

    fun findByIdentifierTag(identifierTag: String): ImageDTO {
        val image = imageRepository.findByIdentifierTag(identifierTag)
            .orElseThrow { RuntimeException("Image not found with tag: $identifierTag") }
        return image.toDTO()
    }

    fun findAndLoadImage(identifierTag: String): Pair<ImageDTO, Resource> {
        val image = imageRepository.findByIdentifierTag(identifierTag)
            .orElseThrow { RuntimeException("Image not found with tag: $identifierTag") }

        val resource = loadImageAsResource(image.fileName)
        val dto = image.toDTO()

        return Pair(dto, resource)
    }

    fun loadImageAsResource(fileName: String): Resource {
        try {
            val filePath = uploadLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists() || resource.isReadable) {
                return resource
            } else {
                throw RuntimeException("Could not read the file!")
            }
        } catch (e: MalformedURLException) {
            throw RuntimeException("Error: " + e.message)
        }
    }
}