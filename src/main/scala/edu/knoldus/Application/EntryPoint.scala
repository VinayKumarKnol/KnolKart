package edu.knoldus.Application

import org.apache.log4j.Logger

import scala.io.StdIn

class EntryPoint{
  val log: Logger = Logger.getLogger(this.getClass)

  def customerModule() = {
    log.info(s"\nEnter Choice :\n1. Search Item\n2. ")
    log.info(s"\nYour Choice : ")
    val choice = StdIn.readInt()
    choice match {}
  }
  def inventoryModule() = {
    log.info(s"\nEnter Choice :\n1. Update Items \n2. Delete Items")
    log.info(s"\nYour Choice : ")
    val choice = StdIn.readInt()
    choice match {}
  }
}

object EntryPoint {
  val entry = new EntryPoint
  val log: Logger = Logger.getLogger(this.getClass)
  log.info(s"\nEnter Choice :\n1. Customer Module\n2. Inventory Module")
  log.info(s"\nYour Choice : ")
  val choice = StdIn.readInt()
  choice match {
    case 1 => entry.customerModule()
    case 2 => entry.inventoryModule()
    case _ => log.info(s"Wrong Choice")
  }
}
