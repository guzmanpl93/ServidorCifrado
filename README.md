#Ejercicio Práctico 2ª Evaluación
##Servidor de Cifrado

###Ejercicio 1.
Usando el lenguaje Java y técnicas de programación orientada a objetos, completa la clase dam.psp.Servidor de la carpeta src para crear un servidor concurrente que acepte peticiones con el formato que se describe a continuación.


**Petición para obtener el hash de una secuencia de bytes.**


Código de petición (valor 200 de tipo `int`) seguido de una cadena con el nombre de un algoritmo de resumen seguido de una secuencia de bytes de longitud arbitraria.


- **Si la petición es correcta**, el servidor responde con el valor 0 de tipo ìnt` seguido de una cadena que contenga el resumen codificado en Base64.


- **Si la petición es incorrecta**, el servidor responde con el valor -1 de tipo `int` seguido de uno de los siguientes códigos de error, también de tipo `int`:
    - 201 si se produce un timeout al leer el nombre del algoritmo.	
    - 202 si el dato recibido como nombre del algoritmo no es una cadena.	
    - 203 si el nombre del algoritmo no es válido.	
    - 204 si se produce un timeout al leer la secuencia de bytes.

**Petición para almacenar en un objeto KeyStore un certificado.**


Código de petición 300 (valor de tipo `int`) seguido de una cadena que contenga el alias para el certificado, seguido de otra cadena que contenga la codificación en Base64 del certificado.
El servidor deberá crear el almacén de claves si no existe.

- **Si la petición es correcta**, el servidor responde con el valor 0, de tipo `int`, seguido de una cadena que contenga un resumen del certificado codificado en Base64.


- **Si la petición es incorrecta**, el servidor responde con el valor -1 de tipo `int` seguido de uno de los siguientes códigos de error, también de tipo `int`: 
    - 301 si se produce un timeout al leer el alias.    - 302 si el dato recibido como alias no es una cadena.    - 303 si se produce un timeout al leer el certificado
    - 304 si el dato recibido como certificado no es una cadena.
    -	305 si el certificado no está codificado en Base64.
    - 306 si no se recibe un certificado válido.
    
**Petición para cifrar una secuencia de bytes con el algoritmo “RSA/ECB/PKCS1Padding”**

Código de petición 400 (valor de tipo `int`) seguido de una cadena que contenga el alias de un certificado almacenado en el KeyStore del servidor, seguido de una secuencia de bytes de longitud arbitraria.

El cifrado se realizará dividiendo la secuencia en bloques de 256 bytes, cifrando cada uno por separado. El resultado de cifrar cada bloque se codificará en Base64.

- Si la petición es correcta el servidor responderá enviando cada bloque Base64 precedido del valor 0 (de tipo `int`), a excepción del último que se enviará precedido del valor 1 en lugar del valor 0.

- **Si la petición es incorrecta**, el servidor responde con el valor -1 de tipo `int` seguido de uno de los siguientes códigos de error, también de tipo `int`:
    - 401 si se produce un timeout al leer el alias.
    - 402 si el dato recibido como alias no es una cadena.
    - 403 si no existe el alias en el almacén de claves.
    - 404 si la clave asociada al certificado no es una clave RSA.
    - 304 si el dato recibido como certificado no es una cadena.
    - 305 si se produce un timeout al leer la secuencia de bytes.

**Otros códigos de respuesta:**

El servidor también enviará los siguientes códigos de error (valores de tipo `int`) precedidas del valor -1 (de tipo int):
- 10001 si se produce un timeout al leer la petición.
- 10002 si se recibe una petición vacía.
- 10003 si el código de petición no es uno de los tres indicados.

###Ejercicio 2.

Completa el caso de prueba llamado `test17` para que realice una prueba de una petición válida para cifrar un texto de una longitud superior a 256 bytes que no sea múltiplo de 256.
