package model

import java.time.LocalDateTime

data class Bill (var creatorId:Int){
    val id = LocalDateTime.now().hashCode()
    var items = emptyList<Item>()

    var associatedPersonIds = emptyList<Int>()

    fun addItem(item:Item){}

    fun removeItem(/*id?*/){}
    // Get an Item...updates will be called on it directly
    // Add a person
    // Remove a person - sends update to all items to remove and resplit
    // Set split ratio for all items
}