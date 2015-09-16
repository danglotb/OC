package solver

import scala.collection.mutable.ListBuffer
import data._
import solver._
import sun.reflect.generics.reflectiveObjects.NotImplementedException

/**
 * @author danglot
 */

object HillClimbingOptions {

  type Options = Map[String, Any]

  def options(opt: Options, args: List[String]): Options = {
    if (args.isEmpty)
      opt
    else {
      args match {
        case "-select" :: selection :: tail  => options(opt ++ Map("select" -> selection), tail)
        case "-neighbor" :: neighbor :: tail => options(opt ++ Map("neighbor" -> neighbor), tail)
        case "-init" :: init :: tail         => options(opt ++ Map("init" -> init), tail)
        case "-k" :: k :: tail               => options(opt ++ Map("k" -> k), tail)
        case "-file" :: file :: tail         => options(opt ++ Map("file" -> file), tail)
        case _                               => usage(opt)
      }
    }
  }

  def usage(opt: Options): Options = {
    print("Options availaibles : \n")
    print("\t -select [first,best] to choose the selection mode\n")
    print("\t -neighbor [insert,swap,exchange] to choose the generation of neighbors\n")
    print("\t -init [rnd,edd,mdd] to choose the how the first solution is generates\n")
    print("\t -file <pathname> to specify the path to the file used\n")
    print("\t -k <Int> to choose how many run the apps will do (30 if no specified).\n")
    System.exit(1)
    opt
  }

}

object HillClimbing {
  
  var solver : Solver = _
  
  var reader : InstanceReader = _
  
  var score : Int = 0
  
  def report() : String = return "score : "+score
  
  def runAllInstances(gen: (Solution, (Int, Int)) => ListBuffer[Int],
          select: (((Solution, (Int, Int)) => ListBuffer[Int]), Solution, (Int, Int)) => Solution) : Unit = {
    while(reader.hasNext()) {
      run(genFirstSolution, gen, select)
    }
  }
  
  def run(currentSolution: Solution,
          gen: (Solution, (Int, Int)) => ListBuffer[Int],
          select: (((Solution, (Int, Int)) => ListBuffer[Int]), Solution, (Int, Int)) => Solution): Unit = {
    val selected = select(gen, currentSolution, (0, 1))
    if (selected == currentSolution) {
      print(currentSolution.score()+"\t")
      return
    }
    score = selected.score()
    run(selected, gen, select)
  }

  //Generate neighbors

  def exchangeGen(current: Solution, index: (Int, Int)): ListBuffer[Int] = {
    throw new NotImplementedException()
    return current.solution()
  }

  def insertGen(current: Solution, index: (Int, Int)): ListBuffer[Int] = {
    throw new NotImplementedException()
    return current.solution()
  }

  def swapGen(current: Solution, index: (Int, Int)): ListBuffer[Int] = {
    val ret = current.solution().clone
    ret -= ((current.solution())(index._1), (current.solution())(index._2))
    ret.insert(index._2, (current.solution())(index._1))
    ret.insert(index._1, (current.solution())(index._2))
    return ret
  }

  //Select the right neighbor

  def selectFirst(genfunc: (Solution, (Int, Int)) => ListBuffer[Int],
                  currentSolution: Solution,
                  index: (Int, Int)): Solution = {
    if (index._1 == index._2)
      if (index._1 == currentSolution.instance().nbJobs() - 1)
        return currentSolution
      else
        return selectFirst(genfunc, currentSolution, (index._1, index._2 + 1))
    else if (index._2 == currentSolution.instance().nbJobs() - 1)
      return selectFirst(genfunc, currentSolution, (index._1 + 1, 0))
    else {
      val neighbor = new Solution(currentSolution.instance(), genfunc(currentSolution, (index._1, index._2)))
      if (neighbor.score < currentSolution.score)
        return neighbor
      else
        return selectFirst(genfunc, currentSolution, (index._1, index._2 + 1))
    }
  }

  def selectBest(genfunc: (Solution, (Int, Int)) => ListBuffer[Int],
                 currentSolution: Solution,
                 index: (Int, Int)): Solution = {
    throw new NotImplementedException()
    return new Solution(currentSolution.instance(), currentSolution.solution())
  }

  //Init the first solution
  
  def initRandom(pathname: String) : Unit = {
    reader = new InstanceReader(100, pathname, 125)
    solver = new RandomSolver(100, reader)
  }

  def initEdd(pathname: String) : Unit = {
    reader = new InstanceReader(100, pathname, 125)
    solver = new EddSolver(100, reader)
  }

  def initMdd(pathname: String) : Unit = {
    reader = new InstanceReader(100, pathname, 125)
    solver = new MddSolver(100, reader)
  }
  
  def genFirstSolution() : Solution = {
    solver.run()
    return new Solution(solver.instance, solver.solution)
  }

}
