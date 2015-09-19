package main

import scala.collection.mutable.ListBuffer
import data._
import solver._

object MainHillClimbing extends App {

  def run(): Unit = {
    Logger.open("output/" + name + ".log")

    val values: ListBuffer[(Int, Long)] = new ListBuffer[(Int, Long)]()
    for (i <- 0 until nbRuns) {
      val time : Long =  System.currentTimeMillis()
      println(i + " run")
      initFunc(pathname)
      values += ( (HillClimbing.run(HillClimbing.genFirstSolution(), genFunc, selectFunc),
          System.currentTimeMillis() - time) )
    }

    Logger.write("\tscores\ttimes\t\n")

    var avgTime: Long = 0
    var avgScore: Int = 0

    values.foreach {x => 
      avgTime += x._2      
      avgScore += x._1
    }
    
    Logger.write("0\t"+avgScore/nbRuns+"\t"+avgTime/nbRuns+"\n")

    Logger.close()
  }

  def runAll(): Unit = {
    Logger.open("output/" + name + ".log")

    val values: ListBuffer[(ListBuffer[Int], ListBuffer[Long])] = new ListBuffer[(ListBuffer[Int], ListBuffer[Long])]()
    for (i <- 0 until nbRuns) {
      println(i + " run")
      initFunc(pathname)
      HillClimbing.runAllInstances(genFunc, selectFunc)
      values += ((HillClimbing.scores, HillClimbing.times))
    }

    Logger.write("\tscores\ttimes\t\n")

    var avgTime: Long = 0
    var avgScore: Int = 0

    var str: String = ""

    for (i <- 0 until values(0)._1.length) {
      str += i + "\t"
      for (j <- 0 until values.length) {
        avgScore += values(j)._1(i)
        avgTime += values(j)._2(i)
      }
      str += avgScore / nbRuns + "\t" + avgTime / nbRuns + "\n"
      avgScore = 0
      avgTime = 0
    }

    Logger.write(str)

    Logger.close()
  }

  val options = HillClimbingOptions.options(Map(), args.toList)

  var selectFunc: ((((Solution) => Solution), Solution) => (Solution)) = _
  var initFunc: (String) => Unit = _
  var genFunc: (Solution) => Solution = _
  var nbRuns: Int = 30
  var pathname: String = ""

  var name: String = ""

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

  MainHillClimbing.run()

}

