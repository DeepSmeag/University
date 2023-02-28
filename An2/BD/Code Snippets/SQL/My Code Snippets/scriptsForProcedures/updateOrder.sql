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
ALTER PROCEDURE updateOrder
(
    @id INT,
    @dateCreated nvarchar(10),
    @status NVARCHAR(50),
    @comments NVARCHAR(50),
    @orderTotal INT
)
AS
BEGIN
    declare @statusExists BIT
	declare @orderExists BIT
	declare @dateCreatedType date
    exec SP_validateAppearsInTable @status, N'status', N'OrderStatus', @statusExists output;
	exec SP_validateAppearsInTable @id, N'id', N'Orders', @orderExists output;
    IF dbo.validateNonNegativeInteger(@orderTotal) = 1 AND @statusExists = 1 AND @orderExists = 1 AND dbo.validateDate(@dateCreated) = 1
    BEGIN
		set @dateCreatedType = @dateCreated
        UPDATE Orders SET dateCreated = @dateCreatedType, status = @status, comments = @comments, orderTotal = @orderTotal WHERE id = @id
    END
    else
    begin
        print 'Something is wrong; cannot update'
    end
END
GO
