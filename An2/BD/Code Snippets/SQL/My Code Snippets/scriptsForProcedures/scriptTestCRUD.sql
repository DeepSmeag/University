--Big inserts
exec dbo.SP_InsertCustomers @Rows = 1000
go 
exec dbo.SP_InsertInventory @Rows = 1000
go
exec dbo.SP_InsertOrders @Rows = 1000
go
exec dbo.SP_InsertOrdersList @Rows = 1000


--Inventory

exec dbo.createInventory @ItemCode = 'code', @ItemName = 'name', @stock = 1, @pricePerUnit = 1, @category = 'Microphone', @manufacturerName = 'Behringer';

exec dbo.readInventory @ItemCode = 'code'
select * from Inventory

exec dbo.updateInventory @ItemCode = 'code', @ItemName = 'nameAltered', @stock = 1, @pricePerUnit = 1, @category = 'Microphone', @manufacturerName = 'Behringer';

exec dbo.deleteInventory @ItemCode = 'code'

--Orders

exec dbo.createOrder @dateCreated = '2022-12-05', @status = 'RETURNED', @comments = '', @orderTotal = 5;
exec dbo.SP_Delete N'Orders'
DBCC CHECKIDENT ('Orders', RESEED, 0);

exec dbo.readOrder @id = 1;
select * from Orders

exec dbo.updateOrder @id = 1, @dateCreated = '2023-01-01', @status = 'DELIVERED', @comments = '', @orderTotal = 10;

exec dbo.deleteOrder @id = 1;

--OrdersList
select * from Customers
exec dbo.SP_InsertCustomers @Rows = 100

exec dbo.createOrderList @orderID = 3, @customerId = 4, @itemCode ='code', @quantity = 1, @orderUnitPrice = 10, @orderDate = '2023-01-01';

exec dbo.readOrderList @orderID = 1;
select * from OrdersList

exec dbo.updateOrderList @orderID = 1, @customerId = 1, @itemCode ='code', @quantity = 1, @orderUnitPrice = 10, @orderDate = '2022-12-01';

exec dbo.deleteOrderList @orderID = 1;

