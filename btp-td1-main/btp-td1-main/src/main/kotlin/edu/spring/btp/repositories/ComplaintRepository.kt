package edu.spring.btp.repositories

import org.springframework.data.jpa.repository.JpaRepository

interface ComplaintRepository:JpaRepository<edu.spring.btp.entities.Complaint, Int> {
}