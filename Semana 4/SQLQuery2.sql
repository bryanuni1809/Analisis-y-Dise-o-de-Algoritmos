-- Ordena millones de registros de las ordenes de venta
SELECT SalesOrderID, OrderDate, CustomerID, TotalDue
FROM Sales.SalesOrderHeader
ORDER BY TotalDue DESC;
