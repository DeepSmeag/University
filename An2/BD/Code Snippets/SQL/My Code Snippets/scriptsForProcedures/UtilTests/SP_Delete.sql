USE [MusicShop]
GO
/****** Object:  StoredProcedure [dbo].[SP_Delete]    Script Date: 12/8/2022 8:16:26 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[SP_Delete] @TableNameD nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	declare @sql nvarchar(100) = N'delete from ' + @TableNameD
	exec(@sql)
	print 'Deleted from ' + @TableNameD
END
