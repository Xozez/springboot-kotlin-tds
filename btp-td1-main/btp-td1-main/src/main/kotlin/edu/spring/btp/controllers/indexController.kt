package edu.spring.btp.controllers

import edu.spring.btp.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/")
@Controller
class indexController {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var domainRepository: DomainRepository

    @Autowired
    lateinit var complaintRepository: ComplaintRepository

    @Autowired
    lateinit var providerRepository: ProviderRepository

    
}