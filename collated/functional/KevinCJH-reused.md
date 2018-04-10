# KevinCJH-reused
###### /java/seedu/address/logic/GoogleAuthentication.java
``` java
            validToken = checkTokenIndex(url.indexOf("token="));
            if (validToken) {
                token = url.substring(url.indexOf("token=") + 6, url.indexOf("&"));
            } else {
                throw new StringIndexOutOfBoundsException();
            }
```
