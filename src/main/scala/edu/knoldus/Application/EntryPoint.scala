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
    Log.info(s"\nEnter Choice :\n1. Search Item\n2. Get Detailed Description\n3. Buy Items")
    Log.info(s"\nYour Choice : ")
    val choice = StdIn.readInt()
    choice match {
      case 1 =>
        Log.info(s"\nEnter Choice :\n1. Search By Category\n2. Search By Vendor\n3. Search By ID")
        Log.info(s"\nYour Choice : ")
        searchChoice(StdIn.readInt, database)

      case 2 =>
        Log.info(s"\nCategory : ")
        val category: String = StdIn.readLine()
        Log.info(s"\nVendor : ")
        val vendor: String = StdIn.readLine()
        Log.info(s"\nID : ")
        val id: String = StdIn.readLine()
        val map = Map("category" -> category, "vendor" -> vendor, "id" -> id)
        database.searchItem(map)

      case 3 =>
    }
  }

  def inventoryModule(): Unit = {
    Log.info(s"\nEnter Vendor ID : ")
    val id = StdIn.readInt()
    //database.searchPerson()
    Log.info(s"\nEnter Choice :\n1. Add Items \n2. Delete Item\n3. Update Stocks")
    Log.info(s"\nYour Choice : ")
    val choice = StdIn.readInt()
    choice match {
      case 1 =>

      case 2 =>

      case 3 =>
    }
  }
}

object EntryPoint extends App {
  val Entry = new EntryPoint
  Log.info(s"\nEnter Choice :\n1. Customer Module\n2. Inventory Module")
  Log.info(s"\nYour Choice : ")
  val Choice: Int = StdIn.readInt()
  Choice match {
    case 1 => Entry.customerModule()
    case 2 => Entry.inventoryModule()
    case _ => Log.info(s"Wrong Choice")
  }
}
