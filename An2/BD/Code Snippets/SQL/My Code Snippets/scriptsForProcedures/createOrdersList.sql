-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE createOrderList
(
    @orderID INT,
    @customerId INT,
    @itemCode NVARCHAR(10),
    @quantity INT,
    @orderUnitPrice INT,
    @orderDate NVARCHAR(10)
)
AS
BEGIN
    declare @orderExists BIT
    declare @customerExists BIT
    declare @itemExists BIT
	declare @orderDateType DATE
    exec SP_validateAppearsInTable @orderID, N'id', N'Orders', @orderExists output;
    exec SP_validateAppearsInTable @customerId, N'id', N'Customers', @customerExists output;
    exec SP_validateAppearsInTable @itemCode, N'code', N'Inventory', @itemExists output;
    IF dbo.validateNonNegativeInteger(@quantity) = 1 AND @orderExists = 1 AND @customerExists = 1 AND @itemExists = 1 AND dbo.validateNonNegativeInteger(@orderUnitPrice) = 1 AND dbo.validateDate(@orderDate) = 1
    BEGIN
		set @orderDateType = @orderDate
        INSERT INTO OrdersList VALUES (@orderID, @customerId, @itemCode, @quantity, @orderUnitPrice, @orderDateType)
    END
    else
    begin
        print 'Something is wrong; cannot insert'
    end
END
GO
