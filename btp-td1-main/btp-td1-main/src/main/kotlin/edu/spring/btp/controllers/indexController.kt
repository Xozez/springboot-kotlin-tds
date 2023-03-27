package edu.spring.btp.controllers

import edu.spring.btp.entities.*
import edu.spring.btp.repositories.*
import edu.spring.btp.security.DbUserService
import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
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
    lateinit var dbUserService: DbUserService

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

    @GetMapping("/signup")
    fun signup(model: ModelMap, auth: Authentication?): String {
        model["username"] = auth?.name
        return "Signup"
    }

    @PostMapping("/signup")
    fun signup(
        @RequestParam("username") newUsername: String,
        @RequestParam("email") newEmail: String,
        @RequestParam("password") newPassword: String,
        @RequestParam("confirmPassword") confirmPassword: String
    ): RedirectView {
        if (newPassword != confirmPassword) {
            return RedirectView("/signup?error=Passwords doesn't match")
        }

        val newUser = User()
        newUser.username = newUsername
        newUser.email = newEmail
        newUser.password = newPassword
        newUser.role = "USER"
        (dbUserService).encodePassword(newUser)
        userRepository.save(newUser)

        return RedirectView("/")
    }
    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): String {
        val errorCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int
        return if (errorCode == 403) {
            "main/Erreur403"
        } else if (errorCode == 404) {
            "main/Erreur404"
        } else if (errorCode == 500) {
            "main/Erreur500"
        }
        else {
            "main/Erreur"
        }
    }
}