package sofiarodfer.project6.entity

import jakarta.persistence.*

@Entity
@Table(name = "accounts")
data class Account(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(nullable = false, unique = true, length = 8)
    val cep: String,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: User
)