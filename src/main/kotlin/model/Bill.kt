package model

import java.time.LocalDateTime

data class Bill (var title:String){

    constructor(title:String, associatedPersonIds:MutableList<Int>) :this(title)
    val id = LocalDateTime.now().hashCode()
    var items = mutableListOf<Item>()

    var associatedPersonIds = mutableListOf<Int>()

    fun addItem(item:Item){}

    fun removeItem(/*id?*/){}
    // Get an Item...updates will be called on it directly
    // Add a person
    // Remove a person - sends update to all items to remove and resplit
    // Set split ratio for all items

    //Takes a list of person Ids and a mode. If add, adds them if not in list
    //Else, removes ids if present
    fun updatePeople(ids:MutableList<Int>, addMode:Boolean) {
        //TODO Recalc methods may be required here
        if(addMode) {
            for (x in ids) {
                if (!associatedPersonIds.contains(x)) {
                    associatedPersonIds.add(x)
                }
            }
            return
        }
        for(x in ids){
            if(associatedPersonIds.contains(x)){
                associatedPersonIds.remove(x)
            }
        }
    }
}