#Derivative Skeleton
This skeleton is written in QML with QT 5.4 toolkit and is a starting point for
the second task from the photo/computational branch of VIN course. 

##Prerequisities
You should have TADP and QT 5.4.0 installed. If you do not have these, please follow
the instructions in general [README](../README.md). You should also be able to 
run this skeleton on the NVIDIA Shield device or at least on your laptop.
Manual how to do this can also be fould in general [README](../README.md).

##Tutorial
We suppose you are able to run this skeleton using QT 5.4.0.

All the code that will be discused is in single qml file. Please, locate the 
file in the left menu pane Edit > Resources > qml.qrc > / > main.qml and open it.
If you do not have any idea how the QML code works, please look at the first task 
introduction: [Mandelbrot Set](../mandelbrot_set/README.md)

###Image Filtration
The core of the edge detection is image filtration. This is done as convolution of 
the image with some filtration kernel. The convolution is simply weighted average
of the pixels in some area, where the weights are given by the filtration kernel. 
Formally the convolution can be written as follows:

```F(x, y) * G(s, t) = SUM_{s}SUM_{t} F(x - s, y - t) * G(s, t).```
The F(x, y) is image intensity (greyscale value) at point (x, y), and the G(s, t) 
is the kernel value at point (s, t). With this filtering many things can be achieved.
For example, the image can be blurred - for example with gaussian kernel that 
we approximate as follows:

```
0	1/8	0
1/8	1/2	1/8
0	1/8	0
```

For image blur we can also use box kernel:

```
1/9	1/9	1/9
1/9	1/9	1/9
1/9	1/9	1/9
```

The derivative of the image can be simply calculated with filter like this:

```
-1 1
```

Now we will observe how the filtration is implemented inside a fragment shader.
The simple fragment shader that calculates a derivative of the image in this example
is shown below:

```
uniform sampler2D tex;
varying highp vec2 qt_TexCoord0;
uniform highp vec2 kernel;
uniform highp float texWidth;
void main() {
	highp float dx = 1.0 / texWidth;
	highp vec3 res0 = texture2D(tex, qt_TexCoord0.xy).rgb;
	highp vec3 res1 = texture2D(tex, vec2(qt_TexCoord0.x + dx, qt_TexCoord0.y)).rgb;
	highp vec3 res = res0 * kernel.x + res1 * kernel.y;
	gl_FragColor = vec4(res + 0.5, 1.0);
}
```

The kernel is here vec2(-1, 1). The texture containing current photo from the 
camera stream is called tex, and the coordinate of current pixel is stored in vec2 qt_TexCoord0.
The fragment shader runs exactly one main function for each pixel separately. Thanks 
to this we avoid any for cycles - as the shader programs runs in parallel, 
it does this for us automatically. So, we look the value of the current pixel,
and the pixel next to it on the right side and apply the values of the filter. 

###The task
To implement the Laplacian Edge detector minor changes to this example must be 
introduced. Try to figure out, how to implement the filtration with simple laplacian kernel:

```
0		-1/4		0
-1/4	1			-1/4
0		-1/4		0
```









