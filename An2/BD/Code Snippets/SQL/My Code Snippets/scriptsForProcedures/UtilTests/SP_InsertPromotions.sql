USE [MusicShop]
GO
/****** Object:  StoredProcedure [dbo].[SP_InsertPromotions]    Script Date: 12/8/2022 10:01:12 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[SP_InsertPromotions]
	-- Add the parameters for the stored procedure here
	@Rows int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	DBCC CHECKIDENT ('Promotions', RESEED, 0);
	while @Rows > 0
	begin
		INSERT INTO [dbo].[Promotions]
           ([itemCode]
           ,[discountPercent])
		VALUES
           (cast(@Rows as varchar(10))
           ,0.3)
		set @Rows = @Rows -1
	end
    -- Insert statements for procedure here
	print 'executed InsertPromotions ' + cast(@Rows as nvarchar(50))
END
