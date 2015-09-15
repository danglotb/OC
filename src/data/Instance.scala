package data

/**
 * @author danglot
 */
class Instance(nbJobs : Int) {
  
  val processingTimes = Array.ofDim[Int](nbJobs)
  
  val weights = Array.ofDim[Int](nbJobs)
  
  val dueDates = Array.ofDim[Int](nbJobs)
  
  def nbJobs() : Int = nbJobs
  
  override def toString() : String = {
    var ret : String = "nb jobs : "+nbJobs+"\n"
    for (i <- 0 until this.nbJobs) 
      ret += "p:"+this.processingTimes(i)+" w:"+this.weights(i)+" d:"+this.dueDates(i)+" \n"
    ret
  }
  
}