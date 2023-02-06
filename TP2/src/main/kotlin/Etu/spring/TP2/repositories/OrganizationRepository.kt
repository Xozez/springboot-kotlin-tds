package Etu.spring.TP2.repositories

import Etu.spring.TP2.entities.Organization
import org.springframework.data.repository.CrudRepository

interface OrganizationRepository : CrudRepository<Organization, Int> {
}