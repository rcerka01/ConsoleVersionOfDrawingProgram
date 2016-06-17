package com.springer.graffiti

import com.springer.graffiti.controllers.CommandController
import com.springer.graffiti.model.Error
import com.springer.graffiti.view.CanvasOutput

/**
  * Created by raitis on 08/06/2016.
  */
object Router {


  def runCommand(command: Array[String]): Unit = {

    val commandFirstParameter = command(0)

    commandFirstParameter match {

      case "C" => CommandController.createCanvas(command)

      case "L" => CommandController.addLine(command)

      case "R" => CommandController.addRectangle(command)

      case "B" => CommandController.addFill(command)

      case "Q" => CommandController.terminate("Goodbye!")

      case _   => CanvasOutput.printErrorMessage(Error("Unknown error, system stop"))

    }

  }


}
