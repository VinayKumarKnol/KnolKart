package edu.knoldus.inventory

object sortImplicitMethods {

  implicit def sortByName(list: List[Items])(direction: String): List[Items] = {
    direction match {
      case "high to low" => list.sortWith((first, second) => first.name > second.name)
      case "low to high" => list.sortWith((first, second) => first.name < second.name)
    }

  }

  implicit def sortById(list: List[Person])(direction: String): List[Person] = {

    case "high to low" => list.sortWith((first, second) => first.id > second.id)
    case "low to high" => list.sortWith((first, second) => first.id < second.id)

  }
}