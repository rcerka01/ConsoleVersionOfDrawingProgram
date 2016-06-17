package com.springer.graffiti

import com.springer.graffiti.model.Error
import com.springer.graffiti.services.CommandValidationServices
import com.springer.graffiti.view.CanvasOutput

import scala.io.StdIn._

/**
  * Created by raitis on 09/06/2016.
  */
object Input extends App {


  def run(title: String)(implicit error: Error): Unit = {

    if (error != null) {
      CanvasOutput.printErrorMessage(error)
      run("Enter a new command or 'Q' to exit:")(null)
    }

    else {
      CanvasOutput.printMessage(title)

      val command: Array[String] = readLine().split(" ")

      implicit val isValidCommand: Error = CommandValidationServices.isValidCommand(command)

      if (isValidCommand != null) {
        run("Enter a new command or 'Q' to exit:")(isValidCommand)
      }
      else {
        Router.runCommand(command)
      }
    }

  }


  // start
  run("Enter a command:")(null)

}