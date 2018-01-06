package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model._

import com.pvergara.scala.monitorGPIOLinux.utils.GpioUtil._

import com.pvergara.scala.monitorGPIOLinux.ImplicitCallBack.TraitCallBack

import scala.concurrent.duration.Duration

class MonitorGPIO(implicit val timeout: Duration, implicit val callback: TraitCallBack[Gpio]) {

  def startMonitor(gpios: Map[String, Gpio]): Unit = {
    var currentGpios = gpios
    val thread = new Thread {
      override def run: Unit = {
        while (true) {
          var changed = for {
            gpio <- currentGpios.values
            ls <- lastState(gpio, currentStatus(gpio) )
          } yield (changeStatus(ls))
          changed.map(gpio => currentGpios = currentGpios.updated(gpio.pin, gpio))
          Thread.sleep(timeout.toMillis)
        }
      }
    }
    thread.start
  }

  /**
    * This method returns an optional value, in case the gpio doesnt have changes , the return is None, and if the value
    * has changes it returns Some
    *
    * @param gpio
    * @param cs
    * @return
    */
  def lastState(gpio: Gpio, cs: String): Option[Gpio] = {
    val result = gpio.status match {
      case On(_) if cs.equals("0") => {
        //apagar
        Some(gpio.copy(status = Off(false)))
      }
      case Off(_) if cs.equals("1") => {
        //prender
        Some(gpio.copy(status = On(true)))
      }
      case _ => {
        //apagado y apagar o prendido y prender = no se hace nada
        //println(s"The value is the same for ${gpio.pin} - Status ${gpio.status} and currentStatus $cs")
        None
      }
    }
    result
  }

  /**
    * When is Some, execute the callback
    * Wnen None, only log
    *
    * @param gpio
    */
  def changeStatus(gpio: Gpio): Gpio = {
    callback.execute(gpio)
    gpio
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
