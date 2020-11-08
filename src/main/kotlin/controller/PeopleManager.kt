package controller

import kotlinx.serialization.Serializable
import model.Person
@Serializable
class PeopleManager (var people:MutableList<Person>){
    var groups = mutableMapOf<String,MutableList<Int>>()

    //Create new person and add to list
    fun newPerson(pair:Pair<String,Boolean>):Int?{
        var newPerson =Person(pair.first,pair.second)
        return if (people.find { person -> person.name == pair.first  } == null ) {
            people.add(newPerson)
            newPerson.id
        } else
            null
    }

    fun deletePerson(index:Int) : Boolean{
        if(people.removeAt(index) != null){
            return true
        }
        return false
    }

    fun updatePerson(index:Int,name:String,isUser:Boolean):Person {
        var newPerson = people[index]
        newPerson.name=name
        if(newPerson.isUser != isUser){
            newPerson.isUser = isUser
            clearIsUser()
        }
        newPerson.isUser=isUser
        people[index] = newPerson
        return newPerson
    }

    //Get person by ID - returns person
    fun getPersonById(id:Int) :Person? {
        return (people.find { person -> person.id == id })
    }

    //Get person by name
    fun getPersonByName(name:String) : Person? {
        return (people.find { person -> person.name == name })
    }

    //get user with isUser==true
    fun getCurrentUser() : Person? {
        return(people.find { person -> person.isUser == true})
    }

    //Get People by Id - returns list of people from list of ids
    fun getNamesById(ids:MutableList<Int>) : MutableList<String> {
        var matches :MutableList<Person>
        var names =mutableListOf<String>()
        matches = (people.filter{ person -> person.id in ids }).toMutableList()
        for(x in matches){
            names.add(x.name)
        }
        return names
    }

    fun getAllPeople() : MutableList<Person>{
        return people
    }

    fun createOrUpdateGroup(name:String,memberIds: MutableList<Int>){
        groups[name] = memberIds
    }

    fun removeGroup(name:String){
        if(groups.containsKey(name)) {
            groups.remove(name)
        }
    }



    fun clearIsUser(){
        for (x in people){
            x.isUser = false
        }
    }

    fun save(){

    }

    fun load(){

    }


}