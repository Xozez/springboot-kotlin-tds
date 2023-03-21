package edu.spring.btp.controllers

import edu.spring.btp.entities.Domain
import edu.spring.btp.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/")
@Controller
class IndexController {

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
        return "index"
    }

    @GetMapping("/domain/{name}")
    fun domainName(@PathVariable name: String, model: ModelMap): String {
        val domain = domainRepository.findByName(name)
        model["domain-name"] = domain.name
        model["domain-children-size"]= domain.children.size
        model["domain-children"]= domain.children
        return "domainIndex"
    }
}