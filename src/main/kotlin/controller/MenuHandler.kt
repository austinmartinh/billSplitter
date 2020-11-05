package controller

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
            }
        }
        println("Bye!")
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
            println("binput is $bInput")
        }
        println("Bye!")
    }

    fun peopleMenu(){
        var pInput: Int? = -1
        while (pInput != 0) {
            println("People Management\n-----------------")
            println("1. Search People")    //listAllBills()
            println("2. Create Person")   //createNewBill()
            println("3. Update Person")   //listAllBills
            println("4. Delete Person")   //listAllBillsForDelete()

            println("\n0. Exit")
            println("------------------")

            //TODO Validate input
            pInput = getNumericInput()
            when (pInput){
                1 -> printAllPeople()
                2 -> p.newPerson(readPersonalDetails())
                3 -> handleUpdate()
                4 -> handleDelete()
                0 -> break
            }
        }
        println("Bye!")
    }

    fun printAllPeople() : Int{
        println("Listing all people")
        println("--------------------")
        for (x in p.getAllPeople()){
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

    fun handleUpdate(){
        val upperBound = printAllPeople()
        println("Who would you like to update details for?")
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
        var updateDetails = readPersonalDetails()
        p.updatePerson(selected, updateDetails.first,updateDetails.second)
    }

    fun handleDelete(){
        val upperBound = printAllPeople()
        println("Who would you like to remove?")
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
        println("\nDeleting Person from list")
        p.deletePerson(selected)
    }

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

    fun save(){
        //TODO Check order
        p.save()
        b.save()
    }

    fun load(){
        p.load()
        b.load()
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