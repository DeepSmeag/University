-- ================================================
-- Template generated from Template Explorer using:
-- Create Inline Function (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the function.
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
CREATE FUNCTION validateName
(
    @name VARCHAR(50)
)
RETURNS BIT
AS
BEGIN
    DECLARE @isValid BIT
    IF @name = UPPER(SUBSTRING(@name, 1, 1)) + LOWER(SUBSTRING(@name, 2, LEN(@name) - 1)) AND LEN(@name) > 0
    BEGIN
        SET @isValid = 1
    END
    ELSE
    BEGIN
        SET @isValid = 0
    END
    RETURN @isValid
END
