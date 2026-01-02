@echo off
echo Fixing group_quiz_sessions table structure...
echo.

cd /d C:\xampp\mysql\bin
mysql.exe -u root -p storia_db < "C:\xampp\htdocs\storia_project\sql\check-and-fix-group-sessions.sql"

echo.
echo Done! Press any key to exit...
pause >nul
