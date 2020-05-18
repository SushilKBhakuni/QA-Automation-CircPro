Call("uploadFile")

Func uploadFile()
;$date = "C:\Users\NiSingh\Downloads\test";
$path = $CMDLINE[1]
WinWait("File Upload")
WinActivate("File Upload")
ControlSetText("File Upload", "", "[CLASS:Edit;INSTANCE:1]",$path)
Sleep(4000);
ControlClick("File Upload", "", "[CLASS:Button;TEXT:&Open]")

EndFunc
