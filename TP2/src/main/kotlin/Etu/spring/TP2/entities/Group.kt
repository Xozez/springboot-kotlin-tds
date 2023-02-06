package Etu.spring.TP2.entities

import jakarta.persistence.*

@Entity
class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null

    @Column(nullable = false, length = 60, unique = true)
    open lateinit var name: String

    @Column(length = 120)
    open var email : String? = null

    @Column(length = 45)
    open var aliases: String? = null

    @ManyToOne
    @JoinColumn(name = "organizationId", nullable = false)
    open lateinit var groupOrganization : Organization

    @ManyToMany(mappedBy = "groups")
    open val users : Set<User>? = HashSet()
}