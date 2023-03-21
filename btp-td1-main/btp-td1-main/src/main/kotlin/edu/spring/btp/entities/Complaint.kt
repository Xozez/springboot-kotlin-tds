package edu.spring.btp.entities

import jakarta.persistence.*

@Entity
class Complaint() {
    constructor(title: String, description: String, user: User?, provider: Provider, domain: Domain) : this() {
        this.title = title
        this.description = description
        this.user = user
        this.provider = provider
        this.domain = domain
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id = 0

    @Column(length = 155)
    open var title: String? = null

    @Lob
    open var description: String? = null

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    open var  user: User?=null

    @ManyToOne
    @JoinColumn(name = "provider_id")
    open lateinit var  provider: Provider

    @ManyToOne
    @JoinColumn(name = "domain_id")
    open lateinit var  domain: Domain

    @ManyToMany
    @JoinTable(name = "complaint_users")
    open var claimants= mutableListOf<User>()

}