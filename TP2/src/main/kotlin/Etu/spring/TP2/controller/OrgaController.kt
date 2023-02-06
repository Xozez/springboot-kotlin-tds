package Etu.spring.TP2.controller

import Etu.spring.TP2.entities.Organization
import Etu.spring.TP2.repositories.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/orgas/")
class OrgaController {

    @Autowired
    lateinit var orgaRepository: OrganizationRepository

    @RequestMapping(path=["","index"])
    fun orgaIndexAction(model : ModelMap) : String {
        val orgas = orgaRepository.findAll()
        model.addAttribute("orgas", orgas)
        return "orgas/orgaIndex"
    }

    @GetMapping("add/{nom}")
    @ResponseBody
    fun testAddAction(@PathVariable("nom") nom: String): String {
        var orga = Organization()
        orga.name = nom
        orgaRepository.save(orga)
        return "L'organisation $nom a été ajoutée"
    }
}