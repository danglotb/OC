package main

import data.Util

object MainPlotDeviationILS extends App {

  val source = scala.io.Source.fromFile("output/ils.log")

  val iterator = source getLines

  var str = ""

  while (iterator hasNext) {

    val sc = new java.util.Scanner(iterator next)

    sc useDelimiter ("\t")

    str += (sc next) + "\t"

    //thrash the score
    (sc next)
    (sc next)
    (sc next)

    str += (sc next) + "\n"
  }
  
  Util.write("data/ilsdeviation.dat", str)

  str = "set style data histogram\nset style histogram cluster gap 1\nset style fill solid border -1\nset xtic rotate by -45\nplot \'../data/ilsdeviation.dat\' using 2:xtic(1) title \'Deviation\'"
  
  Util.write("plot/ilsdeviation.plt", str)  
  
  (source close)
  
}

object MainPlotDeviation extends App {

  val source = scala.io.Source.fromFile("output/besteddinsertdeviation.log")

  val iterator = source getLines

  var str = ""

  while (iterator hasNext) {

    val sc = new java.util.Scanner(iterator next)

    sc useDelimiter ("\t")

    str += (sc next) + "\t"

    //thrash the score
    sc next

    str += (sc next) + "\n"
  }
  
  Util.write("data/beedinsertdeviation.dat", str)

  str = "set style data histogram\nset style histogram cluster gap 1\nset style fill solid border -1\nset xtic rotate by -45\nplot \'../data/beedinsertdeviation.dat\' using 2:xtic(1) title \'Deviation\'"

   Util.write("plot/beedinsertdeviation.plt", str)  
  
  (source close)
  
}

object MainGetBest extends App {
  val source = scala.io.Source.fromFile("output/log")
  val iterator = source getLines

  //trash the first lines
  iterator next

  val scores = scala.collection.mutable.ListBuffer[Int]()
  val times = scala.collection.mutable.ListBuffer[Int]()
  val names = scala.collection.mutable.ListBuffer[String]()

  while (iterator hasNext) {

    var current = iterator next
    var sc = new java.util.Scanner(current)
    sc useDelimiter "\t"

    names += (sc next)
    scores += (sc next).toInt

    current = (iterator next)
    sc = new java.util.Scanner(current)
    sc useDelimiter "\t"

    //trash the label
    sc.next
    times += (sc next).toInt
  }

  var str = ""

  for (i <- 0 until names.length)
    str += names(i).substring(0, names(i).length - "Score".length) + "\t" +
      scores(i) + "\t" + times(i) + "\t" + i + "\t" + (scores(i) / times(i)) + "\n"

  Util.write("data/1rstInstance.dat", str)

  str = "set style data histogram\nset style histogram cluster gap 1\nset style fill solid border -1\nset xtic rotate by -45\nplot \'../data/1rstInstance.dat\' using 5:xtic(1) title \'Score on Time(S/T)\'"

  Util.write("plot/1rstInstanceScoreOnTime.plt", str)

  //  str = "set style data histogram\nset style histogram cluster gap 1\n
  //set style fill solid border -1\nset xtic rotate by -45\nplot \'../data/1rstInstance.dat\' using 2:xtic(1) title \'Score\'"
  //  
  //  Util.write("plot/1rstInstanceScore.plt", str)
  //  
  //  str = "set style data histogram\nset style histogram cluster gap 1\nset style fill solid border -1\nset xtic rotate by -45\nplot \'../data/1rstInstance.dat\' using 3:xtic(1) title \'Time(ms)\'"
  //  
  //  Util.write("plot/1rstInstanceTime.plt", str)

  source close
}

object MainPlot extends App {

  val source = scala.io.Source.fromFile("output/log")
  val iterator = source getLines

  val listScore = scala.collection.mutable.ListBuffer[Int]()
  val listTime = scala.collection.mutable.ListBuffer[Int]()
  val listIndex = scala.collection.mutable.ListBuffer[Int]()

  //trash the first lines
  iterator next

  var current = iterator next

  while (!current.startsWith("besteddinsert")) {
    current = iterator next
  }

  var sc = new java.util.Scanner(current)
  sc.useDelimiter("\t")
  var index = 0

  //trash the label
  sc.next
  while (sc.hasNext) {
    listScore += (sc next).toInt
    listIndex += index
    index += 1
  }

  current = iterator next

  sc = new java.util.Scanner(current)
  sc.useDelimiter("\t")

  //trash the label
  sc.next
  while (sc.hasNext) {
    listTime += (sc next).toInt
  }

  //  var str = ""
  //  
  //  listIndex.foreach {i => 
  //    str += i+"\t"+listScore(i)+"\t"+listTime(i)+"\n"  
  //  }
  //  Util.write("data/besteddinsert.dat", str)
  //  
  //  str = "set style data histogram\nset style histogram cluster gap 1\nset style fill solid border -1\nset xtic rotate by -45\nplot \'../data/besteddinsert.dat\' using 2:xtic(1) title \'Score\'"
  //  
  //  Util.write("plot/besteddinsertScore.plt", str)

  val listScoreEasy = listScore.filter { x => x < 100000 }
  val listScoreMedium = listScore.filter { x => (100000 <= x && x < 500000) }
  val listScoreHard = listScore.filter { x => (500000 <= x) }

  var str = ""

  for (i <- 0 until listScoreEasy.length) {
    str += i + "\t" + listScore.indexOf(listScoreEasy(i)) + "\n"
  }

  Util.write("data/besteddinserteasy.dat", str)

  str = ""

  for (i <- 0 until listScoreMedium.length) {
    str += i + "\t" + listScore.indexOf(listScoreMedium(i)) + "\n"
  }

  Util.write("data/besteddinsertmedium.dat", str)

  str = ""

  for (i <- 0 until listScoreHard.length) {
    str += i + "\t" + listScore.indexOf(listScoreHard(i)) + "\n"
  }

  Util.write("data/besteddinserthard.dat", str)

  //  
  //
  //  str = "set style data histogram\nset style histogram cluster gap 1\nset style fill solid border -1\nset xtic rotate by -45\nplot \'../data/besteddinserteasy.dat\' using 2:xtic(1) title \'Score Easy\'"
  //  
  //  Util.write("plot/besteddinserteasy.plt", str)
  //  
  //   str = "set style data histogram\nset style histogram cluster gap 1\nset style fill solid border -1\nset xtic rotate by -45\nplot \'../data/besteddinsertmedium.dat\' using 2:xtic(1) title \'Score Medium\'"
  //  
  //  Util.write("plot/besteddinsertmedium.plt", str)
  //  
  //   str = "set style data histogram\nset style histogram cluster gap 1\nset style fill solid border -1\nset xtic rotate by -45\nplot \'../data/besteddinserthard.dat\' using 2:xtic(1) title \'Score Hard\'"
  //  
  //  Util.write("plot/besteddinserthard.plt", str)

  source close
}