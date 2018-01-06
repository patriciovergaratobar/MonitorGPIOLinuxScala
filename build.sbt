name := "MonitorGPIOLinuxScala"

version := "1.0"

scalaVersion := "2.12.4"



lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "com.pvergara.scala.monitorGPIOLinux",
  scalaVersion := "2.12.4",
  test in assembly := {}
)

libraryDependencies ++= Seq(
  "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.0.2",
  "com.typesafe.play" %% "play-json" % "2.6.7",
  "com.typesafe" % "config" % "1.3.1"
)

resolvers += "MQTT Repository" at "https://repo.eclipse.org/content/repositories/paho-releases/"



lazy val app = (project in file("app")).
  settings(commonSettings: _*).
  settings(
    mainClass in Compile := Some("com.pvergara.scala.monitorGPIOLinux.AppGPIO"),
    mainClass in assembly := Some("com.pvergara.scala.monitorGPIOLinux.AppGPIO")
  )

