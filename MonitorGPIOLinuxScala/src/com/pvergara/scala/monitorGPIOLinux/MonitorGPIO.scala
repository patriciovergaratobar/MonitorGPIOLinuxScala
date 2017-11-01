package com.pvergara.scala.monitorGPIOLinux

import scala.collection._
import scala.collection.convert.decorateAsScala._
import java.util.concurrent.ConcurrentHashMap
import com.pvergara.scala.monitorGPIOLinux.model.Gpio
import scala.reflect.io.Path
import scala.reflect.io.File

class MonitorGPIO {

  val lastStates: concurrent.Map[String, Gpio] = new ConcurrentHashMap().asScala

  val PATH_GPIO = "/systest/class/gpio/"

  def startMonitor(gpios: List[Gpio]): Unit = {
    while (true) {
      gpios.foreach(checkingChange)
      Thread.sleep(10L)
    }
  }

  /**
   * Method that checks if there are changes of the gpio.
   */
  def checkingChange(gpio: Gpio): Unit = {
    val valorNuevo = currentStatus(pathSystemGpio(gpio))
    var newStatus = false
    if (valorNuevo.contains("1")) {
      newStatus = true;
    }
    val lastValue = lastStates.get(gpio.pin)
    if (lastStates.contains(gpio.pin).!=(true) || lastValue.get.status.equals(newStatus).!=(true)) {
      //Thread handles the callbacks of the gpio changes.
      val threadCallback = new Thread {
        override def run {
          //internally call function callback of gpio
          gpio.changeStatus(newStatus)
          lastStates.put(gpio.pin, gpio)
        }
      }
      threadCallback.start()
    }

  }

  /**
   * Method that obtains the path of the value of a gpio.
   */
  def pathSystemGpio(gpio: Gpio): String = PATH_GPIO.concat("gpio").concat(gpio.pin).concat("/value")

  /**
   * Method that gets the value of the gpio.
   */
  def currentStatus(pathGpio: String): String = io.Source.fromFile(pathGpio).getLines.mkString.trim()

}