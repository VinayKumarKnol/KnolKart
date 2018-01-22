package edu.knoldus.inventory

import java.io.{File, PrintWriter}
import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._

import scala.io.Source

object Database {
  implicit def formats: DefaultFormats = DefaultFormats

  val log: Logger = Logger.getLogger(this.getClass)

  def writeToJSON[T](inventory: List[T]): Boolean = {

    try {
      val writer = new PrintWriter(new File("database.json"))
      val json = write(inventory)
      writer.write(json)
      writer.close()
      true
    } catch {
      case except: Exception => log.info(s"\nError: ${except.getMessage}")
        false
    } finally {

    }
  }

  def readFromJSON[T](): List[T] = {
    try {
      val bufferedSource = Source.fromFile(new File("Secret.json")).mkString
      val updatedList: List[T] = read[List[T]](bufferedSource)
      updatedList
    } catch {
      case except: Exception => log.info(s"\nError: ${except.getCause}")
        List[T]()
    }
  }

}
