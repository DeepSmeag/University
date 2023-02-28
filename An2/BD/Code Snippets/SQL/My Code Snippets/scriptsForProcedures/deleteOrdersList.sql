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
ALTER PROCEDURE deleteOrderList
(
    @orderID INT
)
AS
BEGIN
    declare @orderExists BIT
    exec SP_validateAppearsInTable @orderID, N'orderID', N'OrdersList', @orderExists output;
    IF dbo.validateNonNegativeInteger(@orderID) = 1 AND @orderExists = 1
    BEGIN
        DELETE FROM OrdersList WHERE orderID = @orderID
    END
    else
    begin
        print 'Something is wrong; cannot delete'
    end
END
GO
