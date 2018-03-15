name = WScript.Arguments(0)

speaks="This is " + name

Set speech=CreateObject("sapi.spvoice")

speech.Rate = 0

speech.Speak speaks