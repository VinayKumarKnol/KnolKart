package edu.knoldus.inventory

import org.apache.log4j.Logger

import scala.io.StdIn
import edu.knoldus.inventory.UtilityMethods._

class Person(
              val id: Int,
              val name: String,
              val category: String, // customer vendor
              val address: String) extends Commodities with Ordered[Person] {
  override def compare(that: Person): Int = this.name.compareTo(that.name)

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
        sortByName(find(map))("high to low")
    }
  }

  def searchChoice(choice: Int, find: Map[String, String] => List[Items]): List[Items] = {
    val log: Logger = Logger.getLogger(this.getClass)
    log.info(s"\nEnter Search Parameters : ")
    val input: String = StdIn.readLine()
    log.info(s"\nSort By :\n1. Name\n2. Price")
    val sortBy: Int = StdIn.readInt()
    log.info(s"\nSort :\n1. Low To High\n2. High To Low")
    val sort: Int = StdIn.readInt()
    (sortBy, sort) match {
      case (1, 1) =>
      case (2, 1) =>
      case (1, 2) =>
      case (2, 2) =>

      case _ =>
        List()
    }
  }
}

