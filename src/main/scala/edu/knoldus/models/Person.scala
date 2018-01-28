package edu.knoldus.models

import edu.knoldus.inventory.log

import scala.io.StdIn

class Person(
              override val id: Int,
              val name: String,
              val category: String, // customer vendor
              val address: String) extends Commodities with Ordered[Person] {
  /**
    *
    * @param that: Send the object of compare with
    * @return
    */
  override def compare(that: Person): Int = this.name.compareTo(that.name)

  /**
    *Modifies the attribute and makes a new object of the type
    * @param attribute : attribute you would like to change. ONLY IN LOWERCASE
    * @param newValue  : Stringify the value always!!
    * @return : New Object of the type
    */
  override def updateAttribute(attribute: String, newValue: String): Person = {
    attribute match {
      case "id" => new Person(newValue.toInt, name, category, address)
      case "name" => new Person(id, newValue, category, address)
      case "category" => new Person(id, name, newValue, address)
      case "address" => new Person(id, name, category, newValue)
      case _ => this
    }
  }

  /**
    *Updates the items.json. Requires you to read from json to update the list
    * @param database : requires Database class's instance
    * @return : Boolean to ensure success
    */
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

  /**
    *Post Condition: Make sure to send the output of this function to
    *updateQuantities of class Database.
    * @param updateItemList : read the list from json and send it here.
    * @return : returns a new list of items you have bought
    */
  def buyItem(updateItemList: List[Items]): List[Items] = {
    log.info("Item's List ")
    log.info("  ID  |  Vendor  |  Name  |  Price  |  Quantity  |  Category  |")
    updateItemList
      .foreach {
        (item) => log.info(item)
      }
    log.info("Enter Id/s <Make sure you have spaces: ")
    val ids: List[String] = StdIn.readLine().split(" ").toList
    val foundItems: List[Items] = for {
      item <- updateItemList
      requiredItems <- ids
      if item.id == requiredItems.toInt
    } yield item
    foundItems
  }

  /**
    *Row description of the object
    * @return
    */
  override def toString: String = s"  $id  | $name  | $category  | $address"
}
