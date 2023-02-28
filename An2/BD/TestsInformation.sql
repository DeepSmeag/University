-- Predare 12 decembrie

--Test1
--Need 1 table, 1 view
--Customers as table
--* from Customers as view



--Test2
--Need 3 tables, 3 views
--Customers, Orders, OrdersList as tables
--Views for Customers, Orders + OrdersList, Customers + Orders + OrdersList group by customersId-OrderId?


--Test3
--Need 3 tables, 4-5 views; chose 4 tables, 4 views
--Orders, OrdersList, Inventory as tables? + Customers
--Views for Orders, Inventory + OrdersList, Orders+OrdersList+Inventory group by OrdersId+InventoryId?, Customers+Orders+OrdersList+Inventory group by...?

--Notite

--Avem reseed la operatii de selectare/random chestii cand luam pt fiecare x elem sa luam vreo 10 alti itemi de generat si cu reseed o luam de la inceputul celor x
--Deci practic un multiplier pt elem dintr-o tabela ca sa fie generate chestii in alta folosim ceva cu reseed

--Invatam despre cursor ca sa putem parcurge un tabel si sa o luam de la inceput; ex pt fiecare manufacturer la mine vreau sa generez 10-20 Inventory; bag 1000 furnizori si de acolo am 10k Inventory total
--folosesc cursoare ca sa navighez asta
--Folosim while peste un cursor si merge peste ele si cand ajunge la capat il dam de la inceput
--Folosim proceduri stocate simple ex PesteInventory, primeste cativa parametri doar si insereaza el usor
--Facem o procedura de InsertValuesInto (primeste parametri campurile si doar aplica un insert)



-- sa facem reseed la TestRuns ( trb golit, nu adunam multe recorduri), ca sa se potriveasca ID de test cu cele din Tests si asa
-- masuram la insert si la select pe view; start si end din TestRuns vor fi start de inceput si end la final de tot