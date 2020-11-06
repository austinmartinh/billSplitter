package controller

import model.Bill
import model.Item

//import tornadofx.*

class BillManager (var bills :MutableList<Bill>){

    fun newBill(title:String,ids:MutableList<Int>){
        bills.add(Bill(title,ids))
    }

    fun updateBillDetails(index:Int, title:String){
        var updatedBill = bills[index]
        updatedBill.title = title
    }

    fun updateAssociatedPeople(index:Int, ids : MutableList<Int>, addMode:Boolean){
        var billToUpdate = bills[index]
        billToUpdate.updatePeople(ids,addMode)
    }

    fun save(){

    }

    fun load(){

    }
}