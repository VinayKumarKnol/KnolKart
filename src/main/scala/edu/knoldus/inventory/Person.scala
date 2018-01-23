package edu.knoldus.inventory

import edu.knoldus.inventory.UtilityMethods._
import org.apache.log4j.Logger

import scala.io.StdIn

class Person(
              override val id: Int,
              val name: String,
              val category: String, // customer vendor
              val address: String) extends Commodities with Ordered[Person] {
  override def compare(that: Person): Int = this.name.compareTo(that.name)

  def searchChoice(choice: Int): List[Items] = {
    val database: Database = new Database()
    val log: Logger = Logger.getLogger(this.getClass)
    log.info(s"\nEnter Search Parameters : ")
    val input: String = StdIn.readLine()
    log.info(s"\nSort By :\n1. Name\n2. Price")
    val sortBy: Int = StdIn.readInt()
    log.info(s"\nSort :\n1. Low To High\n2. High To Low")
    val sort: Int = StdIn.readInt()
    (sortBy, sort) match {
      case (1, 1) => sortByName(getList(choice, input, database.searchItem))("low to high")
      case (2, 1) => sortByPrice(getList(choice, input, database.searchItem))("low to high")
      case (1, 2) => sortByName(getList(choice, input, database.searchItem))("high to low")
      case (2, 2) => sortByPrice(getList(choice, input, database.searchItem))("high to low")
      case _ => List()
    }
  }

  def getList(choice: Int, input: String, find: Map[String, String] => List[Items]): List[Items] = {
    choice match {
      case 1 =>
        val map = Map("category" -> input, "vendor" -> "0", "id" -> "0")
        find(map)

      case 2 =>
        val map = Map("category" -> "0", "vendor" -> input, "id" -> "0")
        find(map)

      case 3 =>
        val map = Map("category" -> "0", "vendor" -> "0", "id" -> input)
        find(map)
    }
  }

  override def updateAttribute(attribute: String, newValue: String): Person = {
    attribute match {
      case "id" => new Person(newValue.toInt, name, category, address)
      case "name" => new Person(id, newValue, category, address)
      case "category" => new Person(id, name, newValue, address)
      case "address" => new Person(id, name, category, newValue)
      case _ => this
    }
  }
}

