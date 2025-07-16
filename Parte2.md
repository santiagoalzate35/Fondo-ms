Para resolver la consulta solicitada en la Parte 2 – 
SQL de la prueba técnica, se diseñó una sentencia SQL que identifica a los clientes que tienen inscrito al menos un producto 
disponible exclusivamente en las sucursales que ellos mismos han visitado. La solución parte del supuesto de que existen 
tablas que representan clientes, productos, sucursales, visitas y las inscripciones de productos por cliente. La consulta 
utiliza una combinación de joins y una subconsulta con NOT EXISTS para asegurar que, para cada cliente, todos los productos 
en los que está inscrito se encuentren únicamente en sucursales que ha visitado, y no en ninguna otra. De esta manera, se garantiza 
que el resultado incluya únicamente los nombres de los clientes que cumplen estrictamente con la condición planteada en el enunciado.

```sql
SELECT DISTINCT c.nombre
FROM clientes c
JOIN inscripciones i ON c.id = i.cliente_id
JOIN productos p ON i.producto_id = p.id
JOIN sucursales s ON p.sucursal_id = s.id
WHERE p.sucursal_id IN (
    SELECT v.sucursal_id
    FROM visitas v
    WHERE v.cliente_id = c.id
)
AND NOT EXISTS (
    SELECT 1
    FROM productos p2
    WHERE p2.id = i.producto_id
    AND p2.sucursal_id NOT IN (
        SELECT v2.sucursal_id
        FROM visitas v2
        WHERE v2.cliente_id = c.id
    )
);
```
