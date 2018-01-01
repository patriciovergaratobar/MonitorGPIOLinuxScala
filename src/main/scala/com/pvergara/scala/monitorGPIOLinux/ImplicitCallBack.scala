package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.{Broker, Gpio}
import org.eclipse.paho.client.mqttv3.{MqttClient, _}

/**
  * Created by pvergara on 31-12-17.
  */
object ImplicitCallBack {

  implicit def broker: Broker = Broker("tcp://localhost:1883", "MonitorGPIOLinuxScala")

  trait TraitCallBack[T] {
    def printValue(gpio: T): Unit
    def execute(gpio: T): Unit
  }

  implicit object CallbackGpio extends TraitCallBack[Gpio] {
    def printValue(gpio: Gpio): Unit = println(s" Implicit - The new status = $gpio")
    def execute(gpio: Gpio): Unit = {
      val subscriber = new MqttSubscriber
      subscriber.publisher(gpio)
    }
  }

  implicit object CallbackMqttMessage extends TraitCallBack[MqttMessage] {
    def printValue(msg: MqttMessage): Unit = println(s" Implicit - Message = $msg")
    def execute(msg: MqttMessage): Unit = {  }
  }

}
