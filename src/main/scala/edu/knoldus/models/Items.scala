package edu.knoldus.models

class Items(
             override val id: Int,
             val vendorId: Int,
             val name: String,
             val price: Double,
             val quantity: Int,
             val category: String
           ) extends Commodities with Ordered[Items] {

  override def updateAttribute(attribute: String, newValue: String): Items = {
    attribute match {
      case "id" => new Items(newValue.toInt, vendorId, name, price, quantity, category)
      case "vendorId" => new Items(id, newValue.toInt, name, price, quantity, category)
      case "name" => new Items(id, vendorId, newValue, price, quantity, category)
      case "price" => new Items(id, vendorId, name, newValue.toDouble, quantity, category)
      case "quantity" => new Items(id, vendorId, name, price, newValue.toInt, category)
      case "category" => new Items(id, vendorId, name, price, quantity, newValue)
      case _ => this
    }

  }

  override def compare(that: Items): Int = (this.price - that.price) intValue

  override def toString: String = s"  $id  |  $vendorId  |  $name  |  $price  |  $quantity  | $category"
}


