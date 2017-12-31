package com.pvergara.scala.monitorGPIOLinux.utils

import com.pvergara.scala.monitorGPIOLinux.model.Broker
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
  * Created by pvergara on 11-11-17.
  */
class BrokerConnection {

  def getMqttConection(broker: Broker) : MqttClient = {
    val persistence = new MemoryPersistence
    val clientMqtt = new MqttClient(broker.hostBroker, broker.identifier, persistence)
    clientMqtt.connect
    clientMqtt
  }

}
