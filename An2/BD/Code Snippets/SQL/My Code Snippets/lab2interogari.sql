use MusicShop;
go

---- see people above a certain age
--select * from Customers where birthdate > (GETDATE()-100); -- where1

---- see admins that have joined a long time ago
--select * from Admins where since > getdate()-500; -- where2

-- see discounted items
select I.[name], I.category, I.priceUnitList * (1- P.discountPercent) as discountedPrice from Inventory I, Promotions P where P.itemCode = I.code ; -- where1, multi1

-- categories with multiple products and how many products are there
--select count(I.[name]) as #Products, I.category from Inventory I group by I.category having count(I.[name])>=2 order by I.category asc --having1, group1, multi2

-- customers that have ordered expensive inventory (>=500 cost), calculating the total sum they spent
select distinct Customers.name, sum(OrdersList.orderUnitPrice * OrdersList.quantity) as Spent from Customers join OrdersList on Customers.id = OrdersList.customerId where OrdersList.orderUnitPrice >=500 group by Customers.name order by Spent desc -- where2, multi2, group2, distinct1

-- number of people that are musicians 
select count(Customers.name) as #musicians from Customers where Customers.isMusician = 1 -- where3

-- customers that ordered more than 3 products
select Customers.name from Customers inner join OrdersList on Customers.id = OrdersList.customerId group by Customers.name having count(OrdersList.itemCode) > 1; -- having1, group4, multi3
--select Customers.name, aux.quant as Quantity from Customers inner join (select OrdersList.customerId as cid, sum(OrdersList.quantity) as quant from OrdersList group by OrdersList.customerId) as aux on Customers.id = aux.cid where aux.quant >3 -- where3, group4, multi2

-- inventory that has been ordered less than 3 times
--select Inventory.name, sum(OrdersList.quantity) as #ordered from Inventory inner join OrdersList on Inventory.code = OrdersList.itemCode group by Inventory.name having sum(OrdersList.quantity) > 5 -- having3, group5, multi3
select Inventory.name, aux.quant as Quantity from Inventory inner join (select OrdersList.itemCode, sum(OrdersList.quantity) as quant from OrdersList group by OrdersList.itemCode) as aux on Inventory.code = aux.itemCode where aux.quant < 3 -- where4, group5,multi4

-- products that have been ordered by more than 1 distinct customer
select Inventory.name, aux.num as #customers from Inventory inner join (select OrdersList.itemCode, count(distinct OrdersList.customerId) as num from OrdersList group by OrdersList.itemCode) as aux on Inventory.code = aux.itemCode where aux.num > 1 -- where5, multi5, distinct2

-- how many distinct types of categories of products each manufacturer offers
select Manufacturers.name,  ItemsCategories.category from Manufacturers inner join Inventory on Manufacturers.name = Inventory.manufacturer inner join ItemsCategories on Inventory.category = ItemsCategories.category group by Manufacturers.name,ItemsCategories.category --group6,multi6

-- how many of each product a user has ordered, only show products that have been ordered more than once
select Customers.name, Inventory.name, sum(OrdersList.quantity) as #ordered from OrdersList inner join Customers on OrdersList.customerId = Customers.id inner join Inventory on OrdersList.itemCode = Inventory.code  group by Customers.name,Inventory.name order by Customers.name  -- mn1, group7, multi7

-- each user's most ordered #
--select aux.cNames, max(ordered) as #ordered from (select Customers.name as cNames, Inventory.name as pNames, sum(OrdersList.quantity) as ordered from OrdersList inner join Customers on OrdersList.customerId = Customers.id inner join Inventory on OrdersList.itemCode = Inventory.code  group by Customers.name,Inventory.name) as aux group by aux.cNames-- mn(1), ///opt////group7, multi5

-- how many of each product an admin has ordered
select Admins.name, Inventory.name, StockOrders.quantity from Admins inner join StockOrders on Admins.id=StockOrders.adminId inner join Inventory on Inventory.code = StockOrders.itemCode -- mn2, multi8

-- how many orders each user has ever placed | only show customers with more than 1 separate order (recurring customers)
select Customers.name, count(distinct OrdersList.orderId) as #orders from Customers inner join OrdersList on Customers.id = OrdersList.customerId group by Customers.name having count(distinct ORdersList.orderId) > 1 -- having2, multi9, group8



