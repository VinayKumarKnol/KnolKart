package edu.knoldus.Application

import edu.knoldus.inventory._
import edu.knoldus.inventory.UtilityMethods._
import edu.knoldus.models.Database
import org.apache.log4j.Logger

import scala.io.StdIn

class EntryPoint {
  val log: Logger = Logger.getLogger(this.getClass)
  val database: Database = new Database()

  def customerModule(): Unit = {
    //val person = new Person(1001, "", "", "")
    log.info(s"\nEnter Choice :\n1. Search Item\n2. Get Detailed Description")
    log.info(s"\nYour Choice : ")
    val choice = StdIn.readInt()
    choice match {
      case 1 =>
        log.info(s"\nEnter Choice :\n1. Search By Category\n2. Search By Vendor\n3. Search By ID")
        log.info(s"\nYour Choice : ")
        searchChoice(StdIn.readInt, database)

      case 2 =>
        log.info(s"\nCategory : ")
        val category: String = StdIn.readLine()
        log.info(s"\nVendor : ")
        val vendor: String = StdIn.readLine()
        log.info(s"\nID : ")
        val id: String = StdIn.readLine()
        val map = Map("category" -> category, "vendor" -> vendor, "id" -> id)
        database.searchItem(map)

    }
  }

  def inventoryModule(): Unit = {
    log.info(s"\nEnter Choice :\n1. Update Items \n2. Delete Items")
    log.info(s"\nYour Choice : ")
    val choice = StdIn.readInt()
    choice match {
      case 1 =>
    }
  }
}

object EntryPoint {
  val entry = new EntryPoint
  log.info(s"\nEnter Choice :\n1. Customer Module\n2. Inventory Module")
  log.info(s"\nYour Choice : ")
  val choice: Int = StdIn.readInt()
  choice match {
    case 1 => entry.customerModule()
    case 2 => entry.inventoryModule()
    case _ => log.info(s"Wrong Choice")

  }
}
