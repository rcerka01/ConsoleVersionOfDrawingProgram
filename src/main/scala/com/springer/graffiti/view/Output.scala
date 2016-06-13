package com.springer.graffiti.view

/**
  * Created by raitis on 12/06/2016.
  */
sealed trait Output

object CanvasOutput extends Output {


  def printCanvas(canvasPrintable: Array[Array[Char]]): Unit = {

    for (i <- canvasPrintable.indices) {
      for (j <- canvasPrintable(i).indices) {
        print(canvasPrintable(i)(j))
      }
      println
    }

  }


}
