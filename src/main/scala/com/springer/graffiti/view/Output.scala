package com.springer.graffiti.view

/**
  * Created by raitis on 12/06/2016.
  */

import com.springer.graffiti.model.Error

sealed trait Output


object CanvasOutput extends Output {


  def printErrorMessage(error: Error): Unit = {
    val errorMessage = error.message
    println(s"Error: $errorMessage")
  }

  def printMessage(message: String): Unit = println(message)

  def printCanvas(canvasPrintable: Array[Array[Char]]): Unit = {

    for (i <- canvasPrintable.indices) {
      for (j <- canvasPrintable(i).indices) {
        print(canvasPrintable(i)(j))
      }
      println
    }

  }

}
