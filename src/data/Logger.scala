package data

import java.io._

/**
 * @author danglot
 */
object Logger {
  
  var writer : PrintWriter = _
  
  def open(pathname : String) : Unit = writer = new PrintWriter(new File(pathname))
  
  def write(str : String) : Unit = writer.write(str)
  
  def close() : Unit = writer.close()
  
  
}