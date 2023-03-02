package edu.spring.stories.entities

import jakarta.persistence.*


@Entity
class Tag {
    constructor (name: String, color: String){
        this.label = name
        this.color = color
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int? = null

    @Column(nullable = false, length = 60)
    open var label: String = ""

    @Column(nullable = false, length = 60)
    open var color: String = ""
}