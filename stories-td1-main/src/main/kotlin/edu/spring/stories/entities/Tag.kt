package edu.spring.stories.entities

import jakarta.persistence.*
import java.awt.Color

@Entity
class Tag {
    constructor (color: String, label: String){
        this.color = color
        this.label = label
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int = 0;

    @Column()
    open var color: String?;

    @Column()
    open var label: String = "";
}