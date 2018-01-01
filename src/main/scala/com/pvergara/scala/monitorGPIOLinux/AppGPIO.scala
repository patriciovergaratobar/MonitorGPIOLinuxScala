package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.{Broker, Gpio, Off}
import com.pvergara.scala.monitorGPIOLinux.utils.GpioUtil

import scala.concurrent.duration._


/**
  * Created by pvergara on 06-12-17.
  */
object AppGPIO {

  implicit def timeout: Duration = 10 millisecond

  def main(args: Array[String]): Unit = {
    val monitorGPIO = new MonitorGPIO()
    println("Starting MonitorGPIO, every 10 seconds will evaluate gpio`s states (implicit value)")
    val gpio57 = Gpio(GpioUtil.GPIO_57, Off(false))
    val gpio82 = Gpio(GpioUtil.GPIO_82, Off(false))

    println(s"MonitorGPIO $gpio57 ready")
    println(s"MonitorGPIO $gpio82 ready")

    monitorGPIO.startMonitor(Map(gpio57.pin -> gpio57, gpio82.pin -> gpio82))
  }

}
