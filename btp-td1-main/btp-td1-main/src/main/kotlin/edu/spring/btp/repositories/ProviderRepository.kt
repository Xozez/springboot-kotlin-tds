package edu.spring.btp.repositories

import edu.spring.btp.entities.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProviderRepository:JpaRepository<Provider, Int> {
    @Query(nativeQuery = true,value="SELECT * FROM \"provider\" ORDER BY rand() LIMIT 1")
    fun getRandomProvider(): Provider
}