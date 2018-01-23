package edu.knoldus.inventory

abstract class Commodities {
  val id: Int

  def updateAttribute(attribute: String, newValue: String)
}
