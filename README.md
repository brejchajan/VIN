#VIN - Examples and Skeletons for course Artistic Informatics on FIT VUT Brno)
*(Příklady a Skeletony pro předmět Výtvarná Informatika na FIT VUT Brno)*

With these skeletons and examples we want to make it simple to start playing
with programming graphics on NVIDIA SHIELD Tablet. All skeletons are written
with QT 5.4 toolkit, so it may be easy to run it also an laptops, PCs, and other
smart devices like iOS or Android phones and tablets.

Introduction for each skeleton is written in its own README file.

##Skeletons
The sceletons are simple projects which should be improved as a homework in the VIN
course. The detailed tak assignment will be placed in the README file of each project. 
Also a tutorial is provided there for you, so most things you really need will be
explained there.

Below is a brief owerview of the tasks assignments. 

###Task 1 -- Fractal browser
Based on the [Mandelbrot Set](mandelbrot_set/README.md) skeleton create an application
that is able to browse the Mandelbrot Set with pinch zoom and panning (scrolling).

###Task 2 -- Custom fractal
Alter the Mandelbrot Set fractal in [Mandelbrot Set](mandelbrot_set/README.md) skeleton
with some more advanced one which was presented in the VIN course. If you did
the Task 1, you can use it as the starting point for your project, instead of the 
[Mandelbrot Set](mandelbrot_set/README.md) skeleton, so you have your own fractal
browser with a custom fractal.

###Task 3 -- Laplacian Edge Detector
Based on the [Derivative](derivative/README.md) skeleton create an laplacian 
edge detector which detects edges on live camera stream of the tablet.

##Examples 
Also more advanced examples are prepared for you as a starting point for your own 
projects. The examples are written in Android Studio in Android Java with OpenGLES 3.1.

###Example 1 -- Simple Camera
[Simple Camera](androidCamera/README.md) example shows how to take the camera stream, send it to OpenGLES shaders 
as a texture and render the results with OpenGLES. It is very basic and simple,
no other filtering of the input texture is included. It can serve as a starting 
point for any camera for photo/video application, advanced filtering in shaders 
can be added and more. You can for instance only play with fragment shader to 
see the results of the transformation of camera stream directly on the display 
in real time.

###Example 1 -- PhotoCube
[Photo Cube](PhotoCube/README.md) example takes the [Simple Camera](androidCamera/README.md) 
example to the next level. It shows how to add basic geometry and transformations
to the OpenGLES and combine them with the input camera stream. As a result you can
observe a [rotating cube](https://www.youtube.com/watch?v=gF6YdSC22MM) textured 
with the real time camera stream from camera.


