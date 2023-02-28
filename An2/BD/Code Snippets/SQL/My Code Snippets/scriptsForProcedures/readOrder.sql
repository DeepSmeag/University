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
ALTER PROCEDURE readOrder
(
    @id INT
)
AS
BEGIN
    IF dbo.validateNonNegativeInteger(@id) = 1
    BEGIN
        SELECT * FROM Orders WHERE id = @id
    END
    else
    begin
        print 'Something is wrong; cannot read'
    end
END
GO
