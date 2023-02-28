use MusicShop;
go

declare @TestID int, @TestName nvarchar(50)
declare @sql nvarchar(100)
declare @paramsDel nvarchar(100)
declare @paramsIns nvarchar(100)
declare @TableName nvarchar(50), @TableID int, @NoRows int, @Position tinyint
declare @ViewName nvarchar(50), @ViewID int
print '-------STARTING TESTS---------'

-- DECLARE OTHER TIMESTAMPS
declare @TestRunTablesStart datetime
declare @TestRunTablesEnd datetime
declare @TestRunViewsStart datetime
declare @TestRunViewsEnd datetime

-- TIMESTAMP TESTRUN START
declare @TestRunStart datetime
declare @TestRunEnd datetime


-- DELETE INFO FROM TESTRUN TABLES
exec SP_ResetTestRuns
exec SP_ResetTestRunTables
exec SP_ResetTestRunViews
-- ----------------------

declare testCursor cursor forward_only for select TestID, Name from Tests order by TestID;

open testCursor

fetch next from testCursor into @TestID, @TestName
while @@FETCH_STATUS = 0
begin
	print '       Now doing Test' + cast(@TestID as nvarchar)
	-- CREATE TEST ENTRY
	set @TestRunStart = CURRENT_TIMESTAMP
	exec SP_CreateTestRun
	
	
	-- --------DELETE FROM TABLES----------
	
	declare deleteCursor cursor forward_only for 
		select Name, NoOfRows 
		from Tables T 
		inner join TestTables TT on T.TableID = TT.TableID 
		where TT.TestID = @TestID 
		order by Position asc;
	open deleteCursor

	fetch next from deleteCursor into @TableName, @NoRows
	while @@FETCH_STATUS = 0
	begin
		print '                  Deleting from Table ' + @TableName
		set @paramsDel = N'SP_Delete ' + @TableName
		exec sp_executesql @paramsDel --N'SP_Delete' , @paramsDel, @TableNameD = @TableName
		fetch next from deleteCursor into @TableName, @NoRows
	end
	close deleteCursor
	deallocate deleteCursor
	-- -------------------------------------------------------
	

	-- ---------- INSERT INTO TABLES------------
	declare insertCursor cursor forward_only for 
		select Name, NoOfRows, T.TableID
		from Tables T 
		inner join TestTables TT on T.TableID = TT.TableID 
		where TT.TestID = @TestID 
		order by Position desc;
	open insertCursor
	
	
	fetch next from insertCursor into @TableName, @NoRows, @TableID
	while @@FETCH_STATUS = 0
	begin
		print '                  Inserting into Table ' + cast(@TableName as nvarchar(50))
		--First clearing up the table with the timings

		-----------Measuring Insert time
		-- -----TIMESTAMP TESTRUNTABLES START
		set @TestRunTablesStart = CURRENT_TIMESTAMP
		--Executing insert script
		set @sql = N'SP_Insert' + cast(@TableName as nvarchar) 
				+ N' @Rows = ' + cast(@NoRows as nvarchar(50))
		set @paramsIns = N'@Rows int'
		exec sp_executesql @sql
		-- ------TIMESTAMP TESTRUNTABLES END
		set @TestRunTablesEnd = CURRENT_TIMESTAMP
		-----------
		-- -----WRITE TIMESTAMP TO TABLE
		exec SP_WriteTestTables @TestID, @TableID, @TestRunTablesStart, @TestRunTablesEnd
		fetch next from insertCursor into @TableName, @NoRows, @TableID
	end
	close insertCursor
	deallocate insertCursor
	
	-- ------------------------------------------
	
	-- --------SELECTING FROM VIEWS----------
	declare viewCursor cursor forward_only for 
		select Name, V.ViewID
		from Views V 
		inner join TestViews TV on V.ViewID = TV.ViewID 
		where TV.TestID = @TestID 
	open viewCursor
	fetch next from viewCursor into @ViewName, @ViewID
	while @@FETCH_STATUS = 0
	begin
	-- ------TIMESTAMP TESTRUNVIEWS START
		set @TestRunViewsStart = CURRENT_TIMESTAMP
		set @sql = N'select * from ' + cast(@ViewName as nvarchar(50))
		exec sp_executesql @sql
		-- ------TIMESTAMP TESTRUNVIEWS END
		set @TestRunViewsEnd = CURRENT_TIMESTAMP
		-- ------WRITE TIMESTAMP TO TABLE
		exec SP_WriteTestViews @TestID, @ViewID, @TestRunViewsStart, @TestRunViewsEnd
	
		fetch next from viewCursor into @ViewName, @ViewID
	end
	close viewCursor
	deallocate viewCursor
	
	-- ------------------------------------------
	-- --------DELETE FROM TABLES----------
	
	declare deleteCursor cursor forward_only for 
		select Name, NoOfRows 
		from Tables T 
		inner join TestTables TT on T.TableID = TT.TableID 
		where TT.TestID = @TestID 
		order by Position asc;
	open deleteCursor

	fetch next from deleteCursor into @TableName, @NoRows
	while @@FETCH_STATUS = 0
	begin
		print '                  Deleting from Table ' + @TableName
		set @paramsDel = N'SP_Delete ' + @TableName
		exec sp_executesql @paramsDel --N'SP_Delete' , @paramsDel, @TableNameD = @TableName
		fetch next from deleteCursor into @TableName, @NoRows
	end
	close deleteCursor
	deallocate deleteCursor
	-- -------------------------------------------------------
	
	set @TestRunEnd = CURRENT_TIMESTAMP
	exec SP_CompleteTestRun @TestID, '', @TestRunStart, @TestRunEnd
	-- write TestRunEnd to thing
	fetch next from testCursor into @TestID, @TestName
end
print '-------ENDING TESTS---------'
close testCursor
deallocate testCursor

select * from TestRuns
select * from TestRunTables
select * from TestRunViews
