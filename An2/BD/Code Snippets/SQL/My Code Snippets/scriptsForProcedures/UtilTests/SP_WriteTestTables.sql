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
ALTER PROCEDURE SP_WriteTestTables
	-- Add the parameters for the stored procedure here
	@spTestID int,
	@spTableID int,
	@spTestRunTablesStart datetime,
	@spTestRunTablesEnd datetime
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	INSERT INTO [dbo].[TestRunTables]
           ([TestRunID]
           ,[TableID]
           ,[StartAt]
           ,[EndAt])
     VALUES
           (@spTestID
           ,@spTableID
           ,@spTestRunTablesStart
           ,@spTestRunTablesEnd)
    -- Insert statements for procedure here
	print '    executed WriteTestTables for table ' + cast(@spTableID as varchar(10))+ ' part of test ' + cast(@spTestID as varchar(10))
END
GO
