package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.{Gpio}
import com.pvergara.scala.monitorGPIOLinux.AppGPIO.broker
import org.eclipse.paho.client.mqttv3.{MqttMessage}


/**
  * Created by pvergara on 31-12-17.
  */
object ImplicitCallBack {

  trait TraitCallBack[T] {
    def printValue(gpio: T): Unit
    def execute(gpio: T): Unit
  }

  implicit object CallbackGpio extends TraitCallBack[Gpio] {
    def printValue(gpio: Gpio): Unit = println(s"The new status = $gpio")
    def execute(gpio: Gpio): Unit = {
      val mqttSubscriber = new MqttSubscriber
      mqttSubscriber.publisher(gpio)
    }
  }

  implicit object CallbackMqttMessage extends TraitCallBack[MqttMessage] {
    def printValue(msg: MqttMessage): Unit = println(s"Message = $msg")
    def execute(msg: MqttMessage): Unit = { println(s"Execute Message = $msg") }
  }

}
