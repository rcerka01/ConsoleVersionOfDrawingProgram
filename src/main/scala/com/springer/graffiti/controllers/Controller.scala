package com.springer.graffiti.controllers

import com.springer.graffiti.Input
import com.springer.graffiti.services.{CommandValidationServices, CanvasServices}
import com.springer.graffiti.view.CanvasOutput

/**
  * Created by raitis on 09/06/2016.
  */
sealed trait Controller

object CommandController extends Controller {


  def createCanvas(command: Array[String]) = {
    if (CommandValidationServices.isValidCommandCreateCanvas(command)) {
      val canvas = CanvasServices.createCanvasService(command)
      CanvasOutput.printCanvas(canvas.exportToPrint)
      Input.run("Enter a new command or 'Q' to exit:")
    } else {
      Input.run("Incorrect 'Create Canvas' command (example: C 20 4 for canvas 20x4). Try again or enter 'Q' to exit:")
    }
  }

  def addLine(command: Array[String]) = {
    if (CommandValidationServices.isValidCommandAddLine(command)) {
      val canvas = CanvasServices.addLineService(command)
      CanvasOutput.printCanvas(canvas.exportToPrint)
      Input.run("Enter a new command or 'Q' to exit:")
    } else {
      Input.run("Incorrect 'Draw Line' command or canvas is not created (example: L 1 2 6 2 for canvas 20x4). Try again or enter 'Q' to exit:")
    }
  }


  def addRectangle(command: Array[String]) = {
    if (CommandValidationServices.isValidCommandAddRectangle(command)) {
      val canvas = CanvasServices.addRectangleService(command)
      CanvasOutput.printCanvas(canvas.exportToPrint)
      Input.run("Enter a new command or 'Q' to exit:")
    } else {
      Input.run("Incorrect 'Draw Rectangle' command or canvas is not created (example: R 16 1 20 3 for canvas 20x4). Try again or enter 'Q' to exit:")
    }
  }

  def addFill(command: Array[String]) = {
    if (CommandValidationServices.isValidCommandAddFill(command)) {
      val canvas = CanvasServices.addFillService(command)
      CanvasOutput.printCanvas(canvas.exportToPrint)
      Input.run("Enter a new command or 'Q' to exit:")
    } else {
      Input.run("Incorrect 'Bucket' command or canvas is not created. (example: B 10 3 o for canvas 20x4). Try again or enter 'Q' to exit:")
    }
  }

}
