package sofiarodfer.project6.mapper

import sofiarodfer.project6.dto.PageDTO
import sofiarodfer.project6.dto.response.PageResponse
import sofiarodfer.project6.entity.Page


fun Page.toDTO() =
    PageDTO(
        id = this.id,
        name = this.name
    )

fun PageDTO.toResponse() =
    PageResponse(
        id = this.id,
        name = this.name
    )

fun PageDTO.toEntity() =
    Page(
        id = this.id,
        name = this.name
    )
