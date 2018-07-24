ECHO OFF
SETLOCAL ENABLEEXTENSIONS
SET command=%1
SET arg1=%2
SET arg2=%3

IF "%command%"=="matchday" (
    IF DEFINED arg1 (
	IF DEFINED arg2 (
	    java -jar ${project.build.finalName}.jar -c %command% -match_day %arg1% -season %arg2%
	) else {
   	    java -jar ${project.build.finalName}.jar -c %command% -match_day %arg1%
	}
    ) else (
        java -jar ${project.build.finalName}.jar -c %command%
    )
)
