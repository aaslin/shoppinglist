#!/bin/bash 
adb install -r shoppinglist-androidapp/target/shoppinglist-androidapp-1.0.apk 
adb shell am start -n se.aaslin.developer.shoppinglist/se.aaslin.developer.shoppinglist.ui.login.LoginActivity
