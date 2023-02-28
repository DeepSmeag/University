--Index creation


create unique nonclustered  index IX_Inventory_code 
on Inventory (code asc, category asc, manufacturer desc) with (IGNORE_DUP_KEY=ON);
drop index IX_Inventory_code on Inventory

create unique nonclustered  index IX_Orders_ID_Status 
on Orders (id asc) include (status, orderTotal) with (IGNORE_DUP_KEY=ON);
drop index IX_Orders_ID_Status on Orders 

create nonclustered index IX_OrdersList_InventoryID_OrdersId 
on OrdersList (itemCode asc, orderId asc) include (customerId, orderDate);
drop index IX_OrdersList_InventoryID_OrdersId on OrdersList 