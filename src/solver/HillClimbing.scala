package solver

import scala.collection.mutable.ListBuffer

/**
 * @author danglot
 */

object HillClimbingOption {
  
  type Options = Map[String, Any]
  
  def options(opt : Options, args : List[String]) : Options = {
    
    args match {
      case "-select" :: selection :: tail => options( opt++Map("select" -> selection), tail)
      case "-nb" :: nb :: tail => options( opt++Map("nb" -> nb), tail)
      case "-init" :: init :: tail => options(opt++Map("init" -> init), tail)
      case _ => usage(opt)
    }
  }
  
  def usage(opt : Options) : Options = {
    opt
  }
  
}


class HillClimbing(options : Map[String, Any]) {
  
  var currentSolution : ListBuffer[Int] = _
  
  var voisinFunc : ListBuffer[Int] => _  = _
  
  var initFunc : () => ListBuffer[Int] = _
  
  initFunc = randomInit
  
  def run() : Unit = {
    //initSol
    currentSolution = initFunc()
    
    
    //GenNei
    //Select
    
  }
  
  def randomInit() : ListBuffer[Int] = {
    
    
  }

  
  
}