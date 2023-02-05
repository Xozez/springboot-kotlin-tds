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

    @GetMapping("/newCategorie")
    fun newCategorieAction(): String {
        return "newCategorie"
    }

    @GetMapping("/new/{categorie}")
    fun newAction(@PathVariable("categorie") categorie : String): String {
        return "newFormulaire"
    }

    @PostMapping("/addNew")
    fun addNew(@SessionAttribute("categories") categories: MutableSet<Categorie>, @ModelAttribute item:Item, @ModelAttribute("categorie") categorie: String,attrs: RedirectAttributes): RedirectView {
        val it = getCategorieByName(categories,categorie)
        it?.addItem(item)
            ?.let { addMessage(it, attrs,"Ajout","${item.nom} est déjà dans les items","${item.nom} a été ajouté à la liste") }
        return RedirectView("/")
    }

    @PostMapping("/addNewCategorie")
    fun addNewCategorie(@SessionAttribute("categories") categories: MutableSet<Categorie>, @ModelAttribute("libelle") libelle: String,attrs: RedirectAttributes): RedirectView {
        val it = Categorie(libelle)
        addMessage(categories.add(it), attrs,"Ajout","${it.libelle} est déjà dans les catégories","${it.libelle} a été ajouté à la liste des categories")
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
            addMessage(true, attrs,"Suppression","$nom n'est pas dans la liste","$nom a été supprimé")
            return RedirectView("/")
        }
        addMessage(it != null && getItemByName(it?.getItems(),nom) != null, attrs,"Suppression","$nom n'est pas dans la liste","$nom a été supprimé")
        return RedirectView("/")
    }
}