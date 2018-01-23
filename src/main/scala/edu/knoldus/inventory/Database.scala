package edu.knoldus.inventory

import java.io.{File, PrintWriter}
import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._
import scala.io.Source


class Database {
  val item1: Items = new Items(1001, 111, "Wireless Mouse", 200.0, 100, "Electronics")
  val item2: Items = new Items(1002, 111, "Wireless Keyboard", 250.0, 100, "Electronics")
  val person1: Person = new Person(111, "Dell", "Vendor", "Nehru Place")
  val person2: Person = new Person(301, "Sudeep James Tirkey", "Customer", "Ghaziabad")
  val itemList: List[Items] = List(item1, item2)
  val personList: List[Person] = List(person1, person2)

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

  def searchPerson(query: Map[String, String]): List[Person] = {
    //name -> " "
    val name: String = query.getOrElse("name", "null")
    personList.filter(person => person.name == name)
  }

  def searchPersonById(query: Map[String, Int]): List[Person] = {
    val id: Int = query.getOrElse("id", 0)
    personList.filter(person => person.id == id)
  }


}

object Database extends App {

  val log: Logger = Logger.getLogger(this.getClass)

  val obj = new Database()

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

  def readFromJSON(fileName: String): List[Commodities] = {
    try {
      implicit def formats: DefaultFormats = DefaultFormats
      val bufferedSource = Source.fromFile(new File(fileName)).mkString
      read[List[Commodities]](bufferedSource)

    } catch {
      case except: Exception => log.info(s"\nError: ${except.getCause}")
        List[Commodities]()
    }
  }

  val list = writeToJSON(obj.itemList, "items.json")
  val person = writeToJSON(obj.personList, "person.json")
  println(list)
}
