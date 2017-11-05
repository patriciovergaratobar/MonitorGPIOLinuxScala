package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model._
import com.pvergara.scala.monitorGPIOLinux.utils.GpioUtil._

import scala.concurrent.duration.Duration

class MonitorGPIO(implicit val timeout: Duration) {


  implicit def callbackGpio(status: Status[Boolean], gpioId: String) = println(s"The new status of $gpioId is $status")

  def startMonitor(gpios: List[Gpio]): Unit = {
    while (true) {
      for {
        gpio <- gpios
        _ <- changeStatus(lastState(gpio, currentStatus(gpio)))
      } yield ()
      Thread.sleep(timeout.toMillis)
    }
  }

  def lastState(gpio: Gpio, cs: String): Gpio = {
    val result = gpio.status match {
      case On(_) if cs.equals("0") => {
        //apagar
        gpio.copy(status = Off(false))
      }
      case Off(_) if cs.equals("1") => {
        //prender
        gpio.copy(status = On(true))
      }
      case _ => {
        //apagado y apagar o prendido y prender = no se hace nada
        println(s"status ${gpio.status} and currentStatus $cs")
        gpio
      }
    }
    result
  }


  def changeStatus(gpio: Gpio) = {
    callbackGpio(gpio.status, gpio.pin)
    gpio.pin
  }

  /**
    * Method that obtains the path of the value of a gpio.
    */
  implicit def pathSystemGpio: (Gpio => String) =
    gpio => {
      PATH_GPIO.concat("gpio").concat(gpio.pin).concat("/value")
    }

  /**
    * Method that gets the value of the gpio.
    */
  def currentStatus(gpio: Gpio)(implicit f: Gpio => String): String = io.Source.fromFile(f(gpio)).getLines.mkString.trim()

}