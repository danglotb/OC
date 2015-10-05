package main

import data.Logger
import solver.IterateLocalSearch
import solver.HillClimbing

/**
 * @author danglot
 */
object MainILS extends App {

  Logger.open("output/ils.log")
  
  val source = scala.io.Source.fromFile("input/wtbest100b.txt")
  
  val iterator = source getLines
  
  val optimas = scala.collection.mutable.ListBuffer[Int]()
  
  while(iterator hasNext)  {
   try {
     optimas += ((iterator next).filter { x => x != ' ' }).toInt
    } catch {
      case (e: java.lang.NumberFormatException) =>
    }
   }
    
  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  
  var countOpti : Int = 0

  //reach the optima
  for (i <- 0 until 125) {
    val time = System.currentTimeMillis()
    val score = IterateLocalSearch.run(HillClimbing.genFirstSolution(), HillClimbing.selectBest, HillClimbing.insertGen, HillClimbing.exchangeGen, IterateLocalSearch.selectBestSolution, IterateLocalSearch.terminationCounter).score
    val deviation : Float = if (optimas(i) != 0) (100.0*(score.toFloat - optimas(i).toFloat)/optimas(i).toFloat).toFloat else (optimas(i)-score).toFloat
    Logger.write(i+"\t"+(System.currentTimeMillis()-time)+"\t"+score+"\t"+optimas(i)+"\t"+deviation+"\n")
    print(i+"\t"+(System.currentTimeMillis()-time)+"\t"+score+"\t"+optimas(i)+"\t"+deviation+"\n")
    countOpti += (if (score == optimas(i)) 1 else 0)
  }
  
  println(countOpti)
  
  (source close)
  (Logger close)
  
}