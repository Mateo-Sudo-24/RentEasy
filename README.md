# RentEasy
En primer lugar creamos nuestra bas de datos en mongo DB como esta de muestra en la imagen:
![image](https://github.com/user-attachments/assets/b9f8f7fd-4db0-4ac1-9131-4a81e223e3d6)

Creamos las colleciones donde vamos a guardar las casas transacciones y datos

![image](https://github.com/user-attachments/assets/7f7e350c-21d5-4771-8bce-3b76dc69ad4d)

Creamos la clase conexion con las funciones necesarias para tener la BDD en nuestro proyecto

![image](https://github.com/user-attachments/assets/e19b9d71-1184-4f5e-a201-1182f66cccfb)

Luego confirmaos todo esto desde el main mandadno un mensaje de conexion

![image](https://github.com/user-attachments/assets/e3dfe1f9-cbac-4623-aade-fea5ed6809dc)

![image](https://github.com/user-attachments/assets/cf580133-17b5-4d32-9063-518be9715f68)

![image](https://github.com/user-attachments/assets/7175daaa-cadc-4c13-b3be-66395f49ea9a)

Luego generamos la estrucutura de como relizaremos el proyecto

![image](https://github.com/user-attachments/assets/92786f93-bc16-4c29-8916-7789a27b3ad0)

![image](https://github.com/user-attachments/assets/2fa851be-6d00-4202-a585-41d0c8cbeaaa)

![image](https://github.com/user-attachments/assets/3f160781-5f0c-45f0-8a1c-49df60abdf2e)

![image](https://github.com/user-attachments/assets/24567d46-a226-471c-bfa0-5ec426e4121a)

![image](https://github.com/user-attachments/assets/36b2ad06-9f80-45a9-b0a7-f87c180e497d)

![image](https://github.com/user-attachments/assets/bbd67068-57ce-4b7d-b2f2-c07acdb8003d)

![image](https://github.com/user-attachments/assets/7c3d9303-0017-456d-a104-f9ddf9d15920)

Comenzamos a generar roles:

![image](https://github.com/user-attachments/assets/36152815-497d-4cda-96cc-26960f54c8e4)

![image](https://github.com/user-attachments/assets/e7bdaac4-8643-4bca-8a45-b836d2384fb8)

![image](https://github.com/user-attachments/assets/5f7f05ac-d7c3-4586-9bfb-acf850eb93b6)

![image](https://github.com/user-attachments/assets/2c7032f3-d51e-4989-82b8-f8349f6c74d6)

![image](https://github.com/user-attachments/assets/f3ba9465-6b78-4537-9f79-d71c5316bbc5)

Hacemos la prueba si se generan los roles desde la salida de la terminal:

![image](https://github.com/user-attachments/assets/955edd59-f33b-48f7-b48a-439222a92c5d)

Creamos los modelos de nuestro proyecto:

![image](https://github.com/user-attachments/assets/198f7282-2da5-4572-85cf-4d04b40a62a8)

![image](https://github.com/user-attachments/assets/3e07eaba-bff7-4e98-abbc-752c1e9ab83c)

![image](https://github.com/user-attachments/assets/aa011c34-a706-4e66-bda0-4332e316a3b8)

![image](https://github.com/user-attachments/assets/b3e46f1d-de40-420f-a1e7-82431a2d65d8)

![image](https://github.com/user-attachments/assets/1f2b408d-524a-4970-a6dd-569bc1bf8cca)

![image](https://github.com/user-attachments/assets/43ac3dd8-d52f-4a1c-b7d2-cf7f79f617b1)

![image](https://github.com/user-attachments/assets/772afdb7-2c11-4559-a7e3-f1ab1f785a83)

![image](https://github.com/user-attachments/assets/181825de-385d-41a5-b4bb-7ccdd0877947)

![image](https://github.com/user-attachments/assets/d3681ef9-931e-46f3-a8b3-c620b0fb6fdb)

![image](https://github.com/user-attachments/assets/0b8657a2-53cb-4ca2-8d51-394babeb2539)

![image](https://github.com/user-attachments/assets/254673a8-6f7b-4439-87fd-fecd071fc6c6)

![image](https://github.com/user-attachments/assets/a196c641-6582-4f3a-9e85-9c6c0bb4d44b)

![image](https://github.com/user-attachments/assets/741fcc57-277d-4605-a00b-294d94997e0a)



# Informe de Desarrollo y Correcciones en el Proyecto RentEasy

## 1. Introducción
Este informe detalla el desarrollo, implementación y corrección de errores en el sistema **RentEasy**, un sistema de gestión de compra y renta de casas basado en **Java y MongoDB**. Se explicarán los módulos principales, la conexión con la base de datos, la gestión de usuarios y transacciones, además de las mejoras aplicadas en el proceso.

## 2. Conexión a la Base de Datos
Se utilizó **MongoDB** como base de datos para almacenar usuarios, casas y transacciones. La conexión se estableció a través de la clase `Conexion`, la cual instancia la conexión con el servidor MongoDB mediante:

```java
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexion {
    private static MongoDatabase database;
    
    public static MongoDatabase getInstance() {
        if (database == null) {
            database = MongoClients.create("mongodb://localhost:27017").getDatabase("RentEasyDB");
        }
        return database;
    }
}
```

## 3. Módulos Desarrollados
El sistema se compone de diferentes módulos:

### 3.1 Módulo de Usuarios (`UsuarioDAO`)
- Se implementaron métodos para registrar, buscar, actualizar y eliminar usuarios.
- Se agregó validación de duplicados al registrar nuevos usuarios.
- Se corrigieron errores de identificación de roles al momento de consultar datos.

### 3.2 Módulo de Casas (`CasaDAO`)
- Se añadieron métodos para registrar, buscar, listar y eliminar casas.
- Se corrigió el error en la conversión del campo `precio` cuando se recuperaban datos desde la base de datos.

### 3.3 Módulo de Transacciones (`TransaccionDAO`)
- Se implementó el método `buscarTransaccionPorCasaId` para encontrar transacciones por ID de casa.
- Se optimizó la consulta de transacciones, permitiendo filtrar por usuario y casa.

## 4. Implementación de Interfaces Gráficas

### 4.1 `DashboardAdministrador`
- Se mejoró la visualización de datos en tablas.
- Se añadieron métodos para **agregar, editar y eliminar** registros de clientes y vendedores.
- Se corrigió el problema donde solo se podían eliminar casas y no otros registros.

### 4.2 `DashboardVendedor`
- Se agregó la generación de PDF con la información de las transacciones.
- Se corrigió el error `Cannot resolve method 'buscarTransaccionPorCasaId'` llamando el método correcto de `TransaccionDAO`.

### 4.3 `RegistroVendedor`
- Se modificó la interfaz para que use etiquetas (`JLabel`) en lugar de campos de texto para la generación de PDF.
- Se corrigió la llamada incorrecta a `PDFGenerator.generarPDFDesdeBD`.

## 5. Implementación de Generación de PDF (`PDFGenerator`)
Se desarrolló la funcionalidad para exportar información relevante en formato PDF:

- **Generación de comprobantes de transacciones** con detalles de la casa, comprador y vendedor.
- **Generación de reportes desde DashboardVendedor**, listando casas gestionadas.

```java

public static void generarPDFDesdeDashboardVendedor(String filePath, String contenido) {
    try {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        document.add(new Paragraph(contenido));
        document.close();
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
    }
}
```

## 6. Correcciones de Errores
### 🔹 `Cannot resolve method 'buscarTransaccionPorCasaId'` en `DashboardVendedor`
✅ Solución: Se verificó que `TransaccionDAO` tenía el método implementado y se corrigió la llamada incorrecta.

### 🔹 `Cannot resolve method 'generarPDFDashboardVendedor' in 'PDFGenerator'`
✅ Solución: Se creó el método correcto `generarPDFDesdeDashboardVendedor`.

### 🔹 Problema en `RegistroVendedor`
✅ Solución: Se cambiaron los `JTextField` por `JLabel` para evitar errores de entrada de datos.

### 🔹 Problema al eliminar registros en `DashboardAdministrador`
✅ Solución: Se agregó compatibilidad para eliminar **clientes, vendedores y transacciones**, no solo casas.

## 7. Conclusión
Con las correcciones y mejoras implementadas, **RentEasy** ahora cuenta con:
- Una base de datos bien estructurada en **MongoDB**.
- Módulos funcionales para la gestión de usuarios, casas y transacciones.
- Interfaces gráficas intuitivas con capacidades de CRUD.
- Generación de reportes en **PDF**.

Se recomienda realizar **pruebas continuas** para optimizar aún más la funcionalidad y mejorar la experiencia del usuario.


