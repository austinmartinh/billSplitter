package controller

import model.Person

data class PeopleManager (var people:MutableList<Person>){

    //Create new person and add to list
    fun newPerson(pair:Pair<String,Boolean>):Int?{
        var newPerson =Person(pair.first,pair.second)
        return if (people.find { person -> person.name == pair.first  } == null ) {
            people.add(newPerson)
            newPerson.id
        } else
            null
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
    fun getPeopleById(ids:List<Int>){
        var matches :MutableList<Person>
        (people.filter{ person -> person.id in ids })
        //TODO finish if required
    }

    fun getAllPeople() : MutableList<Person>{
//        println("Listing saved people")
//        println("--------------------")
//        for (x in people){
//            var name=x.name
//            var num = people.indexOf(x) +1
//            println("$num. $name")
//        }
        return people
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