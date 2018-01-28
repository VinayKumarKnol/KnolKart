package edu.knoldus.models

abstract class Commodities {
  val id: Int

  def updateAttribute(attribute: String, newValue: String)
}
