#!/bin/bash

echo "========================================================"
echo "COMPILANDO MONITOR GPIOS"
echo "========================================================"

#Se Importa la configuracion
rutaLocal=$(realpath -s $0 | awk -F${0##*/} '{print $FNR}')
cd $rutaLocal
echo "Ejecutando sbt assembly"
sbt assembly
estadoCompilacion=$?
echo "El estado de la compilación es $estadoCompilacion"

echo "Copiando jar compilado en packagedApp/"
cp target/scala-2.12/MonitorGPIOLinuxScala-assembly-1.0.jar packagedApp/

