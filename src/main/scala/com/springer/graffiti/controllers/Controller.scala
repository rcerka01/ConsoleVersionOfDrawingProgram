package com.springer.graffiti.controllers

import com.springer.graffiti.Input
import com.springer.graffiti.model.Error
import com.springer.graffiti.services.{CanvasServices, CommandValidationServices}
import com.springer.graffiti.view.CanvasOutput

/**
  * Created by raitis on 09/06/2016.
  */
sealed trait Controller

object CommandController extends Controller {


  def createCanvas(command: Array[String]) = {
    implicit val error: Error = CommandValidationServices.isValidCommandCreateCanvas(command)
    if (error == null) {
      val canvas = CanvasServices.createCanvasService(command)
      CanvasOutput.printCanvas(canvas.exportToPrint)
    }
    Input.run("Enter a new command or 'Q' to exit:")
  }

  def addLine(command: Array[String]) = {
    implicit val error: Error = CommandValidationServices.isValidCommandAddLine(command)
    if (error == null) {
      val canvas = CanvasServices.addElementService(command)
      CanvasOutput.printCanvas(canvas.exportToPrint)
    }
    Input.run("Enter a new command or 'Q' to exit:")
  }

  def addRectangle(command: Array[String]) = {
    implicit val error: Error = CommandValidationServices.isValidCommandAddRectangle(command)
    if (error == null) {
      val canvas = CanvasServices.addElementService(command)
      CanvasOutput.printCanvas(canvas.exportToPrint)
    }
    Input.run("Enter a new command or 'Q' to exit:")
  }

  def addFill(command: Array[String]) = {
    implicit val error: Error = CommandValidationServices.isValidCommandAddFill(command)
    if (error == null) {
      val canvas = CanvasServices.addElementService(command)
      CanvasOutput.printCanvas(canvas.exportToPrint)
    }
    Input.run("Enter a new command or 'Q' to exit:")
  }

 def terminate(message: String): Unit = CanvasOutput.printMessage(message)

}