ECHO OFF
SETLOCAL ENABLEEXTENSIONS
SET command=%1
SET arg1=%2
SET arg2=%3

IF "%command%"=="matchday" (
    IF DEFINED arg1 (
	IF DEFINED arg2 (
	    java -cp 'lib/*:config' org.d11.admin.D11Admin %command% -number %arg1% -season %arg2%
	) ELSE (
   	    java -cp 'lib/*:config' org.d11.admin.D11Admin %command% -number %arg1%
	)
    ) ELSE (
        java -cp 'lib/*:config' org.d11.admin.D11Admin %command%
    )
) ELSE IF "%command%"=="match" (
    java -cp 'lib/*:config' org.d11.admin.D11Admin -password $command -matchId %arg1%
) ELSE IF "%command%"=="datetimes" (
    java -cp 'lib/*:config' org.d11.admin.D11Admin -password $command
) ELSE (
    java -cp 'lib/*:config' org.d11.admin.D11Admin %command%
)

