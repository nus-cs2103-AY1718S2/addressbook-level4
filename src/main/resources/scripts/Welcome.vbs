a = "user"

speaks="Welcome back " + a

Set speech=CreateObject("sapi.spvoice")

speech.Rate = 0

speech.Speak speaks