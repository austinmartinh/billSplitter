package controller

import kotlinx.serialization.Serializable
import model.Bill
import model.Item

//import tornadofx.*
class BillManager (var bills :MutableList<Bill>){

    fun newBill(title:String,ids:MutableList<Int>){
        bills.add(Bill(title,ids))
    }

    fun getBillById(id:Int): Bill? {
        return (bills.find { bill -> bill.id == id })
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