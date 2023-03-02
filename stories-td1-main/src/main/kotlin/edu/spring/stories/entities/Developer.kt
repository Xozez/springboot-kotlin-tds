package edu.spring.stories.entities

import jakarta.persistence.*

@Entity
class Developer() {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Int = 0

    @Column(nullable = false, length = 60)
    open var lastName: String = ""

    @Column(nullable = false,length = 45)
    open var firstName: String = ""


    @OneToMany(mappedBy = "story",fetch = FetchType.EAGER,cascade = [CascadeType.ALL])
    open val stories : MutableSet<Story> = HashSet()

    constructor (lastname: String , firstname: String):this(){
        this.firstName = firstname
        this.lastName = lastname
    }

    fun addStory(story : Story){
        if (!this.stories.contains(story)){
            this.stories.add(story)
            story.developer = this
        }
    }
    fun giveUpStory(story : Story){
        if (this.stories.contains(story)){
            this.stories.remove(story)
            story.developer = null
        }
    }
    @PreRemove
    fun preRemove(){
        for (story in stories){
            story.developer = null
        }
        this.stories.clear()
    }
}