package edu.knoldus.inventory

class Person(
              override val id: Int,
              val name: String,
              val category: String, // customer vendor
              val address: String) extends Commodities with Ordered[Person] {
  override def compare(that: Person): Int = this.name.compareTo(that.name)

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

