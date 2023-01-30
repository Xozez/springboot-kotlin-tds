package edu.spring.TD1

import edu.spring.TD1.models.Item
import edu.spring.TD1.services.UIMessage
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@Controller
@SessionAttributes("items")
class MainController {

    @get:ModelAttribute("items")
    val items: Set<Item>
        get() {
            val items = HashSet<Item>()
            items.add(Item("Foo"));
            return items
        }

    private fun getItemByName(items : MutableSet<Item>, nom : String) : Item? {
        return items.find { it.nom == nom }
    }

    @RequestMapping("/")
    fun indexAction( @RequestAttribute msg: UIMessage.Message?): String {
        return "index"
    }

    @GetMapping("/new")
    fun newAction(): String {
        return "newFormulaire"
    }

    @PostMapping("/addNew")
    fun addNew(@SessionAttribute("items") items: MutableSet<Item?>, @ModelAttribute item:Item, attrs: RedirectAttributes): RedirectView {
        if(items.add(item)){
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout","${item.nom} a été ajouté à la liste"))
        } else {
            attrs.addFlashAttribute("msg", UIMessage.message("Ajout","${item.nom} est déjà dans les items","error","warning circle"))
        }
        return RedirectView("/")
    }

    @GetMapping("/inc/{nom}")
    fun inc(@SessionAttribute("items") items: MutableSet<Item>, @PathVariable("nom") nom: String, attrs: RedirectAttributes): RedirectView {
        var it = getItemByName(items,nom)
        if(it != null){
            it.increment()
            attrs.addFlashAttribute("msg", UIMessage.message("Incrémentation","$nom a été incrémenté"))
        } else {
            attrs.addFlashAttribute("msg", UIMessage.message("Incrémentation","$nom n'est pas dans la liste","error","warning circle"))
        }
        return RedirectView("/")
    }

    @GetMapping("/dec/{nom}")
    fun dec(@SessionAttribute("items") items: MutableSet<Item>, @PathVariable("nom") nom: String, attrs: RedirectAttributes): RedirectView {
        var it = getItemByName(items,nom)
        if(it != null){
                it.decrement()
                attrs.addFlashAttribute("msg", UIMessage.message("Décrémentation","$nom a été décrémenté"))
        } else {
            attrs.addFlashAttribute("msg",UIMessage.message("Décrémentation", "$nom n'est pas dans la liste", "error", "warning circle"))
        }
        return RedirectView("/")
    }
    @GetMapping("/delete/{nom}")
    fun delete(@SessionAttribute("items") items: MutableSet<Item>, @PathVariable("nom") nom: String, attrs: RedirectAttributes): RedirectView {
        var it = getItemByName(items,nom)
        if(it != null){
                items.remove(it)
                attrs.addFlashAttribute("msg", UIMessage.message("Suppression","$nom a été supprimé"))
        } else{
            attrs.addFlashAttribute("msg", UIMessage.message("Suppression","$nom n'est pas dans la liste","error","warning circle"))
        }
        return RedirectView("/")
    }
}