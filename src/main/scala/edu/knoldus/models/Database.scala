package edu.knoldus.models

import java.io.{File, PrintWriter}

import edu.knoldus.inventory._
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._

import scala.io.Source

class Database {
  val itemList: List[Items] = Database.readFromJSON("items.json").asInstanceOf[List[Items]]
  val personList: List[Person] = Database.readFromJSON("items.json").asInstanceOf[List[Person]]

  /**
    * Pre Condition: Updated List
    * @param query: A Map of (id -> int, vendor -> id(int), category -> String)
    * @return :  List Of Searched item is returned
    */
  def searchItem(query: Map[String, String]): List[Items] = {
    //toInt on Vendor , Id
    val category: String = query.getOrElse("category", " ")
    val vendor: Int = query.getOrElse("vendor", "0").toInt
    val id: Int = query.getOrElse("id", "0").toInt
    val categoryResult: List[Items] = itemList.filter((entry: Items) => entry.category == category)
    val vendorResult: List[Items] = itemList.filter((entry: Items) => entry.vendorId == vendor)
    val idResult: List[Items] = itemList.filter((entry: Items) => entry.id == id)
    categoryResult ::: vendorResult ::: idResult

  }

  /**
    * Pre Condition : Requires an updated list of person
    *You can search the person by name
    * @param query: Map( name -> String)
    * @return
    */
  def searchPerson(query: Map[String, String]): List[Person] = {
    val name: String = query.getOrElse("name", "null")
    personList.filter(person => person.name == name)
  }

  /**
    * Pre Condition: Requires an updated list of items
    *
    *Search on person through Id
    * @param query: Map(id -> Int )
    * @return : List of Person
    */
  def searchPersonById(query: Map[String, Int]): List[Person] = {
    val id: Int = query.getOrElse("id", 0)
    personList.filter(person => person.id == id)
  }

  /**
    *Pre Condition : None
    * @param whichList : Specify which list to update
    * @param commodity : send the object of that type.
    * @return : Boolean values can be used to check success of the operation
    */
  def addToList(whichList: String, commodity: Commodities): Boolean = {
    whichList match {
      case "items" =>
        if (itemList.contains(commodity)) true else Database.writeToJSON(itemList :+ commodity, "items.json")
      case "person" =>
        if (personList.contains(commodity)) true else Database.writeToJSON(personList :+ commodity, "person.json")
      case _ => false
    }
  }

  /**
    *Pre Condition : Before using this operation make sure you have an updated list
    * @param itemsBought : Specify what items you have bought. requires only ids
    * @return : Boolean value can used to check success of the operation
    */
  def updateQuantities(itemsBought: List[Items]): Boolean = {
    val updatedList: List[Items] = for {
      purchased <- itemsBought
      itemInDatabase <- itemList
      if purchased.id == itemInDatabase.id
    } yield itemInDatabase.updateAttribute("quantity", (itemInDatabase.quantity - 1).toString)

    Database.writeToJSON(updatedList, "items.json")

  }

  /**
    *Returns minimal stats about Database: Tolal counts
    * @return
    */
  override def toString: String = s"The database has \nItems: ${itemList.size}" +
    s"\nPersons: ${personList.size}"

}

object Database {

  val list: List[Items] = readFromJSON("items.json").asInstanceOf[List[Items]]
  val person: List[Person] = readFromJSON("person.json").asInstanceOf[List[Person]]
  val obj: Database = new Database()

  /**
    *Use it safely. Works with either of the category
    * @param inventory : Send the updated list of commodities.
    * @param fileName : Name of file to store
    * @return : boolean status to check the success of operation
    */
  def writeToJSON(inventory: List[Commodities], fileName: String): Boolean = {
    try {
      implicit def formats: DefaultFormats = DefaultFormats

      val writer = new PrintWriter(new File(fileName))
      val json = writePretty(inventory)
      writer.write(json)
      writer.close()
      true
    } catch {
      case except: Exception => log.info(s"\nError: ${except.getMessage}")
        false
    }
  }

  /**
    *Pre Condition: Make sure the file exist or You have performed writeToJson
    * Works with either of the category
    * @param fileName: We need the file name
    * @return : List of either type of Commodities
    */
  def readFromJSON(fileName: String): List[Commodities] = {
    try {
      implicit def formats: DefaultFormats = DefaultFormats

      val bufferedSource = Source.fromFile(new File(fileName)).mkString
      fileName match {
        case "person.json" => read[List[Person]](bufferedSource)
        case "items.json" => read[List[Items]](bufferedSource)
      }
    } catch {
      case except: Exception => log.info(s"\nError: ${except.getMessage}")
        List[Commodities]()

    }
  }

}
