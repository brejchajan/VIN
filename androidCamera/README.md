#AndroidCamera Example

This is an example written in Android Java with OpenGLES 3.1. It takes the 
camera stream and feeds it into opengl frame by frame as a texture. The texture
is rendered by fragment shader to the GLSurfaceView.

This example was made in Android Studio. It is recommended to use and Android Studio or
Eclipse to be able to run it. 

##Setup - Android Studio
If you do not have and [Android Studio](http://developer.android.com/sdk/index.html), 
download and istall it. 

Open Android Studio. If you have OSX, you will probably need to set your JVM machine
for the Android Studio. To do this, add following line into your ~/.bash_profile file:

```
export STUDIO_JDK=/Library/Java/JavaVirtualMachines/jdk1.7.0_45.jdk
```

Set proper version of JDK, which you have installed, I recommend version 1.7.
Then open your Terminal and open the Android Studio from terminal like this:

```
open /Applications/Android\ Studio.app
```

If you have any other system, Android Studio shall work out of the box. Now, create 
a new project:

-	Start a new Android Studio project.
-	Set Application Name, click next.
-	Tick phone and tablet, set minimum SDK to API 21.
-	Add No Activity, Finish.

After new project is created, just copy the source files within this example into the
directory tree of your new project:

-	Copy the directory VIN/androidCamera/app/src into <your_project_dir>/app. 
	Overwrite all files that already exist in the target directory.

Now you should be able to compile and run the project.


