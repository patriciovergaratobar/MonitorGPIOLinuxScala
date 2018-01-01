package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.ImplicitCallBack.TraitCallBack

/**
  * Created by pvergara on 31-12-17.
  */
object CallBack {

  def printValue[T](gpio: T)(implicit cb: TraitCallBack[T]): Unit = cb.printValue(gpio)

  def execute[T](gpio: T)(implicit cb: TraitCallBack[T]): Unit = cb.execute(gpio)

}