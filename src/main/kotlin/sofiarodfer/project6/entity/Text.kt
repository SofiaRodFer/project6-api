package sofiarodfer.project6.entity

import jakarta.persistence.*

@Entity
@Table(name = "texts")
data class Text(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "identifier_tag", nullable = false, unique = true)
    val identifierTag: String,

    @Column(columnDefinition = "TEXT", nullable = false)
    val content: String,

    @Column(nullable = false)
    var visible: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    val page: Page
)