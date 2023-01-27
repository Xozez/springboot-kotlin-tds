package edu.spring.TD1.models

class Item(var nom: String?) {
    var evaluation: Int = 0;

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        return if (o !is Item) false else nom == o.nom
    }

    override fun hashCode(): Int {
        return nom.hashCode()
    }
}