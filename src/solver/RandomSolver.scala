package solver

import data.InstanceReader
import data.Instance

/**
 * @author danglot
 */
class RandomSolver(nbJobs : Int, reader : InstanceReader) extends Solver(nbJobs, reader) {
  
  override def run() : Unit = {
    
    score = 0
    
    for (i <- 0 until nbJobs)
      solution += i
    
    solution = scala.util.Random.shuffle(solution)
    
    var currentTime : Int = 0
    
    val instance : Instance = reader.getInstance()
    
    for (i <- 0 until nbJobs) {
      currentTime += instance.processingTimes(this.solution(i));
      val time = Math.max( (currentTime-instance.dueDates(this.solution(i))), 0);
      this.score += instance.weights(this.solution(i)) * time;
    }
    println(this)
  }
  
}

object Main extends App {
  val reader = new InstanceReader(100, "input/wt100.txt", 125)
  val solver : RandomSolver = new RandomSolver(100,reader)
  while (reader.hasNext())
    solver.run()
}
