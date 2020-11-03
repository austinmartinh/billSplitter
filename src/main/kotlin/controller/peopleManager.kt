package controller

import model.Person

data class peopleManager (var people:MutableList<Person>){

    //Create new person and add to list
    fun newPerson(name:String):Int?{
        var newPerson =Person(name,false)
        return if (people.find { person -> person.name != name  } !=null ) {
            people.add(newPerson)
            newPerson.id
        } else
            null
    }

    //Get person by ID - returns person
    fun getPersonById(id:Int) :Person? {
        return (people.find { person -> person.id == id })
    }

    //Get People by Id - returns list
    fun getPeopleById(ids:List<Int>){
        var matches :MutableList<Person>
        people.filter{ person -> person.id in ids }

    }

}