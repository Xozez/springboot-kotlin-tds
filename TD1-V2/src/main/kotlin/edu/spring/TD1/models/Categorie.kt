package edu.spring.TD1.models

class Categorie(var libelle : String?) {
    var  items  = HashSet<Item>()

    fun addItem(item : Item) : Boolean{
        return if (!items.contains(item)) items.add(item) else false
    }
    fun removeItem(item : Item) : Boolean{
        return if (items.contains(item)) items.remove(item) else false
    }
    fun getItems() : MutableSet<Item> {
        return items.toMutableSet()
    }
}