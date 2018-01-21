#!/bin/bash

echo "========================================================"
echo "INICIANDO MONITOR GPIOS"
echo "========================================================"

echo "Activando GPIO"
#Aqui se deben poner los gpio que se activaran.
#Este solo es en el caso de que el hardware sea una Raspberry pi.
#echo 21 > /sys/class/gpio/export
#echo 20 > /sys/class/gpio/export

#Se obtiene la ruta actual del jar
#Esto se hace cuando el archivo run.sh se ejecuta desde otra ruta.
rutaLocal=$(realpath -s $0 | awk -F${0##*/} '{print $FNR}')
# Se utiliza la variable de entorno para indicar ruta del 
# archivo de configuracion.
export GPIO_CONF=resources/application.conf
cd $rutaLocal
java -jar MonitorGPIOLinuxScala-assembly-1.0.jar
