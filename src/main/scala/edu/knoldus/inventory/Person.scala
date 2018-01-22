package edu.knoldus.inventory

import scala.io.StdIn

class Person(
              val id: Int,
              val name: String,
              val category: String, // customer vendor
              val address: String) extends Ordered[Person] {
  override def compare(that: Person): Int = this.name.compareTo(that.name)

  def searchChoice(choice: Int, find: Map[String, String] => List[Items]): List[Items] = {
    choice match {
      case 1 =>
        val category: String = StdIn.readLine()
        val map = Map("category" -> category, "vendor" -> "0", "id" -> "0")
        find(map)

      case 2 =>
        val vendor: String = StdIn.readLine()
        val map = Map("category" -> "0", "vendor" -> vendor, "id" -> "0")
        find(map)

      case 3 =>
        val id: String = StdIn.readLine()
        val map = Map("category" -> "0", "vendor" -> "0", "id" -> id)
        find(map)

      case _ =>
        List()

    }

  }
}
