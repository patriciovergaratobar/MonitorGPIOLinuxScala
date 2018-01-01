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
  "org.eclipse.paho" % "mqtt-client" % "0.4.0",
  "com.typesafe.play" %% "play-json" % "2.6.7"
)

resolvers += "MQTT Repository" at "https://repo.eclipse.org/content/repositories/paho-releases/"



lazy val app = (project in file("app")).
  settings(commonSettings: _*).
  settings(
    mainClass in Compile := Some("com.pvergara.scala.monitorGPIOLinux.AppGPIO"),
    mainClass in assembly := Some("com.pvergara.scala.monitorGPIOLinux.AppGPIO")
  )

