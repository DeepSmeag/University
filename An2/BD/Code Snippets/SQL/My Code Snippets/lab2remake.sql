
-- mn1, multi1, group1, having1, distinct
-- how many orders each user has ever placed | only show customers with more than 1 separate order (recurring customers)
select Customers.name, count(distinct OrdersList.orderId) as #orders 
from Customers 
	inner join OrdersList on Customers.id = OrdersList.customerId
	inner join Orders on Orders.id = OrdersList.orderId 
group by Customers.name 
having count(distinct OrdersList.orderId) > 1

-- mn2, multi2, where1
-- how many of each product an admin has ordered, only show quantities >=50 (big orders)
select Admins.name, Inventory.name, StockOrders.quantity from Admins inner join StockOrders on Admins.id=StockOrders.adminId inner join Inventory on Inventory.code = StockOrders.itemCode where StockOrders.quantity >=50

-- multi3, group2, having2, distinct1
-- manufacturers that offer more than 1 distinct type of category of items
select Manufacturers.name, count(distinct ItemsCategories.category) as #categories from Manufacturers inner join Inventory on Manufacturers.name = Inventory.manufacturer inner join ItemsCategories on Inventory.category = ItemsCategories.category group by Manufacturers.name having count(distinct ItemsCategories.category) > 1

-- mn3, multi4, group3, where2, 
-- customers and the number of items they ordered on a single order (if more than 1) + date of that order
select Customers.name, aux.quant as quant, Orders.dateCreated from  Customers inner join (select OrdersList.customerId, OrdersList.orderId, sum(OrdersList.quantity) as quant from OrdersList group by OrdersList.orderId,OrdersList.customerId ) as aux on aux.customerId = Customers.id inner join Orders on aux.orderId = Orders.id where aux.quant > 1

-- multi5, where3
-- distinct categories of items that have items with multiple orders + how many pieces have been ordered in total (assess popularity of category)
select * from (select ItemsCategories.category, sum(OrdersList.quantity) as quant from ItemsCategories join Inventory on ItemsCategories.category = Inventory.category join OrdersList on OrdersList.itemCode = Inventory.code group by ItemsCategories.category) as aux where aux.quant > 2

-- multi6, where4, distinct2
-- out of discounted items, which have a low (<1000) price
select distinct Inventory.name, Promotions.discountPercent, OrdersList.orderUnitPrice from Inventory inner join Promotions on Promotions.itemCode = Inventory.code inner join OrdersList on OrdersList.itemCode = Inventory.code where OrdersList.orderUnitPrice < 1000

-- multi7, where5
-- manufacturers that offer items that are not heavily discounted (< 0.3)
select Manufacturers.name, Inventory.name, Promotions.discountPercent from Manufacturers inner join Inventory on Inventory.manufacturer = Manufacturers.name inner join Promotions on Inventory.code = Promotions.itemCode where Promotions.discountPercent < 0.3

-- see discounted items
select I.[name], I.category, I.priceUnitList * (1- P.discountPercent) as discountedPrice from Inventory I, Promotions P where P.itemCode = I.code ; -- where1, multi1

-- customers that have ordered expensive inventory (>=500 cost), calculating the total sum they spent
select distinct Customers.name, sum(OrdersList.orderUnitPrice * OrdersList.quantity) as Spent from Customers join OrdersList on Customers.id = OrdersList.customerId where OrdersList.orderUnitPrice >=500 group by Customers.name order by Spent desc -- where2, multi2, group2, distinct1

-- number of people that are musicians 
select count(Customers.name) as #musicians from Customers where Customers.isMusician = 1 -- where3
