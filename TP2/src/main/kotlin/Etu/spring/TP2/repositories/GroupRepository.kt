package Etu.spring.TP2.repositories

import Etu.spring.TP2.entities.Group
import org.springframework.data.repository.CrudRepository

interface GroupRepository : CrudRepository<Group, Int> {
}