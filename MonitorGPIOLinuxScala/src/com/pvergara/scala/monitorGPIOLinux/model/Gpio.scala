package com.pvergara.scala.monitorGPIOLinux.model

case class Gpio(pin: String, statusDefauld: Boolean, callback: Boolean => Unit) {

  var status = statusDefauld

  /**
   * Function change status of gpio.
   */
  def changeStatus(newStatus: Boolean): Unit = {
    status = newStatus;
    //Call the function callBack
    callback(status)
  }
}