USE [MusicShop]
GO
/****** Object:  StoredProcedure [dbo].[V1_2]    Script Date: 11/12/2022 6:17:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[V2_1] 
	-- Add the parameters for the stored procedure here
	--<@Param1, sysname, @p1> <Datatype_For_Param1, , int> = <Default_Value_For_Param1, , 0>, 
	--<@Param2, sysname, @p2> <Datatype_For_Param2, , int> = <Default_Value_For_Param2, , 0>
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
    -- Insert statements for procedure here
	--SELECT <@Param1, sysname, @p1>, <@Param2, sysname, @p2>

	if (select version from [dbo].[Versions]) != 2
	begin
		return -1
	end

	alter table [dbo].[Promotions]
		drop constraint chk_discountPercent

	alter table [dbo].[Promotions]
		alter column discountPercent float not null

	alter table [dbo].[Promotions]
		add constraint chk_discountPercent check (discountPercent >= 0.00 and discountPercent < 100.00);

	update [dbo].[Versions]
		set [version] = 1
END
