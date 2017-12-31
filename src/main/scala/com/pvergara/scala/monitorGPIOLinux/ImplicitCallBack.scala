package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.Gpio

/**
  * Created by pvergara on 31-12-17.
  */
object ImplicitCallBack {

  trait CallBackGpio[T] {
    def printValue(gpio: T): Unit
    def publisherBroker(gpio: T): Unit
  }

  implicit object CallbackGpioIn extends CallBackGpio[Gpio] {
    def printValue(gpio: Gpio): Unit = println(s" Implicit - The new status = $gpio")
    def publisherBroker(gpio: Gpio): Unit = {/*TODO: code send mqtt to broker*/}
  }


}
