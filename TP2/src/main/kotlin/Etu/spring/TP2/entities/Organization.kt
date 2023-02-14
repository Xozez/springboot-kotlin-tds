package Etu.spring.TP2.entities

import jakarta.persistence.*

@Entity
class Organization{
    constructor (name: String){
        this.name = name
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var organizationId: Int? = null

    @Column(nullable = false, length = 60, unique = true)
    open lateinit var name: String

    @Column(length = 45)
    open var domain: String = ""

    @Column(length = 45)
    open var aliases: String = ""

    @OneToMany(mappedBy = "organisation",fetch = FetchType.EAGER,cascade = [CascadeType.ALL])
    open val users : MutableSet<User> = HashSet()

    @OneToMany(mappedBy = "groupOrganization")
    open val groups : MutableSet<Group> = HashSet()

    fun addUser(user : User){
        if (users?.add(user)==true){
            user.organisation = this
        }
    }
}