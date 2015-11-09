package data.multiobjectiv


/**
 * @author danglot
 */
object tspBuilder {
  
  /*
   * Atm defined in the code
   */
  
  val M = 2
  
  val N = 100
  
  def build2mTsp(pathname1 : String, pathname2 : String) : InstanceTSP = {
    val instance = new InstanceTSP(N,M)
    instance addMatrix(tspReader.read(pathname1))
    instance addMatrix(tspReader.read(pathname2))
    instance
  }
  
}