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

    private fun getItemByName(items : MutableSet<Item>, nom : String) : Item? = items.find { it.nom == nom }

    private fun addMessage(response : Boolean, attrs : RedirectAttributes,title : String, error : String, success : String){
        if(response){
            attrs.addFlashAttribute("msg",UIMessage.message(title,success))
        } else {
            attrs.addFlashAttribute("msg",UIMessage.message(title,error,"error","warning circle"))
        }
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
        addMessage(items.add(item), attrs,"Ajout","${item.nom} est déjà dans les items","${item.nom} a été ajouté à la liste")
        return RedirectView("/")
    }

    @GetMapping("/inc/{nom}")
    fun inc(@SessionAttribute("items") items: MutableSet<Item>, @PathVariable("nom") nom: String, attrs: RedirectAttributes): RedirectView {
        var it = getItemByName(items,nom)
        it?.increment()
        addMessage(it != null, attrs,"Mise à jour","$nom n'est pas dans la liste","$nom a été incrémenté")
        return RedirectView("/")
    }

    @GetMapping("/dec/{nom}")
    fun dec(@SessionAttribute("items") items: MutableSet<Item>, @PathVariable("nom") nom: String, attrs: RedirectAttributes): RedirectView {
        var it = getItemByName(items,nom)
        it?.decrement()
        addMessage(it != null, attrs,"Mise à jour","$nom n'est pas dans la liste","$nom a été décrémenté")
        return RedirectView("/")
    }
    @GetMapping("/delete/{nom}")
    fun delete(@SessionAttribute("items") items: MutableSet<Item>, @PathVariable("nom") nom: String, attrs: RedirectAttributes): RedirectView {
        var it = getItemByName(items,nom)
        if(it != null){
            items.remove(it)
        } 
        addMessage(it != null, attrs,"Suppression","$nom n'est pas dans la liste","$nom a été supprimé")
        return RedirectView("/")
    }
}