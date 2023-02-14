package Etu.spring.TP2.services

import Etu.spring.TP2.entities.*
import org.springframework.stereotype.Service


@Service
class OrgaService {

    fun addUsersFromString(users : String, organisation : Organization){
        if (users.isNotEmpty()){
            users.split("\n").forEach(){
                var user = User()
                val (firstName,lastName) = it.trim().split("", limit = 2)
                user.lastName = lastName
                user.firstName = firstName
                user.email = "${user.firstName}.${user.lastName}@${organisation.domain}"
                organisation.addUser(user)
            }
        }
    }
}