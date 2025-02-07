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
