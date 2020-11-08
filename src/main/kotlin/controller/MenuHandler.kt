package controller

import helpers.JsonManager
import model.Bill
import model.Item
import model.Person
import java.io.*
import kotlin.system.exitProcess

class MenuHandler(var p: PeopleManager, var b: BillManager, var j: JsonManager) {



    init {
        mainMenu()
    }

    fun mainMenu() {
        var input: Int? = -1
        while (input != 0) {
            println("--------------------")
            println("Welcome to BillSplitter 1.0 - Console Version")
            println("--------------------")
            println("1. Manage Bills")
            println("2. Manage People")
            println("3. Load from Memory")
            println("4. Save to Memory")
            println("\n0. Exit")
            println("--------------------")
            //TODO Validate input
            input = getNumericInput(5)
            when (input) {
                1 -> billMenu()
                2 -> peopleMenu()
                3 -> load()
                4 -> save()
                0 -> exit()
            }
        }
    }

    /************ BILL Functions **************/


    fun billMenu() {
        var bInput: Int? = -1
        while (bInput != 0) {
            println("Bill Management\n-----------------")
            println("1. List Bills")
            println("2. Print a Bill")
            println("3. Create New Bill")
            println("4. Delete Bill")
            println("5. Update Bill Details")
            println("6. Update Bill Items")


            println("\n0. Exit")
            println("------------------")

            //TODO Validate input
            bInput = getNumericInput(5)
            when (bInput) {
                1 -> listBills(b.bills)
                2 -> handleDisplayBill()
                3 -> handleCreateBill()
                4 -> handleDeleteBill()
                5 -> handleUpdateBill()
                6 -> itemMenu()
            }
        }
    }

    private fun listBills(bills: MutableList<Bill>): Int {
        println("Listing all bills")
        println("--------------------")
        for (x in bills) {
            var name = x.title
            var num = bills.indexOf(x) + 1
            println("$num. $name")
        }
        println("--------------------")
        return bills.size
    }

    private fun handleCreateBill() {
        var newBillDetails = readBillDetails()
        b.newBill(newBillDetails.first, newBillDetails.second)
    }

    private fun handleUpdateBill() {
        println("Select bill to update")
        var selected = selectBillIndexFromList(b.bills)
        var billToUpdate = b.bills[selected]

        var details = readBillDetails()

        for (x in billToUpdate.associatedPersonIds)
            if (!details.second.contains(x)) {
                details.second.add((x))
            }

        billToUpdate.title = details.first
        billToUpdate.associatedPersonIds = details.second

        b.bills[selected] = billToUpdate
    }

    private fun handleDeleteBill() {
        if (b.bills.isEmpty()) {
            println("No bills to delete!")
            return
        }
        println("Select bill to delete")
        var selected = selectBillIndexFromList(b.bills)
        if (selected < 0) {
            selected = 0
        }
        b.bills.removeAt(selected)
    }

    private fun readBillDetails(): Pair<String, MutableList<Int>> {
        var title = ""
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
        if (readLine().toString() == "y") {
            var continueAdding = true

            println("Would you like to use a saved group?")
            println("--> ")
            if (readLine().toString() == "y") {
                //TODO
                //printgroups()
                //add groupToBill()
                //sets groupId
                //sets
                continueAdding = false
            }
            while (continueAdding) {
                println("Select a user to associate with the bill")
                var people = p.getAllPeople()
                var pIndex = (selectUserIndexFromList(people))
                assocIds.add(pIndex)
                println("Would you like to add more users? (y/n)")
                print("--> ")
                if (readLine().toString() != "y")
                    continueAdding = false
            }
        }
        return Pair(first = title, second = assocIds)
    }

    private fun printBills(bills: MutableList<Bill>): Int {
        println("Listing bills")
        println("--------------------")
        for (x in bills) {
            var name = x.title
            var num = b.bills.indexOf(x) + 1
            println("$num. $name")
        }
        println("--------------------")
        return p.people.size
    }


    /********** PERSON MENU AND FUNCTIONS **********/

    private fun peopleMenu() {
        var pInput: Int? = -1
        while (pInput != 0) {
            println("People Management\n-----------------")
            println("1. List all People")    //listAllBills()
            println("2. Create Person")   //createNewBill()
            println("3. Update Person")   //listAllBills
            println("4. Delete Person")   //listAllBillsForDelete()
            println("5. Create Group")
            println("6. Delete Group")
            println("\n0. Exit")
            println("------------------")

            //TODO Validate input
            pInput = getNumericInput(7)
            when (pInput) {
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

    private fun printPeople(people: MutableList<Person>): Int {
        println("Listing people")
        println("--------------------")
        for (x in people) {
            var name = x.name
            var num = p.getAllPeople().indexOf(x) + 1
            println("$num. $name")
        }
        println("--------------------")
        return p.people.size
    }

    private fun readPersonalDetails(): Pair<String, Boolean> {
        println("Taking personal details")
        println("---------------------")

        var name = ""
        var isUser = false

        while (name == "") {
            println("Enter the persons name:")
            print("---> ")
            name = readLine().toString()
        }

        println("Is this person you? (y/n)")
        print("---> ")
        if (readLine() == "y") {
            isUser = true
        }
        print("user value: $isUser")
        return Pair(first = name, second = isUser)
    }

    private fun handleUpdatePerson() {
        println("Who would you like to update details for?")
        var selected = selectUserIndexFromList(p.getAllPeople())
        var updateDetails = readPersonalDetails()
        if (selected < 0) {
            selected = 0
        }
        p.updatePerson(selected, updateDetails.first, updateDetails.second)
    }

    private fun handleDeletePerson() {
        if (p.people.isEmpty()) {
            println("No people to delete!")
            return
        }
        println("Who would you like to remove?")
        var selected = selectUserIndexFromList(p.getAllPeople())
        println("\nDeleting Person from list")
        println("----------------------------")
        if (selected < 0) {
            selected = 0
        }
        p.deletePerson(selected)
    }

    private fun handleCreateGroup() {
        var groupName = readGroupName()
        println("----------------------------")
        println("Select people to add to group")
        println("----------------------------")
        var members = readToAddToGroup()
        p.createOrUpdateGroup(groupName, members)
    }

    private fun handleDeleteGroup() {
        if (p.groups.isEmpty()) {
            println("No groups to delete!")
            return
        }
        listGroups(p.groups)
        var groupName = readGroupName()
        p.removeGroup(groupName)
    }

    private fun listGroups(g: MutableMap<String, MutableList<Int>>): Int {
        println("Listing group names")
        println("--------------------")
        var i = 1
        for (x in g) {
            var name = x.key
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

    private fun readToAddToGroup(): MutableList<Int> {
        var members = mutableListOf<Int>()
        var finished = false
        while (!finished) {
            val people = p.getAllPeople()
            var index = selectUserIndexFromList(people)
            if (index < 0) {
                index = 0
            }
            var id = people[index].id
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

    private fun handleDisplayBill() {
        val billId = selectBillIndexFromList(b.bills)
        val bill = b.getBillById(billId)
        if (bill != null) {
            listItems(bill)
            return
        }
        println("Error locating that bill")
    }

    /************** Item FUNCTIONS **********************/

    private fun itemMenu() {
        println("What bill would you like to edit the items of?")
        print("--->")
        var bill = b.bills[selectBillIndexFromList(b.bills)]

        var input: Int? = -1
        while (input != 0) {
            println("Item Management\n-----------------")
            println("1. Add Item to bill")
            println("2. Remove Item from Bill")
            println("3. Update Item on Bill")

            println("\n0. Exit")
            println("------------------")

            //TODO Validate input
            input = getNumericInput(4)
            when (input) {
                1 -> handleCreateItem(bill)
                2 -> handleDeleteItem(bill)
                3 -> handleUpdateItem(bill)
            }
        }
    }

    private fun handleCreateItem(bill: Bill) {
        //item name
        var itemName = readItemName()

        //item value
        var value: Float = readItemValue()

        //for each person on bill, decide their split
        var splits = mutableMapOf<String, Float>()

        if (bill.associatedPersonIds.isNotEmpty()) {
            var remaining = value
            for (x in bill.associatedPersonIds) {
                var name = p.getPersonById(x)!!.name
                var split: Float? = null
                while (split == null) {
                    println("How much does $name owe? ($remaining unaccounted for)")
                    print("---> ")
                    split = readLine()?.toFloatOrNull()
                }
                remaining -= split
                splits[name] = split
            }
        }
        bill.addItem(Item(itemName, value, bill.id))
    }

    fun listItems(bill: Bill) {
        val namesOnBill = p.getNamesById(bill?.associatedPersonIds!!)
        if (bill != null) {
            println("Listing the bill items")
            println("****************************")
            println("\t ${bill.title}")
            println("****************************")
            for (x in bill.items) {
                println("$x.name\t\t\t ${x.value}")
                for (y in x.splits) {
                    println("\t-${y.key}\t\t${y.value}")
                }
            }
            println("****************************")
            println("Total\t\t\t${bill.total()}")
            for (x in bill.subtotals(namesOnBill)) {
                println("\t${x.key}'s share\t\t${x.value}")
            }
        }
    }

    fun handleUpdateItem(bill: Bill) {
        if (bill.items.isEmpty()) {
            println("No items to update!")
            return
        }
        var item = bill.items[selectItemIndexFromList(bill)]

        var name = item.name
        var newValue = item.value
        var recalc = false
        var splits = item.splits

        println("Would you like to update the item name? (y/n)")
        print("-->")
        if (readLine() == "y") {
            name = readItemName()
        }

        println("Would you like change the value of the item? (y/n)")
        println("--> ")
        if (readLine() == "y") {
            newValue = readItemValue()
        }

        println("Would you like to automatically recalculate splits? (y/n)")
        if (readLine() == "y") {
            recalc = true
        } else {
            splits = readSplitValues(newValue, bill)
        }

        item.update(name, newValue, recalc, splits)
    }

    fun handleDeleteItem(bill: Bill) {
        if (bill.items.isEmpty()) {
            println("No items to delete!")
            return
        }
        println("What item would you like to remove?")
        var index = selectItemIndexFromList(bill)
        bill.items.removeAt(index)


    }

    private fun readItemName(): String {
        var itemName = ""
        while (itemName == "") {
            println("Enter a name for the item")
            print("---> ")
            itemName = readLine().toString()
        }
        return itemName
    }

    private fun readItemValue(): Float {
        var value: Float? = null
        while (value == null) {
            println("Enter the value of the item")
            print("---> ")
            value = readLine()?.toFloatOrNull()
        }
        return value
    }

    fun readSplitValues(value: Float, bill: Bill): MutableMap<String, Float> {
        var splits = mutableMapOf<String, Float>()

        if (bill.associatedPersonIds.isNotEmpty()) {
            var remaining = value
            for (x in bill.associatedPersonIds) {
                var name = p.getPersonById(x)!!.name
                var split: Float? = null
                while (split == null) {
                    println("How much does $name owe? ($remaining unaccounted for)")
                    print("---> ")
                    split = readLine()?.toFloatOrNull()
                }
                remaining -= split
                splits[name] = split
            }
        }
        return splits
    }

    /************** HELPER FUNCTIONS **********************/

    /**
     * Takes input until a number is given
     */
    private fun getNumericInput(upperBound: Int): Int? {
        var input: Int? = null
        while (input == null || input < 0) {
            print("Please enter a numeric index\n--->  ")
            input = readLine()?.toIntOrNull()
        }
        if (input < 0) {
            println("Selecting first entry")
            input = 0
        }
        if (input >= upperBound) {
            println("Selecting last entry")
            input = upperBound
        }
        return input
    }

    /**
     * Take a list of people, prints them sequentially and takes user input to represent selecting that item.
     */
    private fun selectUserIndexFromList(people: MutableList<Person>): Int {
        val upperBound = printPeople(people)

        return getNumericInput(upperBound)!! - 1
    }

    private fun selectBillIndexFromList(bills: MutableList<Bill>): Int {
        val upperBound = printBills(bills)
        return getNumericInput(upperBound)!! - 1
    }

    private fun selectItemIndexFromList(bill: Bill): Int {
        val upperBound = bill.items.size

        println("Listing items")
        println("--------------------")
        var num = 0
        for (x in bill.items) {
            num++
            println("$num. $x.name\t\t\t ${x.value}")
        }
        println("--------------------")

        return getNumericInput(upperBound)!! - 1
    }

    private fun save() {
        j.serializeBills(b.bills)
        j.serializePeople(p.people)
    }

    private fun load() {
        var bills = j.deserializeBills()
        if (bills.isNotEmpty()) {
            b.bills = bills
            println("Bills loaded from memory!")
        } else {
            println("No bills in memory!")
        }
        var people = j.deserializePeople()
        if (people.isNotEmpty()) {
            p.people = people
            println("People loaded from memory!")
        } else {
            println("No people in memory!")
        }

    }

    private fun exit() {
        println("Bye!")
        exitProcess(0)
    }


}