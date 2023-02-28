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
ALTER PROCEDURE SP_validateAppearsInTable
(
    @input VARCHAR(50),
    @column VARCHAR(50),
    @table VARCHAR(50)
)
AS
BEGIN
	DECLARE @isValid BIT
	DECLARE @Count INT
    DECLARE @query NVARCHAR(100)
    SET @query = N'SELECT @Cnt = Count(*) FROM ' + @table + N' WHERE ' + @column + N' = ''' + @input + N''''
    EXEC sp_executesql @query, N'@Cnt INT OUTPUT', @Cnt=@Count OUTPUT
    if @Count > 0
	begin
		set @isValid = 1
	end
	else
	begin
		set @isValid = 0
	end

    RETURN @isValid
END
GO

