USE [MusicShop]
GO
/****** Object:  StoredProcedure [dbo].[SP_InsertInventory]    Script Date: 12/8/2022 9:54:28 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[SP_InsertInventory]
	-- Add the parameters for the stored procedure here
	@Rows int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	--DBCC CHECKIDENT ('Inventory', RESEED, 0);
	while @Rows > 0
	begin
		INSERT INTO [dbo].[Inventory]
			   ([code]
			   ,[name]
			   ,[category]
			   ,[manufacturer]
			   ,[priceUnitList]
			   ,[stock])
		VALUES
			   (cast(@Rows as varchar(10))
			   ,cast(@Rows as varchar(10))
			   ,'Mixer'
			   ,'Behringer'
			   ,10
			   ,100)
		set @Rows = @Rows -1
	end
    -- Insert statements for procedure here
	print 'executed SP_InsertInventory ' + cast(@Rows as varchar(50))
END
