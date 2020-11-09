package controller

import helpers.JsonManager
import model.Bill
import model.Item
import model.Person
import kotlin.system.exitProcess

class MenuHandler(private var p: PeopleManager, private var b: BillManager, private var j: JsonManager) {



    init {
        mainMenu()
    }

    private fun mainMenu() {
        var input: Int? = -1
        while (input != 0) {
            println("----------------------------")
            println("Welcome to BillSplitter 1.0 - Console Version")
            println("----------------------------")
            println("1. Manage Bills")
            println("2. Manage People")
            println("3. Load from Memory")
            println("4. Save to Memory")
            println("\n0. Exit")
            println("----------------------------")
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


    private fun billMenu() {
        var bInput: Int? = -1
        while (bInput != 0) {
            println("----------------------------")
            println("Bill Management")
            println("----------------------------")
            println("1. List All Bills")
            println("2. Print a Bill")
            println("3. Create New Bill")
            println("4. Delete Bill")
            println("5. Update Bill Details")
            println("6. Update Bill Items")
            println("\n0. Exit")
            println("----------------------------")

            bInput = getNumericInput(6)
            when (bInput) {
                1 -> listBills(b.bills)
                2 -> handleDisplayBill(b.bills)
                3 -> handleCreateBill()
                4 -> handleDeleteBill()
                5 -> handleUpdateBill()
                6 -> itemMenu()

            }
        }
    }

    private fun listBills(bills: MutableList<Bill>): Int {
        if (bills.isNotEmpty()) {
            println("----------------------------")
            println("Listing all bills")
            println("----------------------------")
            for (x in bills) {
                val name = x.title
                val num = bills.indexOf(x) + 1
                println("$num. $name")
                for (y in p.getNamesById(x.associatedPersonIds)) {
                    println("\t\t- $y")
                }
            }
            println("----------------------------")
            return bills.size
        }
        println("No Bills to Print!")
        return 0
    }

    /**
     * Print a single bill, including items and price breakdown per person
     */
    private fun handleDisplayBill(bills: MutableList<Bill>) {
        if (bills.size != 0) {
            val billIndex = selectBillIndexFromList(bills)
            val bill = b.bills[billIndex]
            if (bill != null) {
                listItems(bill)
                return
            }
            println("Error locating that bill")
        }
    }

    private fun handleCreateBill() {
        val newBillDetails = readBillDetails()
        b.newBill(newBillDetails.first, newBillDetails.second)
    }

    private fun handleUpdateBill() {
        println("Select bill to update")
        val selected = selectBillIndexFromList(b.bills)
        val billToUpdate = b.bills[selected]

        val details = readBillDetails()

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
        val assocIds = mutableListOf<Int>()

        println("Taking bill details")
        println("----------------------------")

        while (title == "") {
            println("Enter a title for the bill:")
            print("---> ")
            title = readLine().toString()
        }
        println("Would you like to associate people with the bill now? (y/n)")
        print("---> ")
        if (readLine().toString() == "y") {
            var continueAdding = true
            if (p.groups.isNotEmpty()) {
                println("Would you like to use a saved group?")
                println("--> ")
                if (readLine().toString() == "y") {
                    println("----------------------------")
                    println("Printing Groups")
                    println("----------------------------")
                    var i = 1
                    var input: String? = ""
                    for (x in p.groups) {
                        println("$i. ${x.key}")
                        i++
                    }
                    while (!p.groups.containsKey(input)) {
                        println("Enter the name of the group to use.")
                        print("---> ")
                        input = readLine()
                    }
                    val group = p.groups[input]
                    for (x in group!!) {
                        assocIds.add(x)
                    }
                    continueAdding = false
                }
            }
            while (continueAdding) {
                println("Select a user to associate with the bill")
                val people = p.getAllPeople()
                val pIndex = selectUserIndexFromList(people)
                assocIds.add(people[pIndex].id)
                println("Would you like to add more users? (y/n)")
                print("--> ")
                if (readLine().toString() != "y")
                    continueAdding = false
            }
        }
        return Pair(first = title, second = assocIds)
    }


    /********** PERSON MENU AND FUNCTIONS **********/

    private fun peopleMenu() {
        var pInput: Int? = -1
        while (pInput != 0) {
            println("----------------------------")
            println("People Management")
            println("----------------------------")
            println("1. List all People")
            println("2. Create Person")
            println("3. Update Person")
            println("4. Delete Person")
            println("5. Create Group")
            println("6. Delete Group")
            println("\n0. Exit")
            println("----------------------------")


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
        if (p.people.isNotEmpty()) {
            println("Listing people")
            println("----------------------------")
            for (x in people) {
                val name = x.name
                val num = p.getAllPeople().indexOf(x) + 1
                println("$num. $name")
            }
            println("----------------------------")
            return p.people.size
        }
        println("No people to list!")
        return 0
    }

    private fun readPersonalDetails(): Pair<String, Boolean> {
        println("Taking personal details")
        println("----------------------------")

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
        return Pair(first = name, second = isUser)
    }

    private fun handleUpdatePerson() {
        println("Who would you like to update details for?")
        var selected = selectUserIndexFromList(p.getAllPeople())
        val updateDetails = readPersonalDetails()
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
        val groupName = readGroupName()
        println("----------------------------")
        println("Select people to add to group")
        println("----------------------------")
        val members = readToAddToGroup()
        p.createOrUpdateGroup(groupName, members)
    }

    private fun handleDeleteGroup() {
        if (p.groups.isEmpty()) {
            println("No groups to delete!")
            return
        }
        listGroups(p.groups)
        val groupName = readGroupName()
        p.removeGroup(groupName)
    }

    private fun listGroups(g: MutableMap<String, MutableList<Int>>): Int {
        println("Listing group names")
        println("----------------------------")
        var i = 1
        for (x in g) {
            val name = x.key
            println("$i. $name")
            i++
        }
        println("----------------------------")
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
        val members = mutableListOf<Int>()
        var finished = false
        while (!finished) {
            val people = p.getAllPeople()
            var index = selectUserIndexFromList(people)
            if (index < 0) {
                index = 0
            }
            val id = people[index].id
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


    /************** Item FUNCTIONS **********************/

    private fun itemMenu() {
        println("What bill would you like to edit the items of?")
        val bill = b.bills[selectBillIndexFromList(b.bills)]

        var input: Int? = -1
        while (input != 0) {
            println("----------------------------")
            println("Item Management")
            println("----------------------------")
            println("1. Add Item to bill")
            println("2. Remove Item from Bill")
            println("3. Update Item details on Bill")
            println("4. Set Item Splits")

            println("\n0. Exit")
            println("----------------------------")

            input = getNumericInput(4)
            when (input) {
                1 -> handleCreateItem(bill)
                2 -> handleDeleteItem(bill)
                3 -> handleUpdateItem(bill)
                4 -> handleSetSplits(bill)
            }
        }
    }

    private fun handleCreateItem(bill: Bill) {
        //item name
        val itemName = readItemName()

        //item value
        val value: Float = readItemValue()

        //for each person on bill, decide their split
        var splits = mutableMapOf<String, Float>()

        if (bill.associatedPersonIds.isNotEmpty()) {
            splits = readSplitValues(value, bill)
        }
        bill.addItem(Item(itemName, value, bill.id, splits))
    }

    private fun listItems(bill: Bill) {
        val namesOnBill = p.getNamesById(bill.associatedPersonIds)
        println("Listing the bill items")
        println("****************************")
        println("\t ${bill.title}")
        println("****************************")
        for (x in bill.items) {
            println("${x.name}\t\t\t ${x.value}")
            for (y in x.splits) {
                println("\t-${y.key}\t\t${y.value}")
            }
        }
        println("****************************")
        println("Total\t\t\t${bill.total()}")
        for (x in bill.subtotals(namesOnBill)) {
            println("\t${x.key}'s share\t\t${x.value}")
        }
        println("****************************")
    }

    private fun handleUpdateItem(bill: Bill) {
        if (bill.items.isEmpty()) {
            println("No items to update!")
            return
        }
        val item = bill.items[selectItemIndexFromList(bill)]

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

    private fun handleSetSplits(bill: Bill) {
        if (bill.items.isEmpty()) {
            println("No items to split!")
            return
        }
        if (bill.associatedPersonIds.isEmpty()) {
            println("No people to split items between!")
            return
        }
        for (x in bill.items) {
            println("Item: ${x.name}")
            println("----------------------------")
            x.splits = readSplitValues(x.value, bill)
        }
    }

    private fun handleDeleteItem(bill: Bill) {
        if (bill.items.isEmpty()) {
            println("No items to delete!")
            return
        }
        println("What item would you like to remove?")
        val index = selectItemIndexFromList(bill)
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

    private fun readSplitValues(value: Float, bill: Bill): MutableMap<String, Float> {
        val splits = mutableMapOf<String, Float>()

        if (bill.associatedPersonIds.isNotEmpty()) {
            var remaining = value
            for (x in bill.associatedPersonIds) {
                val name = p.getPersonById(x)!!.name
                var split: Float? = null
                while (split == null || split > remaining) {
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
        if (input > upperBound) {
            println("Selecting last entry")
            input = upperBound
        }
        return input
    }

    private fun inputToIndex(input: Int): Int {
        if (input == 0) {
            return input
        }
        return input - 1
    }

    /**
     * Take a list of people, prints them sequentially and takes user input to represent selecting that item.
     */
    private fun selectUserIndexFromList(people: MutableList<Person>): Int {
        val upperBound = printPeople(people)
        return inputToIndex(getNumericInput(upperBound)!!)
    }

    private fun selectBillIndexFromList(bills: MutableList<Bill>): Int {
        val upperBound = listBills(bills)
        return inputToIndex(getNumericInput(upperBound)!!)
    }

    private fun selectItemIndexFromList(bill: Bill): Int {
        val upperBound = bill.items.size
        println("Listing items")
        println("----------------------------")
        var num = 0
        for (x in bill.items) {
            num++
            println("$num. ${x.name}\t\t\t ${x.value}")
        }
        println("----------------------------")
        return inputToIndex(getNumericInput(upperBound)!!)
    }

    private fun save() {
        j.serializeBills(b.bills)
        j.serializePeople(p.people)
    }

    private fun load() {
        val bills = j.deserializeBills()
        if (bills.isNotEmpty()) {
            b.bills = bills
            println("Bills loaded from memory!")
        } else {
            println("No bills in memory!")
        }
        val people = j.deserializePeople()
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