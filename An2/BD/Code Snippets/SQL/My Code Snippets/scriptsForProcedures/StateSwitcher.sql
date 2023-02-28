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
alter PROCEDURE [dbo].[StateSwitcher] @state int = 1
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

	if @state < 1 or @state > 6
		return -1


	declare @currState as int = (select version from [dbo].[Versions]);
		
	declare @proc nvarchar(10);

	while @currState != @state
	begin
		if @currState < @state
		begin
			set @proc = N'V' + cast(@currState as nvarchar(2)) + '_' + cast(@currState+1 as nvarchar(2));
			print N'Now executing ' + @proc ;
			execute sp_executesql @proc;
			set @currState = @currState + 1;
		end
		else
		begin
			set @proc = N'V' + cast(@currState as nvarchar(2)) + '_' + cast(@currState-1 as nvarchar(2));
			print N'Now executing ' + @proc;
			execute sp_executesql @proc;
			set @currState = @currState - 1;
		end
	end

	update [dbo].[Versions]
		set [version] = @state
END
