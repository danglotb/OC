package main

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
  
  for (i <- 0 until names.length) {
    names(i) = names(i).substring(0, names(i).length-"Score".length)
    println(names(i))
  }
  
}

object MainPlot extends App {
  
  val source = scala.io.Source.fromFile("output/log")
  val iterator = source getLines

  //trash the first lines
  iterator next
  
  val current = iterator next
  
  val values = Array[Int](current.filter{x => x == '\t'}.length)
  
  val sc = new java.util.Scanner(current)
  
  sc.useDelimiter("\t")
  
  val listScore = scala.collection.mutable.ListBuffer[Int]()
  val listIndex = scala.collection.mutable.ListBuffer[Int]()
  
  var index = 0
  
  val title = sc next
  
  while (sc hasNext) {
    listScore += (sc next).toInt
    listIndex += index
    index += 1
  }
  
  val listSortedIndex = listIndex.sortBy { x => listScore(x) }
  
  var str = ""
  
  listIndex.foreach { x =>  
    str += x + "\t" + listScore(listSortedIndex(x)) + "\t" + listSortedIndex(x)+ "\n"
  }
  
  Some(new java.io.PrintWriter("sortedInstances"+title+".dat")).foreach { p => p.write(str); p.close }
  
  source close
}