# Multiplicación de Matrices con MPI en Java

## Descripción
Este proyecto implementa la multiplicación paralela de dos matrices utilizando la biblioteca MPI-Java (MPJ Express). Se centra en los conceptos de arquitectura de memoria compartida, arquitectura de memoria distribuida y paso de mensajes.

## Instrucciones para Compilar y Ejecutar

### Requisitos
- En mi caso use Java 21
- MPJ Express

### Configuración del Entorno

1. **Instalar MPJ Express**:
   - Descarga y extrae MPJ Express desde [MPJ Express](https://sourceforge.net/projects/mpjexpress/files/releases/).
   - Extrae el archivo comprimido
   - Configura las variables de entorno:
     - Primero crea la variable `MPJ_HOME`: Con la ruta al directorio donde extrajiste MPJ Express como valor (por ejemplo donde lo hice yo: `C:\mpj-v0_44\mpj-v0_44`).
     - Añade `%MPJ_HOME%\bin` a la variable de entorno `Path`.
     - Crea una variable `CLASSPATH` con el valor `%MPJ_HOME%\lib\mpj.jar`.

2. **Verificar que se instalo correctamente**:
   - Abre una nueva ventana de comandos y ejecuta los siguientes comandos para verificar las variables de entorno:
     ```sh
     echo %MPJ_HOME%
     echo %CLASSPATH%
     echo %PATH%
     ```
   - Si no ves ningun error y se imprimen los directorios pues esta todo correcto y puedes proceder a compilar el programa.  

### Compilar

1. **Clonar el repositorio**:
   ```sh
   git clone https://github.com/AlbertGC13/Programacion_Paralela/tree/master
   cd src
   ```
   - Luego que estas en el directorio donde se encuentra el programa ejecutas el siguiente cmd: ```mpjrun.bat -np 4 MatrixMultiplication```
   - Si no esta compilado pues primero este: ```javac -cp .;%MPJ_HOME%\lib\mpj.jar MatrixMultiplication.java```


