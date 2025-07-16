// =========================
// Insertar usuarios
// =========================
db.usuarios.insertMany([
    {
        _id: ObjectId("64fa12345678901234567890"),
        nombre: "Juan Pérez",
        email: "juan.perez@example.com",
        password: "$2a$10$AISZ89nssbCIa7G3knd6bOSj5VKXCfxlyGgzyrw1MAB1J7jUetf4i",
        rol: "CLIENTE",
        fechaCreacion: ISODate("2025-07-12T10:00:00Z"),
        activo: true,
        saldoDisponible: NumberDecimal("500000"),
        notificacionPreferencia: "EMAIL"
    },
    {
        _id: ObjectId("64fa12345678901234567891"),
        nombre: "María Gómez",
        email: "maria.gomez@example.com",
        password: "$2a$10$AISZ89nssbCIa7G3knd6bOSj5VKXCfxlyGgzyrw1MAB1J7jUetf4i",
        rol: "CLIENTE",
        fechaCreacion: ISODate("2025-07-12T10:00:00Z"),
        activo: true,
        saldoDisponible: NumberDecimal("500000"),
        notificacionPreferencia: "SMS"
    },
    {
        _id: ObjectId("64fa12345678901234567892"),
        nombre: "Carlos Ramírez",
        email: "carlos.ramirez@example.com",
        password: "$2a$10$AISZ89nssbCIa7G3knd6bOSj5VKXCfxlyGgzyrw1MAB1J7jUetf4i",
        rol: "ADMIN",
        fechaCreacion: ISODate("2025-07-12T10:00:00Z"),
        activo: true,
        saldoDisponible: NumberDecimal("500000"),
        notificacionPreferencia: "EMAIL"
    }
]);

// =========================
// Insertar fondos
// =========================
db.fondos.insertMany([
    {
        _id: "1",
        nombre: "FPV_BTG_PACTUAL_RECAUDADORA",
        montoMinimo: NumberDecimal("75000"),
        categoria: "FPV"
    },
    {
        _id: "2",
        nombre: "FPV_BTG_PACTUAL_ECOPETROL",
        montoMinimo: NumberDecimal("125000"),
        categoria: "FPV"
    },
    {
        _id: "3",
        nombre: "DEUDAPRIVADA",
        montoMinimo: NumberDecimal("50000"),
        categoria: "FIC"
    },
    {
        _id: "4",
        nombre: "FDO-ACCIONES",
        montoMinimo: NumberDecimal("250000"),
        categoria: "FIC"
    },
    {
        _id: "5",
        nombre: "FPV_BTG_PACTUAL_DINAMICA",
        montoMinimo: NumberDecimal("100000"),
        categoria: "FPV"
    }
]);

// =========================
// Insertar transacción ejemplo
// =========================
db.transacciones.insertOne({
    transaccionId: "TXN001",
    clienteId: "64fa12345678901234567890", // Juan Pérez
    fondoId: "1",
    tipo: "SUSCRIPCION",
    monto: NumberDecimal("75000"),
    fecha: ISODate("2025-07-12T10:15:00Z")
});
