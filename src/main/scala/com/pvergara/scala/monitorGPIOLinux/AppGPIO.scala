package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.{Broker, Gpio, Off}
import com.pvergara.scala.monitorGPIOLinux.utils.GpioUtil
import scala.concurrent.duration._
import com.typesafe.config._

/**
  * Created by pvergara on 06-12-17.
  */
object AppGPIO {

  implicit def timeout: Duration = 10 millisecond

  val conf = ConfigFactory.load("application.conf")

  implicit def broker: Broker = Broker(conf.getString("broker.host"), conf.getString("broker.identifier"))

  def main(args: Array[String]): Unit = {

    val monitorGPIO = new MonitorGPIO()
     println("Starting MonitorGPIO, every 10 millisecond will evaluate gpio`s states (implicit value)")
    val gpio57 = Gpio(GpioUtil.GPIO_57, Off(false))
    val gpio82 = Gpio(GpioUtil.GPIO_82, Off(false))

    println(s"MonitorGPIO $gpio57 ready")
    println(s"MonitorGPIO $gpio82 ready")


    val mqttSubscriber = new MqttSubscriber
    mqttSubscriber.mqttListener(Array("cl/topicoA", "com/topicoA", "com/topicoB"))

    monitorGPIO.startMonitor(Map(gpio57.pin -> gpio57, gpio82.pin -> gpio82))



  }

}
