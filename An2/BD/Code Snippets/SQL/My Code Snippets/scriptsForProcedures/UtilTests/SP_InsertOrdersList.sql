USE [MusicShop]
GO
/****** Object:  StoredProcedure [dbo].[SP_InsertOrdersList]    Script Date: 12/8/2022 9:58:57 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[SP_InsertOrdersList]
	-- Add the parameters for the stored procedure here
	@Rows int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	--DBCC CHECKIDENT ('OrdersList', RESEED, 0);
	while @Rows > 0
	begin
		INSERT INTO [dbo].[OrdersList]
           ([orderId]
           ,[customerId]
           ,[itemCode]
           ,[quantity]
           ,[orderUnitPrice]
           ,[orderDate])
     VALUES
           (cast(@Rows as varchar(10))
           ,100
           ,cast(@Rows as varchar(10))
           ,2
           ,10
           ,'2022-11-10')
		set @Rows = @Rows -1
	end
    -- Insert statements for procedure here
	print 'executed SP_InsertOrdersList ' + cast(@Rows as varchar(50))
END
