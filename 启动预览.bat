@echo off
setlocal
set "SDK=C:\Users\shanh\android-sdk"
set "ADB=%SDK%\platform-tools\adb.exe"
set "EMU=%SDK%\emulator\emulator.exe"
set "APK=%~dp0dist\InnerIsle-0.3.2-debug.apk"

if not exist "%EMU%" (
  echo Emulator not found: %EMU%
  pause
  exit /b 1
)
if not exist "%APK%" (
  echo APK not found: %APK%
  pause
  exit /b 1
)

echo Starting Android emulator...
start "InnerIsle Emulator" "%EMU%" -avd inner_isle_test -no-snapshot-save -no-boot-anim

echo Waiting for device...
"%ADB%" wait-for-device

:wait_boot
set "BOOT="
for /f "delims=" %%b in ('"%ADB%" shell getprop sys.boot_completed 2^>nul') do set "BOOT=%%b"
if not "%BOOT%"=="1" (
  timeout /t 2 /nobreak >nul
  goto wait_boot
)

echo Installing APK...
"%ADB%" install -r "%APK%"

echo Launching Inner Isle...
"%ADB%" shell am start -n com.innerisle.app/.MainActivity

echo.
echo Done. Use the emulator window to view the app.
pause
