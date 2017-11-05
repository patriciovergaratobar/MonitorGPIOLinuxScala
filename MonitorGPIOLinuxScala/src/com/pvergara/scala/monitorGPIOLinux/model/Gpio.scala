package com.pvergara.scala.monitorGPIOLinux.model

trait Status[A]

case class On[A](state: A) extends Status[A] {
  override def toString: String = "on"
}

case class Off[A](state: A) extends Status[A] {
  override def toString: String = "off"
}

case class Gpio(pin: String, status: Status[Boolean]) {
  override def toString: String = s"Gpio pin =$pin is in status ${status}"
}