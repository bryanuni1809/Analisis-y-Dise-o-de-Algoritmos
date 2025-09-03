USE [Análisis y Diseño de Algoritmos];
DROP TABLE IF EXISTS VentasExternas;
CREATE TABLE VentasExternas(
    IdVenta INT IDENTITY PRIMARY KEY,
    Cliente VARCHAR(50),
    Monto DECIMAL(10,2),
    Fecha DATE
);
INSERT INTO VentasExternas(Cliente,Monto,Fecha)
SELECT TOP (100000)
    'Cliente_'+CAST(ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS VARCHAR),
    CAST(RAND(CHECKSUM(NEWID()))*1000 AS DECIMAL(10,2)),
    DATEADD(DAY, - (ABS(CHECKSUM(NEWID())) % 365), GETDATE())
FROM sys.all_objects a
CROSS JOIN sys.all_objects b;

SET STATISTICS IO ON;
SET STATISTICS TIME ON;
SELECT*FROM VentasExternas
ORDER BY Fecha;
