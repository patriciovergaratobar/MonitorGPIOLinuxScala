package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.{Broker, Gpio, Off}

import scala.concurrent.duration._
import com.typesafe.config._

import collection.JavaConversions._
import java.io._

/**
  * Created by pvergara on 06-12-17.
  */
object AppGPIO {

  implicit def timeout: Duration = 10 millisecond

  val env = if (System.getenv("GPIO_CONF") == null) "src/main/resources/application.conf" else System.getenv("GPIO_CONF")

  val conf = ConfigFactory.parseFile(new File(env))

  implicit def broker: Broker = Broker(
    conf.getString("broker.host"),
    conf.getString("broker.identifier"),
    conf.getString("broker.topicPublish"),
    conf.getString("broker.topicReception")
  )

  def main(args: Array[String]): Unit = {

    val gpios = (
      for (
        item <- conf.getStringList("gpios").toList
      )yield (item.toString -> new Gpio(item.toString, Off(false)))
      ).toMap

    println("Starting MonitorGPIO")
    println(s"MonitorGPIO $gpios ready")

    val monitorGPIO = new MonitorGPIO()
    monitorGPIO.startMonitor(gpios)

    val mqttSubscriber = new MqttSubscriber
    mqttSubscriber.mqttListener

  }

}
