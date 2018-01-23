package edu.knoldus.inventory

import org.apache.log4j.Logger
import scala.io.StdIn

object UtilityMethods {
  @deprecated("Use SortBy for item lists")
  def sortByName(list: List[Items])(direction: String): List[Items] = {
    direction match {
      case "high to low" => list.sortWith((first, second) => first.name > second.name)
      case "low to high" => list.sortWith((first, second) => first.name < second.name)
    }
  }

  def searchChoice(choice: Int, database: Database): List[Items] = {
    val log: Logger = Logger.getLogger(this.getClass)
    log.info(s"\nEnter Search Parameters : ")
    val input: String = StdIn.readLine()
    log.info(s"\nSort By :\n1. Name\n2. Price")
    val attribute: Int = StdIn.readInt()
    log.info(s"\nSort :\n1. Low To High\n2. High To Low")
    val direction: Int = StdIn.readInt()
    (attribute, direction) match {
      case (1, 1) => sortBy("name", getList(choice, input, database.searchItem), "low to high")
      case (2, 1) => sortBy("price", getList(choice, input, database.searchItem), "low to high")
      case (1, 2) => sortBy("name", getList(choice, input, database.searchItem), "high to low")
      case (2, 2) => sortBy("price", getList(choice, input, database.searchItem), "high to low")
      case _ => database.itemList
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

  def sortBy(attribute: String, itemList: List[Items], direction: String): List[Items] = {
    (attribute, direction) match {
      case ("price", "high to low") => itemList.sortWith((first, second) => first.price > second.price)
      case ("price", "low to high") => itemList.sortWith((first, second) => first.price < second.price)
      case ("name", "high to low") => itemList.sortWith((first, second) => first.name > second.name)
      case ("name", "low to high") => itemList.sortWith((first, second) => first.name < second.name)
    }
  }

  def sortById(list: List[Person])(direction: String): List[Person] = {
    direction match {
      case "high to low" => list.sortWith((first, second) => first.id > second.id)
      case "low to high" => list.sortWith((first, second) => first.id < second.id)
    }
  }

  @deprecated("Use SortBy for item lists")
  def sortByPrice(list: List[Items])(direction: String): List[Items] = {
    direction match {
      case "high to low" => list.sortWith((first, second) => first.price > second.price)
      case "low to high" => list.sortWith((first, second) => first.price < second.price)
    }
  }
}

