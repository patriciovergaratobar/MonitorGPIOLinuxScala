# GPIO Monitor for Linux whith Scala

This project aims to be an alternative to read gpios with Scala. The project was tested on a Raspberry Pi and an imx.6.

## MonitorGPIOLinuxScala
This project takes charge of monitoring the change in gpio and send state of gpio to broker ActiveMQ when gpio change status.

Create these paths
/systest/class/gpio/gpio57/value
/systest/class/gpio/gpio82/value

and only contain a character that is 0 or 1

For humans gpio = General Purpose Input/Outpu
