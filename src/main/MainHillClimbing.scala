package main

import scala.collection.mutable.ListBuffer
import data._
import solver._

object MainHillClimbing extends App {

  var selectFunc: ((((Solution) => Solution), Solution) => (Solution)) = _
  var initFunc: (String, Int, Int, Boolean) => Unit = _
  var genFunc: (Solution) => Solution = _
  var nbRuns: Int = 30
  var pathname: String = ""

  var name: String = ""

  parseOptions
  run

  def run(): Unit = {

    var optimaCounter = 0

    val optimas = Util.readOptimas
    Logger.open("output/bestmddswapdeviation.log")
    HillClimbing.initMdd(pathname, 100, 125, false)
    for (i <- 0 until 125) {
      val score = HillClimbing.run(HillClimbing.genFirstSolution, HillClimbing.swapGen, HillClimbing.selectBest)
      val deviation: Float = if (optimas(i) != 0) (100.0 * (score.toFloat - optimas(i).toFloat) / optimas(i).toFloat).toFloat else (optimas(i) - score).toFloat
      Logger.write(i + "\t" + score + "\t" + deviation + "\t" + optimas(i) + "\n")
      print(i + "\t" + score + "\t" + deviation + "\t" + optimas(i) + "\n")
      if (optimas(i) == score) {
        optimaCounter += 1
      }
    }

    Logger.write("optima found : " + optimaCounter + " / 125")

    Logger.close()
  }

  def runAll(): Unit = {
    Logger.open("output/log")

    val values: ListBuffer[(ListBuffer[Int], ListBuffer[Long])] = new ListBuffer[(ListBuffer[Int], ListBuffer[Long])]()
    for (i <- 0 until nbRuns) {
      println(i + " run")
      initFunc(pathname, 100, 125, false)
      val time: Long = System.currentTimeMillis()
      HillClimbing.runAllInstances(genFunc, selectFunc)
      values += ((HillClimbing.scores, HillClimbing.times))
    }

    val avgTime: Array[Long] = Array.ofDim[Long](125)
    val avgScore: Array[Int] = Array.ofDim[Int](125)

    var strTime: String = name + "Time"
    var strScore: String = name + "Score"

    for (i <- 0 until 125) {
      for (j <- 0 until values.length) {
        avgScore(i) += (values(j)._1(i))
        avgTime(i) += (values(j)._2(i))
      }
    }

    for (i <- 0 until 125) {
      avgScore(i) = (avgScore(i) / nbRuns)
      avgTime(i) = (avgTime(i) / nbRuns)
    }

    for (i <- 0 until 125) {
      strScore += "\t" + avgScore(i)
      strTime += "\t" + avgTime(i)
    }

    Logger.write(strScore + "\n")
    Logger.write(strTime + "\n")

    Logger.close

  }

  def parseOptions(): Unit = {
    val options = HillClimbingOptions.options(Map(), args.toList)

    options.get("select") match {
      case Some(strSelect) =>
        strSelect match {
          case "first" => selectFunc = HillClimbing.selectFirst
          case "best"  => selectFunc = HillClimbing.selectBest
          case _       => HillClimbingOptions.usage(options)
        }
        name += strSelect
      case _ => HillClimbingOptions.usage(options)
    }

    options.get("init") match {
      case Some(strInit) =>
        strInit match {
          case "rnd" => initFunc = HillClimbing.initRandom
          case "edd" => initFunc = HillClimbing.initEdd
          case "mdd" => initFunc = HillClimbing.initMdd
          case _     => HillClimbingOptions.usage(options)
        }
        name += strInit
      case _ => HillClimbingOptions.usage(options)
    }

    options.get("neighbor") match {
      case Some(strNeighbor) =>
        strNeighbor match {
          case "insert"   => genFunc = HillClimbing.insertGen
          case "swap"     => genFunc = HillClimbing.swapGen
          case "exchange" => genFunc = HillClimbing.exchangeGen
          case _          => HillClimbingOptions.usage(options)
        }
        name += strNeighbor
      case _ => HillClimbingOptions.usage(options)
    }

    options.get("k") match {
      case Some(k) => nbRuns = k.toString.toInt
      case _       =>
    }

    options.get("file") match {
      case Some(file) => pathname = file.toString
      case _          => HillClimbingOptions.usage(options)
    }

  }
}

object MainVnd extends App {

  Logger.open("output/logvnd")

  HillClimbing.initRandom("input/wt100.txt", 100, 125)
  var time: Long = System.currentTimeMillis
  Logger.write("exchange_insert_swap_rnd\t" +
    (HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.insertGen, HillClimbing.swapGen),
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0)).score + "\t" + (System.currentTimeMillis - time) + "ms\n")

  HillClimbing.initRandom("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
  Logger.write("exchange_swap_insert_rnd\t" +
    (HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen),
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0)).score + "\t" + (System.currentTimeMillis - time) + "ms\n")

  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
  Logger.write("exchange_insert_swap_mdd\t" +
    (HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.insertGen, HillClimbing.swapGen),
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0)).score + "\t" + (System.currentTimeMillis - time) + "ms\n")

  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  time = System.currentTimeMillis
  Logger.write("exchange_swap_insert_mdd\t" +
    (HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen),
      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0)).score + "\t" + (System.currentTimeMillis - time) + "ms\n")
      
  
  Logger.close

}

object MainVndAll extends App {
  
  var optimaCounter = 0

  val optimas = Util.readOptimas
 
  Logger.open("output/logVndAllBestInsertSwap.log")
  
  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  for (i <- 0 until 125) {
    val time = System.currentTimeMillis
      val score = HillClimbing.runVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen),
      HillClimbing.genFirstSolution(), HillClimbing.selectBest, 0).score
      val deviation: Float = if (optimas(i) != 0) (100.0 * (score.toFloat - optimas(i).toFloat) / optimas(i).toFloat).toFloat else (optimas(i) - score).toFloat
      Logger.write(i + "\t" + score + "\t" + deviation + "\t" + optimas(i) + "\t"+ (System.currentTimeMillis-time) +"\n")
      print(i + "\t" + score + "\t" + deviation + "\t" + optimas(i) + "\t" + (System.currentTimeMillis-time) + "\n")
      if (optimas(i) == score) {
        optimaCounter += 1
      }
    }
  
  Logger.write(optimaCounter+" / 125\n")
  Logger.close
}

object MainPipedVnd extends App {

  Logger.open("output/logpipedvnd")

  // println(1)
  // 
  //  HillClimbing.initRandom("input/wt100.txt", 100, 125)
  //  var time : Long = System.currentTimeMillis
  //  Logger.write("exchange_insert_swap_rnd\t"+ 
  //      (HillClimbing.runPipedVnd(Array(HillClimbing.exchangeGen, HillClimbing.insertGen, HillClimbing.swapGen), 
  //      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0, false, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms\n")
  // 
  //  println(2)
  //      
  //  HillClimbing.initRandom("input/wt100.txt", 100, 125)
  //  time = System.currentTimeMillis
  //   Logger.write("exchange_swap_insert_rnd\t"+ 
  //       (HillClimbing.runPipedVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen ), 
  //      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0, false, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms\n")
  //      
  //  println(3)
  //      
  //  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  //  time = System.currentTimeMillis
  //  Logger.write("exchange_insert_swap_mdd\t"+ 
  //      (HillClimbing.runPipedVnd(Array(HillClimbing.exchangeGen, HillClimbing.insertGen, HillClimbing.swapGen), 
  //      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0, false, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms\n")
  // 
  //  println(4)
  //      
  //  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  //  time = System.currentTimeMillis
  //   Logger.write("exchange_swap_insert_mdd\t"+ 
  //       (HillClimbing.runPipedVnd(Array(HillClimbing.exchangeGen, HillClimbing.swapGen, HillClimbing.insertGen ), 
  //      HillClimbing.genFirstSolution(), HillClimbing.selectFirst, 0, false, 0)).score +"\t"+ (System.currentTimeMillis - time) + "ms\n")
  //  
  //  println(5)

  //Best Config find by score/time
  HillClimbing.initMdd("input/wt100.txt", 100, 125)
  val time = System.currentTimeMillis()
  Logger.write("insert_swap_best_mdd\t" +
    (HillClimbing.runPipedVnd(Array(HillClimbing.insertGen, HillClimbing.swapGen),
      HillClimbing.genFirstSolution(), HillClimbing.selectBest, 0, false, 0)).score + "\t" + (System.currentTimeMillis() - time) + "ms\n")

  Logger.close

}

