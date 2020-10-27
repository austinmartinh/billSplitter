package model

import java.time.LocalDateTime

data class Person (var name:String, var isUser: Boolean){

    val id = LocalDateTime.now().hashCode()
    // List of bills which feature this person
    var associatedBillIds = emptyList<Int>()


    //Take billId, add Person to bill.personlist
    fun associateBill(billId:Int){}

    // Take billId, call bill.unassociatePerson(this.personId)
    fun unassociateBill(billId:Int){}

    //Go to bill list, check each for personID, add all unpaid items to item list, return list
    fun findUnpaidItems() : List<Item>{
        var items = emptyList<Item>()
        return items
    }
}