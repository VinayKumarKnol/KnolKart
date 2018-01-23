package edu.knoldus.inventory

import java.io.{File, PrintWriter}

import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._

import scala.io.Source

class Database {
  val item1: Items = new Items(1001,111,"Wireless Mouse",200.0,100,"Electronics")
  val item2: Items = new Items(1002,111,"Wireless Keyboard",250.0,100,"Electronics")
  val person1: Person = new Person(111,"Dell","Vendor","Nehru Place")
  val person2: Person = new Person(301,"Sudeep James Tirkey","Customer","Ghaziabad")
  val itemList: List[Items] = List(item1,item2)
  val personList: List[Person] = List(person1,person2)

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



}

object Database extends App {

  val log: Logger = Logger.getLogger(this.getClass)
  val obj = new Database()
  def writeToJSON[T](inventory: List[T], fileName: String): Boolean = {
    implicit def formats: DefaultFormats = DefaultFormats
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

  def readFromJSON[T](fileName: String): List[T] = {
    implicit def formats: DefaultFormats = DefaultFormats
    try {
      val bufferedSource = Source.fromFile(new File(fileName)).mkString
      val updatedList: List[T] = read[List[T]](bufferedSource)
      updatedList
    } catch {
      case except: Exception => log.info(s"\nError: ${except.getCause}")
        List[T]()
    }
  }

  writeToJSON[Items](obj.itemList,"items.json")
}
