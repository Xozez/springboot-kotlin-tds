package edu.spring.btp.controllers

import edu.spring.btp.entities.Complaint
import edu.spring.btp.repositories.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

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

    @RequestMapping(path = ["/", "/index", ""])
    fun index(model: ModelMap): String {
        model["domain-name"] = domainRepository.findByParentIsNull()[0].name
        model["domain-children-size"] = domainRepository.findByParentIsNull()[0].children.size
        model["domain-children"] = domainRepository.findByParentIsNull()[0].children
        return "index"
    }

    @GetMapping("/domain/{name}")
    fun domainName(@PathVariable name: String, model: ModelMap): String {
        val domain = domainRepository.findByName(name)
        model["domain-name"] = domain.name
        model["domain-children-size"] = domain.children.size
        model["domain-children"] = domain.children
        return "domainIndex"
    }

    @GetMapping("/complaints/{name}")
    fun complaints(model: ModelMap, auth: Authentication, @PathVariable(name = "name") name: String): String {
        model["username"] = auth.name
        model["domainName"] = name
        model["complaints"] = domainRepository.findByName(name).complaints
        model["complaintsAmount"] = domainRepository.findByName(name).complaints.size
        return "Complaints"
    }

    @GetMapping("/complaints/{name}/new")
    fun newComplaint(model: ModelMap, auth: Authentication, @PathVariable(name = "name") name: String): String {
        model["username"] = auth.name
        model["domainName"] = name
        return "NewComplaint"
    }

    @PostMapping("/complaints/{name}/new")
    fun newComplaint(
        auth: Authentication,
        @PathVariable(name = "name") name: String,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String
    ): RedirectView {
        val user = userRepository.findByUsernameOrEmail(auth.name)
        val domain = domainRepository.findByName(name)
        val complaint = Complaint()
        complaint.title = title
        complaint.description = description
        complaint.domain = domain
        complaint.provider = domain.providers[0]
        if (user != null) {
            complaint.user = user
            complaint.claimants.add(user)
        }
        complaintRepository.save(complaint)

        return RedirectView("/complaints/$name")
    }
}