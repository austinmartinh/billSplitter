package model

import java.time.LocalDateTime

data class Item (var name:String, var value:Float) {

    val id = LocalDateTime.now().hashCode()
    var billId = -1
    var paid = false
    var dateTime = LocalDateTime.now()
    var splits = emptyMap<String, Float>()

    constructor(id:Int, name: String, value: Float, billId: Int) : this(name, value) {
        this.billId = billId
    }

    //addPersonwWithRatio(int id, float split)

    //addPersonWithValue(int id, float value)

    //removePerson() - 1. If personId on item, calc their share and split among other shares
    //                 2. Remove that person from map

}