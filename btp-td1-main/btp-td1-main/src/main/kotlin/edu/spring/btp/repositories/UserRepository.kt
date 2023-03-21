package edu.spring.btp.repositories

import edu.spring.btp.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Int> {
    @Query(nativeQuery = true,value="SELECT * FROM \"user\" ORDER BY rand() LIMIT 1")
    fun getRandomUser(): User

    @Query("SELECT u FROM User u WHERE u.username=:usernameOrEmail OR u.email=:usernameOrEmail")
    fun findByUsernameOrEmail(usernameOrEmail: String?): User?
}