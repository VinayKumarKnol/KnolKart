package edu.knoldus.inventory

class Items(
             val id: Int,
             val vendorId: Int,
             val name: String,
             val price: Double,
             val quantity: Int,
             val category: String
           ) extends Ordered[Items] {
  override def compare(that: Items): Int = (this.price - that.price) intValue
}


