package Etu.spring.TP2.controller

import Etu.spring.TP2.entities.Organization
import Etu.spring.TP2.entities.User
import Etu.spring.TP2.exceptions.ElementNotFoundException
import Etu.spring.TP2.repositories.OrganizationRepository
import Etu.spring.TP2.services.OrgaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/orgas/")
class OrgaController {

    @Autowired
    lateinit var orgaRepository: OrganizationRepository
    @Autowired
    lateinit var orgaService: OrgaService

    @RequestMapping(path=["","index"])
    fun orgaIndexAction(model : ModelMap) : String {
        val orgas = orgaRepository.findAll()
        model.addAttribute("orgas", orgas)
        return "orgas/orgaIndex"
    }

    @GetMapping("new")
    fun addNewAction(model : ModelMap) : String {
        model["orga"] = Organization("NewOrganisation")
        return "orgas/formOrga"
    }

    @PostMapping("new")
    fun submittedAddNewAction(@ModelAttribute orga: Organization,@ModelAttribute("users") users : String) : RedirectView {
        orgaService.addUsersFromString(users, orga)
        orgaRepository.save(orga)
        return RedirectView("/orgas/")
    }

    @GetMapping("add/{nom}")
    @ResponseBody
    fun testAddAction(@PathVariable("nom") nom: String): String {
        var orga = Organization(nom)
        var user = User()
        user.firstName = "test"
        user.email = "user@gmail.com"
        user.lastName = "test"
        orga.users.add(user)
        orgaRepository.save(orga)
        return "L'organisation $nom a été ajoutée avec l'utilisateur ${user.email}"

        @GetMapping("/display/{id}")
        fun displayAction(@PathVariable("id") id: Int, model: ModelMap): String {
            val orga = orgaRepository.findById(id)
            if (orga.isPresent) {
                model["orga"] = orga.get()
                return "orgas/displayOrga"
            }
            throw ElementNotFoundException("Organisation d'identifiant $id non trouvée")
        }

        @ExceptionHandler(value = [ElementNotFoundException::class])
        @ResponseBody
        fun exceptionHandler(ex : RuntimeException, model : ModelMap): ModelAndView {
            val mv = ModelAndView("/main/error")
            model["error"]={ex.message}
            return mv
        }
    }
}