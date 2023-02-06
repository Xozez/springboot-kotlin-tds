package Etu.spring.TP2.entities

import jakarta.persistence.*

@Entity
open class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var userId: Int? = null

    @Column( length = 30)
    open var firstName: String? = null

    @Column( length = 30)
    open var lastName: String? = null

    @Column (nullable = false, length = 60, unique = true)
    open lateinit var email: String

    open var password: String? = null

    open var suspended : Boolean = false

    @ManyToOne
    @JoinColumn(name = "organizationId", nullable = false)
    open lateinit var organisation : Organization

    @ManyToMany
    @JoinTable(name = "user_group", joinColumns = [JoinColumn(name = "userId")], inverseJoinColumns = [JoinColumn(name = "groupId")])
    open val groups : Set<Group>? = HashSet()
}