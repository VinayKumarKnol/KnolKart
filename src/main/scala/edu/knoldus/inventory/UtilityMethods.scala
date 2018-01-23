package edu.knoldus.inventory

object UtilityMethods {

  def sortByName(list: List[Items])(direction: String): List[Items] = {
    direction match {
      case "high to low" => list.sortWith((first, second) => first.name > second.name)
      case "low to high" => list.sortWith((first, second) => first.name < second.name)
    }
  }

  def sortById(list: List[Person])(direction: String): List[Person] = {
    direction match {
      case "high to low" => list.sortWith((first, second) => first.id > second.id)
      case "low to high" => list.sortWith((first, second) => first.id < second.id)
    }
  }

  def sortByPrice(list: List[Items])(direction: String): List[Items] = {
    direction match {
      case "high to low" => list.sortWith((first, second) => first.price > second.price)
      case "low to high" => list.sortWith((first, second) => first.price < second.price)
    }
  }

}