package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.ImplicitCallBack.CallBackGpio

/**
  * Created by pvergara on 31-12-17.
  */
object CallBack {

  def printValue[T](gpio: T)(implicit cb: CallBackGpio[T]): Unit = cb.printValue(gpio)

  def publisherBroker[T](gpio: T)(implicit cb: CallBackGpio[T]): Unit = cb.publisherBroker(gpio)

}