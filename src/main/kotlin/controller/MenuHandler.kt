package controller

import model.Bill
import model.Person
import kotlin.system.exitProcess

class MenuHandler(var p:PeopleManager,var b: BillManager) {



    init{
        mainMenu()
    }

    fun mainMenu() {
        var input: Int? = -1
        while (input != 0) {
            println("Welcome to BillSplitter 1.0 - Console Version\n-----------------")
            println("1. Manage Bills")
            println("2. Manage People")
            println("3. Load from Memory")
            println("4. Save to Memory")
            println("\n0. Exit")
            println("------------------")
            //TODO Validate input
            input = getNumericInput()
            when(input) {
                1 -> billMenu()
                2 -> peopleMenu()
                3 -> load()
                4 -> save()
                5 -> exit()
            }
        }
    }

    fun billMenu() {
        var bInput: Int? = -1
        while (bInput != 0) {
            println("Bill Management\n-----------------")
            println("1. Search for Bills")    // searchBills()
            println("2. Create New Bill")   //createNewBill()
            println("3. Delete Bill")   //listAllBillsForDelete()
            println("4. Update Bill")   //listAllBills

            println("\n0. Exit")
            println("------------------")

            //TODO Validate input
            bInput = getNumericInput()
            when(bInput){
                1 -> listBills(b.bills)
                2 -> handleCreateBill()
                3 -> handleUpdateBill()
                4 -> handleDeleteBill()
            }
            println("binput is $bInput")
        }
        println("Bye!")
    }

    fun listBills(bills : MutableList<Bill>) : Int {
        println("Listing all bills")
        println("--------------------")
        for (x in bills){
            var name = x.title
            var num = bills.indexOf(x)+1
            println("$num. $name")
        }
        println("--------------------")
        return bills.size
    }

    fun handleCreateBill(){
        var newBillDetails = readBillDetails()
        b.newBill(newBillDetails.first,newBillDetails.second)
    }

    fun handleUpdateBill(){
        println("Select bill to update")
        var selected = selectBillIndexFromList(b.bills)
        var billToUpdate = b.bills[selected]
        var details = readBillDetails()

        for(x in billToUpdate.associatedPersonIds)
        if(!details.second.contains(x)){
            details.second.add((x))
        }

        billToUpdate.title = details.first
        billToUpdate.associatedPersonIds = details.second

        b.bills.set(selected,billToUpdate)
    }

    fun handleDeleteBill(){
        println("Select bill to delete")
        var selected = selectBillIndexFromList(b.bills)
        b.bills.removeAt(selected)
    }

    fun readBillDetails(): Pair<String,MutableList<Int>> {
        var title : String = ""
        var assocIds = mutableListOf<Int>()

        println("Taking bill details")
        println("---------------------")

        while (title == "") {
            println("Enter a title for the bill:")
            print("---> ")
            title = readLine().toString()
        }
        println("Would you like to associate people with the bill now? (y/n)")
        print("---> ")
        if (readLine().toString() == "y")
        {
            var continueAdding = true

            println("Would you like to use a saved group?")
            println("--> ")
            if(readLine().toString() =="y")
            {
                //printgroups()
                //add groupToBill()
                    //sets groupId
                    //sets
                continueAdding=false
            }
            while(continueAdding){
            println("Select a user to associate with the bill")
                var people = p.getAllPeople()
                var pIndex = (selectUserIndexFromList(people))
                assocIds.add(pIndex)
                println("Would you like to add more users? (y/n)")
                print("--> ")
                if(readLine().toString()!="y")
                    continueAdding = false
            }
        }
        return Pair(first = title,second = assocIds)
    }

    fun printBills(bills:MutableList<Bill>) : Int{
        println("Listing bills")
        println("--------------------")
        for (x in bills){
            var name = x.title
            var num = b.bills.indexOf(x) + 1
            println("$num. $name")
        }
        println("--------------------")
        return p.people.size
    }

    /********** PERSON MENU AND FUNCTIONS **********/

    fun peopleMenu(){
        var pInput: Int? = -1
        while (pInput != 0) {
            println("People Management\n-----------------")
            println("1. Search People")    //listAllBills()
            println("2. Create Person")   //createNewBill()
            println("3. Update Person")   //listAllBills
            println("4. Delete Person")   //listAllBillsForDelete()
            println("5. Create Group")
            println("6. Delete Group")
            println("7. Update Group")

            println("\n0. Exit")
            println("------------------")

            //TODO Validate input
            pInput = getNumericInput()
            when (pInput){
                1 -> printPeople(p.getAllPeople())
                2 -> p.newPerson(readPersonalDetails())
                3 -> handleUpdatePerson()
                4 -> handleDeletePerson()
                5 -> handleCreateGroup()
                6 -> handleDeleteGroup()
                0 -> break
            }
        }
        println("Bye!")
    }

    fun printPeople(people:MutableList<Person>) : Int{
        println("Listing people")
        println("--------------------")
        for (x in people){
            var name=x.name
            var num = p.getAllPeople().indexOf(x) +1
            println("$num. $name")
        }
        println("--------------------")
        return p.people.size
    }

    fun readPersonalDetails(): Pair<String,Boolean> {
        println("Taking personal details")
        println("---------------------")

        var name : String = ""
        var isUser : Boolean = false

        while (name == "") {
            println("Enter the persons name:")
            print("---> ")
            name = readLine().toString()
        }

        println("Is this person you? (y/n)")
        print("---> ")
        if(readLine() == "y") {
            isUser = true
        }
        print("user value: $isUser")
        return Pair(first = name,second = isUser)
    }

    fun handleUpdatePerson(){
        println("Who would you like to update details for?")
        val selected = selectUserIndexFromList(p.getAllPeople())
        var updateDetails = readPersonalDetails()
        p.updatePerson(selected, updateDetails.first,updateDetails.second)
    }

    fun handleDeletePerson(){
        println("Who would you like to remove?")
        val selected = selectUserIndexFromList(p.getAllPeople())
        println("\nDeleting Person from list")
        println("----------------------------")
        p.deletePerson(selected)
    }

    fun handleCreateGroup() {
        var groupName = readGroupName()
        println("----------------------------")
        println("Select people to add to group")
        println("----------------------------")
        var members = readToAddToGroup()
        p.createOrUpdateGroup(groupName,members)
    }
    fun handleDeleteGroup(){
        listGroups(p.groups)
        var groupName = readGroupName()
        p.removeGroup(groupName)
    }

    fun listGroups(g : MutableMap<String, MutableList<Int>>) : Int{
        println("Listing group names")
        println("--------------------")
        var i = 1
        for (x in g){
            var name=x.key
            println("$i. $name")
            i++
        }
        println("--------------------")
        return g.size
    }

    private fun readGroupName(): String {
        var groupName = ""
        while (groupName == "") {
            println("Enter the name of the group")
            print("---> ")
            groupName = readLine().toString()
        }
        return groupName
    }

    private fun readToAddToGroup() :MutableList<Int> {
        var members = mutableListOf<Int>()
        var finished = false
        while (!finished) {
            val people = p.getAllPeople()
            var id = people[selectUserIndexFromList(people)].id
            if (!members.contains(id)) {
                members.add(id)
            }
            println("Would you like to add more people? (y/n)")
            print("---> ")
            if (readLine() != "y") {
                finished = true
            }
        }
        return members
    }


    /************** HELPER FUNCTIONS **********************/

    /**
     * Takes input until a number is given
     */
    fun getNumericInput() : Int?{
        var input : Int? = null
        while(input == null || input <0) {
            print("Please enter a numeric index\n--->  ")
            input = readLine()?.toIntOrNull()
        }
        return input
    }

    /**
     * Take a list of people, prints them sequentially and takes user input to represent selecting that item.
     */
    fun selectUserIndexFromList(people: MutableList<Person>): Int{
        val upperBound = printPeople(people)
        var selected = getNumericInput()!! -1
        if (selected<0){
            println("Selecting first entry")
            selected = 0
        }
        if(selected >= upperBound)
        {
            println("Selecting last entry")
            selected = upperBound-1
        }
        return selected
    }

    fun selectBillIndexFromList(bills: MutableList<Bill>): Int{
        val upperBound = printBills(bills)
        var selected = getNumericInput()!! -1
        if (selected<0){
            println("Selecting first entry")
            selected = 0
        }
        if(selected >= upperBound)
        {
            println("Selecting last entry")
            selected = upperBound-1
        }
        return selected
    }

    fun save(){
        //TODO Check order
        p.save()
        b.save()
    }

    fun load(){
        p.load()
        b.load()
    }

    fun exit(){
        println("Bye!")
        exitProcess(0)
    }


//Manage Bills
//1. Create a new Bill
//2. Update Bills
//3. Delete a bill

//Manage People

//1. People
//1. View all people
//List all people and an index, type index to select
//1. View all bills for person
//2. View Groups
//2. Create a new Person
//3. Delete a person

//2. Groups
//1. View all Groups
//1. Add person to this group
//2. Remove person from this group - calls recalc method
//2. Create Group
//3. Delete Group

//
//    fun setMode(mode: Int) {
//       this.operationMode = mode
//    }
}