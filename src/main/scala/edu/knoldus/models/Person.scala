package edu.knoldus.models

import scala.io.StdIn
import edu.knoldus.inventory.log

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

  def addItem(database: Database): Boolean = {
    if (this.category == "Vendor") {
      log.info(s"\nEnter Name : ")
      val name = StdIn.readLine()
      log.info(s"\nEnter Price : ")
      val price = StdIn.readDouble()
      log.info(s"\nEnter Quantity : ")
      val quantity = StdIn.readInt()
      log.info(s"\nEnter Category : ")
      val category = StdIn.readLine()
      val initialList = database.itemList
      val id = initialList.max(id)
      val newItem: Items = new Items(id.id + 1, this.id, name, price, quantity, category)
      database.addToList("items", newItem)
    }
    else
      false
  }

  def restockItem(database: Database): Boolean = {
    if (this.category == "vendor") {
      log.info(s"\nEnter ID : ")
      val id = StdIn.readInt()
      val map = Map("vendor" -> this.id.toString, "id" -> id.toString)
      val result = database.searchItem(map)
      if (result.nonEmpty) {
        val top=result.head
        log.info(s"\nEnter Quantity : ")
        val quantity = StdIn.readInt()
        val updates = new Items(top.id,top.vendorId,top.name,top.price,quantity,top.category)
        database.addToList("items",updates)
      }
      else
        false
    }
    else
      false
  }

  def buyItem(database: Database): List[Items] = {
    val  newItemList : List[Items] = database.itemList
  }

  override toString: String = s"  $id  | $name  | $category  | $address"
}
