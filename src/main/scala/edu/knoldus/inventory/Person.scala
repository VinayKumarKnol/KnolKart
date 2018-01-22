package edu.knoldus.inventory

class Person(
              val id: Int,
              val name: String,
              val category: String, // customer vendor
              val address: String) extends Ordered[Person] {
  override def compare(that: Person): Int = this.name.compareTo(that.name)
}