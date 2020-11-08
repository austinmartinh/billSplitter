package helpers

import com.google.gson.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import model.Bill
import model.Person
import java.util.*

val BILLS_FILE = "bills.json"
val PEOPLE_FILE = "people.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listTypeBills = object : TypeToken<java.util.ArrayList<Bill>>() {}.type
val listTypePeople = object : TypeToken<java.util.ArrayList<Person>>() {}.type


class JsonManager {
    private var bills = mutableListOf<Bill>()
    private var people = mutableListOf<Person>()

    fun serializeBills(bills: MutableList<Bill>) {
        val billsString = gsonBuilder.toJson(bills, listTypeBills)
        write(BILLS_FILE, billsString)
    }

    fun serializePeople(people: MutableList<Person>) {
        val peopleString = gsonBuilder.toJson(people, listTypePeople)
        write(PEOPLE_FILE, peopleString)
    }

    fun deserializeBills(): MutableList<Bill> {
        if (exists(BILLS_FILE)) {
            val billsString = read(BILLS_FILE)
            bills = Gson().fromJson(billsString, listTypeBills)
            return bills
        }
        return mutableListOf()
    }

    fun deserializePeople(): MutableList<Person> {
        if (exists(PEOPLE_FILE)) {
            val peopleString = read(PEOPLE_FILE)
            people = Gson().fromJson(peopleString, listTypePeople)
            return people
        }
        return mutableListOf()

    }
}