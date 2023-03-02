package edu.spring.stories.repositories

import edu.spring.stories.entities.Tag
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.awt.Color

@Repository
interface TagRepository : CrudRepository<Tag, Long> {
    fun findByColor(color: String): List<Tag>
}