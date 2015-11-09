package data.multiobjectiv

/**
 * @author danglot
 */
object tspReader extends App {

  def read(pathname: String) : Array[Array[Int]] = {
    val source = scala.io.Source.fromFile(pathname)

    val it = readHeader("", source getLines)

    val matrix = Array.fill(100, 100)(-1)
    
    matrix(0)(0) = 0
    
    readMatrix(it,matrix,0,1)
  }
  
  def readMatrix(it : Iterator[String], matrix : Array[Array[Int]], x : Int, y : Int) : Array[Array[Int]] = {
    if (!(it hasNext))
      matrix
    else {
      val value = (it next).toInt
      if (y == 100) {
        matrix(x+1)(x+1) = value
        readMatrix(it, matrix, x+1, x+2)
      } else {
        matrix(x)(y) = value
        readMatrix(it, matrix, x, y+1)
      }
    }
  }

  def readHeader(currentLine: String, it : Iterator[String]): Iterator[String] = {
    try {
      if (currentLine.toInt == 0)
        return it
      else
        readHeader(it next, it)
    } catch {
      case  e : java.lang.NumberFormatException =>  readHeader(it next, it)
    }
  }

  read("input/tsp/randomA100.tsp")

}