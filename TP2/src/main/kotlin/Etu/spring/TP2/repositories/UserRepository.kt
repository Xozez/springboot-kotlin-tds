package Etu.spring.TP2.repositories

import Etu.spring.TP2.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {

}