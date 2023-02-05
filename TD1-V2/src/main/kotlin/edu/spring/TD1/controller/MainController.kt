package edu.spring.TD1.controller

import edu.spring.TD1.models.Categorie
import edu.spring.TD1.models.Item
import edu.spring.TD1.services.UIMessage
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@Controller
@SessionAttributes("categories")
class MainController {

    @get:ModelAttribute("categories")
    val categories: HashSet<Categorie>
        get() {
            val categories = HashSet<Categorie>()
            categories.add(Categorie("Amis"))
            categories.add(Categorie("Famille"))
            categories.add(Categorie("Professionnel"))
            categories.elementAt(0).addItem(Item("Jean"))
            categories.elementAt(0).addItem(Item("AA"))
            categories.elementAt(0).addItem(Item("Jeanne"))
            return categories
        }

    private fun getItemByName(items : MutableSet<Item>?, nom : String) : Item? = items?.find { it.nom == nom }
    private fun getCategorieByName(categories : MutableSet<Categorie>, nom : String) : Categorie? = categories.find { it.libelle == nom }

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

    @GetMapping("/new/{categorie}")
    fun newAction(@PathVariable("categorie") categorie : String): String {
        return "newFormulaire"
    }

    @PostMapping("/addNew")
    fun addNew(@SessionAttribute("categories") categories: MutableSet<Categorie>, @ModelAttribute item:Item, @ModelAttribute categorie: String,attrs: RedirectAttributes): RedirectView {
        val it = getCategorieByName(categories,categorie)
        it?.addItem(item)
            ?.let { addMessage(it, attrs,"Ajout","${item.nom} est déjà dans les items","${item.nom} a été ajouté à la liste") }
        return RedirectView("/")
    }

    @GetMapping("/inc/{categorie}/{nom}")
    fun inc(@SessionAttribute("categories") categories: MutableSet<Categorie>, @PathVariable("nom") nom: String, @PathVariable("categorie") categorie: String,attrs: RedirectAttributes): RedirectView {
        val it = getCategorieByName(categories,categorie)
        getItemByName(it?.getItems(),nom)?.increment()
        addMessage(it != null && getItemByName(it?.getItems(),nom) != null, attrs,"Mise à jour","$nom n'est pas dans la liste","$nom a été incrémenté")
        return RedirectView("/")
    }

    @GetMapping("/dec/{categorie}/{nom}")
    fun dec(@SessionAttribute("categories") categories: MutableSet<Categorie>, @PathVariable("nom") nom: String, @PathVariable("categorie") categorie : String,attrs: RedirectAttributes): RedirectView {
        val it = getCategorieByName(categories,categorie)
        getItemByName(it?.getItems(),nom)?.decrement()
        addMessage(it != null && getItemByName(it?.getItems(),nom) != null, attrs,"Mise à jour","$nom n'est pas dans la liste","$nom a été décrémenté")
        return RedirectView("/")
    }
    @GetMapping("/delete/{categorie}/{nom}")
    fun delete(@SessionAttribute("categories") categories: MutableSet<Categorie>,@PathVariable("categorie") categorie : String ,@PathVariable("nom") nom: String, attrs: RedirectAttributes): RedirectView {
        val it = getCategorieByName(categories,categorie)
        if(getItemByName(it?.getItems(),nom) != null){
            it?.removeItem(getItemByName(it.getItems(),nom)!!)
        }
        addMessage(it != null && getItemByName(it?.getItems(),nom) != null, attrs,"Suppression","$nom n'est pas dans la liste","$nom a été supprimé")
        return RedirectView("/")
    }
}