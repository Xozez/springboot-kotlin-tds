package edu.spring.btp.controllers

import edu.spring.btp.entities.Domain
import edu.spring.btp.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
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

    @RequestMapping(path = ["/", "/index",""])
    fun index(model : ModelMap): String {
        model["domain-name"] = domainRepository.findByParentIsNull()[0].name
        model["domain-children-size"]= domainRepository.findByParentIsNull()[0].children.size
        model["domain-children"]= domainRepository.findByParentIsNull()[0].children
        for (domain in model["domain-children"] as List<Domain>){
            model["providers-size"]= domain.providers.size
            model["complaint-count"] = domain.getComplainsCount()
            model["complaint-size"] = domain.complaints.size
        }
        return "index"
    }
}