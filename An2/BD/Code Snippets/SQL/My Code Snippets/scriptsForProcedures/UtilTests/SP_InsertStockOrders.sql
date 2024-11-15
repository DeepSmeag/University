USE [MusicShop]
GO
/****** Object:  StoredProcedure [dbo].[SP_InsertStockOrders]    Script Date: 12/8/2022 10:03:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[SP_InsertStockOrders]
	-- Add the parameters for the stored procedure here
	@Rows int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	DBCC CHECKIDENT ('StockOrders', RESEED, 0);
	while @Rows > 0
	begin
		--INSERT INTO [dbo].[StockOrders]
  --         ([dateCreated]
  --         ,[adminId]
  --         ,[itemCode]
  --         ,[quantity])
		--VALUES
  --         ('2022-11-00'
  --         ,<adminId, int,>
  --         ,<itemCode, varchar(255),>
  --         ,<quantity, int,>)
		set @Rows = @Rows -1
	end
    -- Insert statements for procedure here
	print 'executed insertStockOrders ' + cast(@Rows as nvarchar(50))
END
