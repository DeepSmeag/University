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
ALTER FUNCTION validateDate
(
    @date VARCHAR(10)
)
RETURNS BIT
AS
BEGIN
    DECLARE @isValid BIT
    DECLARE @year INT
    DECLARE @month INT
    DECLARE @day INT
	
    SET @year = CAST(SUBSTRING(@date, 1, 4) AS INT)
    SET @month = CAST(SUBSTRING(@date, 6, 2) AS INT)
    SET @day = CAST(SUBSTRING(@date, 9, 2) AS INT)
	
	
    IF LEN(@date) = 10 AND @year > 0 AND @month > 0 AND @month < 13 AND @day > 0 AND @day < 32
    BEGIN
        SET @isValid = 1
    END
    ELSE
    BEGIN
        SET @isValid = 0
    END
    RETURN @isValid
END
GO
