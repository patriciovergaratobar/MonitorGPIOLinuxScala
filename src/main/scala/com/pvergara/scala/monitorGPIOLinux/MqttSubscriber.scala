package com.pvergara.scala.monitorGPIOLinux

import com.pvergara.scala.monitorGPIOLinux.model.Broker
import com.pvergara.scala.monitorGPIOLinux.utils.BrokerConnection
import org.eclipse.paho.client.mqttv3.{MqttClient, _}

/**
  * Created by pvergara on 14-12-17.
  */
class MqttSubscriber {

  def publisher(broker: Broker)  =  (m: String) => {
    try {
      val topicMqtt = buildMqttClient(broker).getTopic("cl/topicoA")
      val msg = new MqttMessage(m.getBytes("utf-8"))
      topicMqtt.publish(msg)
    } catch {
      case e: MqttException => println("Exception : " + e)
    }
  }

  def buildMqttClient(broker: Broker): MqttClient = {
    val conn = new BrokerConnection
    conn.getMqttConection(broker)
  }

/*
  def openConection(broker: Broker): Unit = {

    //val broker = new Broker("tcp://localhost:1883", "SubscriptorEventScala")

    val conn = new BrokerConnections

    val mqttConexion: MqttClient = conn.getMqttConection(broker)

    val topics = Array("cl/topicoA", "com/topicoA", "com/topicoB")

    //mqttPublisher(mqttConexion, "publish this msg :)")

  }

  def mqttPublisher(mqttConexion: MqttClient, messageSend: String): Unit = {

    try {
      val topicMqtt = mqttConexion.getTopic("cl/topicoA")
      val msg = new MqttMessage(messageSend.getBytes("utf-8"))
      topicMqtt.publish(msg)
    } catch {
      case e: MqttException => println("Exception : " + e)
    }
  }*/

}
