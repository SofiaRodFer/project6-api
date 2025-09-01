package sofiarodfer.project6.entity

import jakarta.persistence.*

@Entity
@Table(name = "images")
data class Image(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "identifier_tag", nullable = false, unique = true)
    val identifierTag: String,

    @Column(name = "file_name", nullable = false)
    val fileName: String,

    @Column(name = "content_type", nullable = false)
    val contentType: String,

    @Column(name = "alt_text", nullable = false)
    val altText: String,

    @Column(nullable = false)
    var visible: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    val page: Page
)