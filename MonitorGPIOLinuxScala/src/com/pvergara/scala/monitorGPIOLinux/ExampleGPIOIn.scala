package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.Gpio
import com.pvergara.scala.monitorGPIOLinux.utils.GpioUtil

object ExampleGPIOIn {

  def main(args: Array[String]): Unit = {

    val monitorGPIO = new MonitorGPIO();

    //val gpios: List[Gpio] = List() //no is necessary ?

    def callbackGpio57(status: Boolean): Unit = println("The new status of gpio57 is " + status)
    val gpio57 = new Gpio(GpioUtil.GPIO_57, false, callbackGpio57);

    def callbackGpio82(status: Boolean): Unit = println("The new status of gpio82 is " + status)
    val gpio82 = new Gpio(GpioUtil.GPIO_82, false, callbackGpio82)

    monitorGPIO.startMonitor(List(gpio57, gpio82))

  }

}