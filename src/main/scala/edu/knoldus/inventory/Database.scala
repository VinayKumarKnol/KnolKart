package edu.knoldus.inventory

import java.io.{File, PrintWriter}

import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._

import scala.io.Source

class Database {
  val itemList: List[Items] = Database.readFromJSON[Items]("items.json")
  val personList: List[Person] = Database.readFromJSON[Person]("person.json")

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

  def serachPersonById(query: Map[String, Int]): List[Person] = {
    val id: Int = query.getOrElse("id", 0)
    personList.filter(person => person.id == id)
  }


}

object Database {
  implicit def formats: DefaultFormats = DefaultFormats

  val log: Logger = Logger.getLogger(this.getClass)

  def writeToJSON[T](inventory: List[T], fileName: String): Boolean = {
    try {
      val writer = new PrintWriter(new File(fileName))
      val json = write(inventory)
      writer.write(json)
      writer.close()
      true
    } catch {
      case except: Exception => log.info(s"\nError: ${except.getMessage}")
        false
    }
  }

  def readFromJSON(fileName: String): List[Items]  = {
    try {
      val bufferedSource = Source.fromFile(new File(fileName)).mkString
      fileName match {
        case "items.json" =>  read[List[T]](bufferedSource).asInstanceOf[List[Items]]
        case "person.json" => read[List[T]](bufferedSource).asInstanceOf[List[Person]]
       }

    } catch {
      case except: Exception => log.info(s"\nError: ${except.getCause}")
        List[T]()
    }
  }
}
