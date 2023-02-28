USE [MusicShop]
GO

UPDATE [dbo].[Orders]
   SET [orderTotal] = (select sum(quantity*orderUnitPrice) as value from OrdersList where orderId = 1)
 WHERE id = 1
GO


