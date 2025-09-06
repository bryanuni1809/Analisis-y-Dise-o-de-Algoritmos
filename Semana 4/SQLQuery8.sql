IF OBJECT_ID('VentasGrandes', 'U') IS NOT NULL
    DROP TABLE VentasGrandes;
GO
SELECT SalesOrderID, 
       CarrierTrackingNumber, 
       OrderQty, 
       ProductID, 
       SpecialOfferID, 
       UnitPrice, 
       UnitPriceDiscount, 
       LineTotal, 
       rowguid, 
       ModifiedDate
INTO VentasGrandes
FROM Sales.SalesOrderDetail;
GO
INSERT INTO VentasGrandes (SalesOrderID, CarrierTrackingNumber, OrderQty, ProductID, SpecialOfferID, UnitPrice, UnitPriceDiscount, LineTotal, rowguid, ModifiedDate)
SELECT SalesOrderID, CarrierTrackingNumber, OrderQty, ProductID, SpecialOfferID, UnitPrice, UnitPriceDiscount, LineTotal, rowguid, ModifiedDate
FROM Sales.SalesOrderDetail;
GO 10