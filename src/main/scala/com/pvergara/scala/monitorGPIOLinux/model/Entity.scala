package com.pvergara.scala.monitorGPIOLinux.model

trait Status[A]

case class On[A](state: A) extends Status[A] {
  override def toString: String = "on"
}

case class Off[A](state: A) extends Status[A] {
  override def toString: String = "off"
}

case class Gpio(val pin: String, val status: Status[Boolean])  {
  override def toString: String = s"GpioIn pin = $pin is in status ${status}"
}

case class Broker(hostBroker: String, identifier: String, topicPublish: String, topicReception: String) {
  override def toString: String = s"Broker $hostBroker and my identifier $identifier"
}
