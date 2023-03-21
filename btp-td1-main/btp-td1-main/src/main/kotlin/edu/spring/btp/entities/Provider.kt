package edu.spring.btp.entities

import jakarta.persistence.*

@Entity
class Provider() {
    constructor(rs: String) : this() {
        this.rs = rs
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id = 0

    @Column(length = 150, nullable = false, unique = true)
    lateinit var rs: String

    @Column(length=255)
    open var address: String? = null

    @Column(length=10)
    open var postalCode: String? = null

    @Column(length=50)
    open var city: String? = null

    @Column(length=25)
    open var phone: String? = null

    @ManyToMany
    @JoinTable(name = "domain_providers")
    open var domains= mutableListOf<Domain>()

    @OneToMany(mappedBy="provider")
    open var complaints= mutableListOf<Complaint>()
}