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
ALTER PROCEDURE createOrder
(
    @dateCreated nvarchar(10),
    @status NVARCHAR(50),
    @comments NVARCHAR(50),
    @orderTotal INT
)
AS
BEGIN
	declare @dateCreatedType DATE
    declare @statusExists BIT
	set @dateCreatedType = @dateCreated
	print dbo.validateDate(@dateCreated)
    exec SP_validateAppearsInTable @status, N'status', N'OrderStatus', @statusExists output;
    IF dbo.validateNonNegativeInteger(@orderTotal) = 1 AND @statusExists = 1 AND dbo.validateDate(@dateCreated) = 1
    BEGIN
		
        INSERT INTO Orders VALUES (@dateCreatedType, @status, @comments, @orderTotal)
    END
    else
    begin
        print 'Something is wrong; cannot insert'
    end
END
GO
