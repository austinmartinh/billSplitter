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

    fun total() : Float {
       var sum =0F
        for(x in items){
            sum += x.value
        }
        return sum
    }

    fun subtotals( names:MutableList<String>) : MutableMap<String,Float> {
        var subtotals = mutableMapOf<String,Float>()
        for(x in names) {
            subtotals[x] = 0F
            for (y in items) {
                //ensures y.splits is not null
                if (y.splits.containsKey(x)){
                    subtotals[x]!! + y.splits[x]!!
                }
            }
        }
        return subtotals
    }

    fun recalc(){

    }

}