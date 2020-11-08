package controller
import helpers.JsonManager
import model.*


    fun main(args: Array<String>) {
        var people = mutableListOf<Person>()
        var bills = mutableListOf<Bill>()
        var p = PeopleManager(people)
        var b = BillManager(bills)
        var j = JsonManager()
        var menu  = MenuHandler(p,b,j)
    }
