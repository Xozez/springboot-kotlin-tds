package Etu.spring.TP2.entities

import jakarta.persistence.*

@Entity
class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var organizationId: Int? = null

    @Column(nullable = false, length = 60, unique = true)
    open lateinit var name: String

    @Column(length = 45)
    open var domain: String? = null

    @Column(length = 45)
    open var aliases: String? = null

    @OneToMany(mappedBy = "organisation",fetch = FetchType.EAGER,cascade = [CascadeType.ALL])
    open val users : Set<User>? = HashSet()

    @OneToMany(mappedBy = "groupOrganization")
    open val groups : Set<Group>? = HashSet()
}