package data.multiobjectiv

/**
 * @author danglot
 */
class InstanceTSP(N : Int , M : Int) {

  private val matrixObjc = scala.collection.mutable.ListBuffer[Array[Array[Int]]]()
  
  def addMatrix(matrix : Array[Array[Int]]) = matrixObjc += matrix
  
  def n : Int = N
  def m : Int = M
  
  def getValue(m : Int ,i : Int, j : Int) : Int = {
    val tmp = matrixObjc(m)(i)(j)
    if(tmp == -1)
      matrixObjc(m)(j)(i)
    else 
      tmp
  }
    
}