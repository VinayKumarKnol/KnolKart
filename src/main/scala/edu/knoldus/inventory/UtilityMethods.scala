package edu.knoldus.inventory

import edu.knoldus.models.{Database, Items, Person}

import scala.io.StdIn

object UtilityMethods {
  @deprecated("Use SortBy for item lists")
  def sortByName(list: List[Items])(direction: String): List[Items] = {
    direction match {
      case "high to low" => list.sortWith((first, second) => first.name > second.name)
      case "low to high" => list.sortWith((first, second) => first.name < second.name)
    }
  }

  /**
    * Searching utility
    *
    * @param choice   : Requires choice to decide on parameters
    * @param database : Database class's instance is required to access list
    * @return : List of items satisfying the search
    */
  def searchChoice(choice: Int, database: Database): List[Items] = {
    Log.info(s"\nEnter Search Parameters : ")
    val input: String = StdIn.readLine()
    Log.info(s"\nSort By :\n1. Name\n2. Price")
    val attribute: Int = StdIn.readInt()
    Log.info(s"\nSort :\n1. Low To High\n2. High To Low")
    val direction: Int = StdIn.readInt()
    (attribute, direction) match {
      case (1, 1) => sortBy("name", getList(choice, input, database.searchItem), "low to high")
      case (2, 1) => sortBy("price", getList(choice, input, database.searchItem), "low to high")
      case (1, 2) => sortBy("name", getList(choice, input, database.searchItem), "high to low")
      case (2, 2) => sortBy("price", getList(choice, input, database.searchItem), "high to low")
      case _ => database.itemList
    }
  }

  /**
    * Returns the list based on your search
    *
    * @param choice : To decided on params
    * @param input  : to specify category , vendor, and Id. String value required
    * @param find   : find function takes a map and returns the list of successfuly found items
    * @return
    */
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

  /**
    * Sorting other than the default ones.
    * SQL EXAMPLE :
    * SELCT * FROM ITEMS SORTBY /SOME ATTRIBUTE/ [ASC/DESC]
    *
    * @param attribute : specify the attribute through which you want to search
    * @param itemList  : specify the list on which you want to search
    * @param direction : ascending or descending order
    * @return : Returns the sorted list
    */
  def sortBy(attribute: String, itemList: List[Items], direction: String): List[Items] = {
    (attribute, direction) match {
      case ("price", "high to low") => itemList.sortWith((first, second) => first.price > second.price)
      case ("price", "low to high") => itemList.sortWith((first, second) => first.price < second.price)
      case ("name", "high to low") => itemList.sortWith((first, second) => first.name > second.name)
      case ("name", "low to high") => itemList.sortWith((first, second) => first.name < second.name)
      case _ => itemList.sorted
    }
  }

  /**
    * Sort the person list according to id
    *
    * @param list      : List of persons
    * @param direction : direction to be specified: Order- ASC or DESC
    * @return : Returns list of person
    */
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

