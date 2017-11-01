package com.pvergara.scala.monitorGPIOLinux.model

//Class Model GPIO.
case class Gpio(pin: String, statusDefauld: Boolean, callback: Boolean => Unit) {
  var status = statusDefauld
  def changeStatus(newStatus: Boolean): Unit = {
    status = newStatus;
    callback(status)
  }
}