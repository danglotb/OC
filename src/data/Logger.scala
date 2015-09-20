package data

import java.io._

/**
 * @author danglot
 */
object Logger {
  
  var writer : FileWriter = _
  
  def open(pathname : String) : Unit = writer = new FileWriter(pathname, true)
  
  def write(str : String) : Unit = writer.write(str)
  
  def close() : Unit = writer.close()
  
}