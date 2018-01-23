package edu.knoldus.inventory

import java.io.{File, PrintWriter}

import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._

import scala.io.Source


class Database {
  val itemList: List[Items] = Database.readFromJSON("items.json").asInstanceOf[List[Items]]
  val personList: List[Person] = Database.readFromJSON("items.json").asInstanceOf[List[Person]]

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

  override def toString: String = s"The database has \nItems: ${itemList.size}" +
    s"\nPersons: ${personList.size}"

}

object Database extends App {

  val log: Logger = Logger.getLogger(this.getClass)
  val list = readFromJSON("items.json").asInstanceOf[List[Items]]
  val person = readFromJSON("person.json").asInstanceOf[List[Person]]
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
      fileName match {
        case "person.json" => read[List[Person]](bufferedSource)
        case "items.json" => read[List[Items]](bufferedSource)
      }
    } catch {
      case except: Exception => log.info(s"\nError: ${except.getMessage}")
        List[Commodities]()

    }
  }
  log.info(s"$obj")
}
