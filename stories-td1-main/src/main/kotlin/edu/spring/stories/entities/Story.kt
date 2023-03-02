package edu.spring.stories.entities

import jakarta.persistence.*


@Entity
open class Story {
    constructor (title: String ){
        this.name = title
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int = 0

    @Column(nullable = false, length = 60)
    open var name: String = ""

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    open var developer: Developer? = null

    @ManyToMany(mappedBy = "tags",fetch = FetchType.EAGER,cascade = [CascadeType.ALL])
    open val tags : MutableSet<Tag> = HashSet()

    fun addDeveloper(developer : Developer){
        if (this.developer == null){
            this.developer = developer
            developer.stories.add(this)
        }
    }
    fun addtags(tag : Tag){
        if (!this.tags.contains(tag)){
            this.tags.add(tag)
        }
    }
}