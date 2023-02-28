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
ALTER PROCEDURE createInventory
(
    @ItemCode NVARCHAR(10),
    @ItemName NVARCHAR(50),
    @stock INT,
    @pricePerUnit INT,
    @category NVARCHAR(50),
    @manufacturerName NVARCHAR(50)
)
AS
BEGIN
	declare @codeExists BIT
	declare @categoryExists BIT
	declare @manufacturerExists BIT
	exec SP_validateAppearsInTable @ItemCode, N'code', N'Inventory', @codeExists output;
	exec SP_validateAppearsInTable @category, N'category', N'ItemsCategories', @categoryExists output;
	exec SP_validateAppearsInTable @manufacturerName, N'name', N'Manufacturers', @manufacturerExists output;
	IF dbo.validateCode(@ItemCode) = 1 AND @codeExists = 0 AND dbo.validateName(@ItemName) = 1 AND dbo.validateNonNegativeInteger(@stock) = 1 AND dbo.validateNonNegativeInteger(@pricePerUnit) = 1 AND @manufacturerExists = 1 AND @categoryExists = 1
    BEGIN
        INSERT INTO Inventory VALUES (@ItemCode, @ItemName, @category, @manufacturerName,@pricePerUnit, @stock)
    END
	else
	begin
		print 'Something is wrong; cannot insert'
	end
END

