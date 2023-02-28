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
ALTER PROCEDURE readInventory
(
    @ItemCode NVARCHAR(10)
)
AS
BEGIN
    declare @codeExists BIT
    exec SP_validateAppearsInTable @ItemCode, N'code', N'Inventory', @codeExists output;
    IF dbo.validateCode(@ItemCode) = 1 AND @codeExists = 1
    BEGIN
        SELECT * FROM Inventory WHERE code = @ItemCode
    END
    else
    begin
        print 'Something is wrong; cannot read'
    end
END
GO
