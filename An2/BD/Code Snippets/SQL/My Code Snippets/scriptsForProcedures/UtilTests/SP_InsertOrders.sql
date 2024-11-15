USE [MusicShop]
GO
/****** Object:  StoredProcedure [dbo].[SP_InsertOrders]    Script Date: 12/8/2022 9:56:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[SP_InsertOrders]
	-- Add the parameters for the stored procedure here
	@Rows int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	DBCC CHECKIDENT ('Orders', RESEED, 0);
	while @Rows > 0
	begin
		INSERT INTO [dbo].[Orders]
           ([dateCreated]
           ,[status]
           ,[comments]
           ,[orderTotal])
     VALUES
           ('2022-12-08'
           ,'NOT PROCESSED'
           ,''
           ,0)
		set @Rows = @Rows -1
	end
    -- Insert statements for procedure here
	print 'executed SP_InsertOrders ' + cast(@Rows as varchar(50))
END
