package sofiarodfer.project6.entity

import jakarta.persistence.*

@Entity
@Table(name = "pages")
data class Page(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 50)
    val name: String

)