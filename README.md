# SoportePro

¡Bienvenido a **SoportePro**, nuestro sistema de gestión para centros de atención al cliente!  

Este proyecto permite registrar clientes, empleados y tickets de soporte, además de gestionar el seguimiento de casos mediante un menú en consola.

---

### 🚀 Requisitos previos

- **Java**: Oracle JDK 11  
- **IDE**: NetBeans 21  

### ⚙️ Cómo compilar y ejecutar

1. Abra el proyecto en **NetBeans**.  
2. Compile el proyecto con la opción **Build Project** (`F11`).  
3. Ejecute el sistema con **Run Project** (`F6`).

### 🗄️ Configuración de la base de datos MySQL

1. **Importe el esquema y datos iniciales desde el archivo [schema.sql](soporte-pro/resources/schema.sql) incluido en el proyecto**

2. **Configure la conexión en el código Java**

En el clase [DBConnection](soporte-pro/src/main/java/database/DBConnection.java), asegúrese de actualizar la URL de conexión y de reemplazar root y la contraseña por las credenciales de su instalación de MySQL.

```java
String url = "jdbc:mysql://localhost:3306/soporte_pro";
String user = "root";      // su usuario
String password = "12345"; // su contraseña
```

Al importar el archivo `schema.sql`, se cargarán algunos clientes, empleados, sucursales y tickets de ejemplo para poder interactuar directamente con el sistema. 

### 📖 Uso

Una vez iniciado, podrá interactuar con el sistema a través de un **menú en consola**, que permite:  
- Agregar manualmente tickets.  
- Listar los tickets asociados a cada cliente registrado.  

### 👥 Autores

- *Felipe MORALES*
- *Byron VASQUEZ*
- *Manon BERTRAND* 
