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
ALTER PROCEDURE SP_CompleteTestRun
	-- Add the parameters for the stored procedure here
	@TestID int,
	@Description nvarchar(2000),
	@StartAt datetime,
	@EndAt datetime
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	UPDATE [dbo].[TestRuns]
   SET [Description] = @Description
      ,[StartAt] = @StartAt
      ,[EndAt] = @EndAt
	 WHERE TestRunID = @TestID
	print '               updated TestRun with id ' + cast(@TestID as nvarchar(10))
END
GO
