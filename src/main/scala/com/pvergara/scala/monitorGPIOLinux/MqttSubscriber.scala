package com.pvergara.scala.monitorGPIOLinux

import java.util.Calendar

import com.pvergara.scala.monitorGPIOLinux.ImplicitCallBack.TraitCallBack
import com.pvergara.scala.monitorGPIOLinux.model.{Broker, Gpio}
import com.pvergara.scala.monitorGPIOLinux.utils.BrokerConnection
import org.eclipse.paho.client.mqttv3.{MqttClient, _}
import play.api.libs.json.{Json, Writes}

/**
  * Created by pvergara on 14-12-17.
  */
class MqttSubscriber {

  def publisher(data: Gpio)(implicit broker: Broker) = {
    try {
      implicit val locationWrites = new Writes[Gpio] {
        def writes(gpio: Gpio) = Json.obj(
          "pin" -> gpio.pin,
          "status" -> gpio.status.toString,
          "dateEvent" -> Calendar.getInstance().getTimeInMillis
        )
      }
      val m = Json.toJson(data).toString
      val topicMqtt = buildMqttClient(broker).getTopic("cl/MonitorGPIO")
      val msg = new MqttMessage(m.getBytes("utf-8"))
      topicMqtt.publish(msg)
    } catch {
      case e: MqttException => println("Exception : " + e)
    }
  }

  def mqttListener(topics: Array[String])(implicit broker: Broker) : Unit = {
    //subscriber list topic
    val mqttConexion = buildMqttClient(broker)
    mqttConexion.subscribe(topics)
    val cbListener = new MqttCallback {
      override def messageArrived(topic: String, message: MqttMessage): Unit = {
            println(s"Topic : $topic, Message : $message")
            //callback.execute(message)
      }
      override def connectionLost(cause: Throwable): Unit = println(s"Connection Lost by $cause")
      override def deliveryComplete(token: IMqttDeliveryToken): Unit = {}
    }
    mqttConexion.setCallback(cbListener)

  }

  def buildMqttClient(broker: Broker): MqttClient = {
    val conn = new BrokerConnection
    conn.getMqttConection(broker)
  }

}
