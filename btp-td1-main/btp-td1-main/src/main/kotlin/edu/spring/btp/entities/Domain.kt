package edu.spring.btp.entities

import jakarta.persistence.*

@Entity
class Domain() {
    constructor(name: String, parent: Domain?) : this() {
        this.name = name
        this.parent = parent
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id = 0

    @Column(length = 50, unique = true, nullable = false)
    open var name: String? = null

    @Lob
    open var description: String? = null

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_id")
    open var parent:Domain? = null

    @OneToMany(mappedBy = "parent")
    open var children= mutableListOf<Domain>()

    @ManyToMany(mappedBy = "domains")
    open var providers= mutableListOf<Provider>()

    @OneToMany(mappedBy = "domain")
    open var complaints= mutableListOf<Complaint>()

    fun hasChildren():Boolean {
        return children.isNotEmpty()
    }

    fun getComplainsCount():Int{
        var count = complaints.size
        children.forEach { count += it.getComplainsCount() }
        return count
    }

}