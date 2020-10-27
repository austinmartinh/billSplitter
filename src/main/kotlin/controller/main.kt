package controller
import model.*
fun main(args:Array<String>){
    var people = emptyList<Person>()

    val list = listOf(20,30,340,59,56)

    val testIds = listOf(1,2,3,4,5,6,30,20)

    print(list.filter{num -> num in testIds})
}

