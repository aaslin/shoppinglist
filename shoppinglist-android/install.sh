#!/bin/bash 

adb install -r shoppinglist-android-app/target/shoppinglist-android-app-1.0.apk 
adb shell am start -n se.aaslin.developer.shoppinglist/se.aaslin.developer.shoppinglist.ui.login.LoginActivity
