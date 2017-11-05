package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.{Gpio, Off}
import com.pvergara.scala.monitorGPIOLinux.utils.GpioUtil

import scala.concurrent.duration._

object ExampleGPIOIn {

  implicit def timeout: Duration = 10 seconds

  def main(args: Array[String]): Unit = {

    val monitorGPIO = new MonitorGPIO()
    println("Star/ting MonitorGPIO, every 10 seconds will evaluate gpio`s states (implicit value)")
    val gpio57 = Gpio(GpioUtil.GPIO_57, Off(false))
    val gpio82 = Gpio(GpioUtil.GPIO_82, Off(false))
    println(s"Gpio $gpio57 ready")
    println(s"Gpio MonitorGPIO $gpio82 ready")
    monitorGPIO.startMonitor(List(gpio57, gpio82))
  }


}