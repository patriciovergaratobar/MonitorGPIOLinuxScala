package com.pvergara.scala.monitorGPIOLinux

import java.util.Calendar

import com.pvergara.scala.monitorGPIOLinux.ImplicitCallBack.TraitCallBack
import com.pvergara.scala.monitorGPIOLinux.model.{Broker, Gpio}
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.eclipse.paho.client.mqttv3.{IMqttDeliveryToken, MqttCallback, MqttClient, MqttException, MqttMessage}
import play.api.libs.json.{Json, Writes}

/**
  * Created by pvergara on 14-12-17.
  */
class MqttSubscriber extends App {

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
      val mattConnexion = buildMqttClient(broker)
      val topicMqtt =  mattConnexion.getTopic(broker.topicPublish)
      val msg = new MqttMessage(m.getBytes("utf-8"))
      topicMqtt.publish(msg)
    } catch {
      case e: MqttException => println("Exception : " + e)
    }
  }

  def mqttListener(implicit broker: Broker) : Unit = {
    val mattConnexion = buildMqttClient(broker)
    mattConnexion.subscribe(broker.topicReception)
    val cbListener = new MqttCallback {
      override def messageArrived(topic: String, message: MqttMessage): Unit = {
            println(s"Topic : $topic, Message : $message")
        def callback[T](m: T)(implicit cb: TraitCallBack[T]): Unit = cb.execute(m)
        callback(message)
      }
      override def connectionLost(cause: Throwable): Unit = println(s"Connection Lost by $cause")
      override def deliveryComplete(token: IMqttDeliveryToken): Unit = println(s"deliveryComplete by $token")
    }
    mattConnexion.setCallback(cbListener)
  }

  def buildMqttClient(broker: Broker): MqttClient = {
    val persistence = new MemoryPersistence
    var clientId = MqttClient.generateClientId
    val clientMqtt = new MqttClient(broker.hostBroker, s"$clientId-${broker.identifier}", persistence)
    clientMqtt.connect
    clientMqtt
  }

}
