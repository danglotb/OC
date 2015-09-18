package data

import java.io._

/**
 * @author danglot
 */
object Logger {
  
  val writer : Writer = new PrintWriter("output/log")
  
  def write(str : String) : Unit = writer.write(str)
  
  def close() : Unit = writer.close()
  
  
}