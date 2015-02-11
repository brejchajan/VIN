package com.example.janbrejcha.computationalcamera;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * Created by janbrejcha on 10.02.15.
 */
public class CameraRenderer implements GLSurfaceView.Renderer {

    private SurfaceTexture texture;

    private TextureView.SurfaceTextureListener mSurfaceTextureListener;

    private static final String mVertexShader =
            "attribute vec3 vPos; " +
            "uniform highp mat3 transform;" +
            "varying highp vec3 texCoord0;" +
            "void main(){" +
            "   gl_Position = vec4((vPos * 2.0) - 1.0, 1.0);" +
            "   texCoord0 = vPos;" +
            "}";

    private static final String mFragmentShader =
            "#extension GL_OES_EGL_image_external : require\n" +
            "uniform samplerExternalOES tex;" +
            "varying highp vec3 texCoord0;" +
            "void main(){" +
            "   highp vec4 col = vec4(texture2D(tex, 1.0 - texCoord0.yx).rgb, 1.0);" +
            "   gl_FragColor = col;" +
            "}";

    private int vPosLoc = 0;
    private int transformLoc = 0;
    private int textureLoc = 0;
    private int textureID[] = new int[1];
    private int program;

    private float transformVals[];
    private int vbo[] = new int[1];

    private int vao[] = new int[1];

    private int texWidth = 200;
    private int texHeight = 200;

    private FloatBuffer vertexBuffer;

    private float vertices[];

    public void setTextureId(int id){
        textureID[0] = id;

    }

    public CameraRenderer(int width, int height)
    {
        textureID[0] = -1;
        texWidth = width;
        texHeight = height;

        vertices = new float[]{
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f
        };
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES31.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        GLES31.glEnable(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);


        int vertexShader = loadShader(GLES31.GL_VERTEX_SHADER, mVertexShader);
        int fragmentShader = loadShader(GLES31.GL_FRAGMENT_SHADER, mFragmentShader);
        program = GLES31.glCreateProgram();
        GLES31.glAttachShader(program, vertexShader);
        GLES31.glAttachShader(program, fragmentShader);
        GLES31.glLinkProgram(program);

        int[] linkStatus = new int[1];
        GLES31.glGetProgramiv(program, GLES31.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES31.GL_TRUE) {
            Log.e("Renderer", "Could not link program: ");
            Log.e("Renderer", GLES31.glGetProgramInfoLog(program));
            GLES31.glDeleteProgram(program);
            program = 0;
        }

        GLES31.glUseProgram(program);

        textureLoc = GLES31.glGetUniformLocation(program, "tex");
        transformLoc = GLES31.glGetUniformLocation(program, "transform");

        // generate one texture pointer and bind it as an external texture.
        GLES20.glGenTextures(1, textureID, 0);
        GLES31.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureID[0]);
        // No mip-mapping with camera source.
        GLES31.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_LINEAR);
        GLES31.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        // Clamp to edge is only option.
        GLES31.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES31.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        texture = new SurfaceTexture(textureID[0]);

        mSurfaceTextureListener.onSurfaceTextureAvailable(texture, (int)texWidth, (int)texHeight);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES31.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {

        GLES31.glClearColor(0.5f, 0.2f, 0.1f, 1.0f);
        GLES31.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        GLES31.glUseProgram(program);

        GLES31.glEnableVertexAttribArray(vPosLoc);
        GLES20.glVertexAttribPointer(vPosLoc, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        texture.updateTexImage();
        GLES31.glActiveTexture(GLES31.GL_TEXTURE0);

        GLES31.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureID[0]);
        GLES31.glProgramUniform1i(program, textureLoc, 0);

        GLES31.glUniformMatrix3fv(transformLoc, 1, false, transformVals, 0);

        GLES31.glDrawArrays(GLES31.GL_TRIANGLE_STRIP, 0, 4);
        GLES31.glDisableVertexAttribArray(vPosLoc);
    }

    private int loadShader(int type, String source){
        int shader = GLES31.glCreateShader(type);
        GLES31.glShaderSource(shader, source);
        GLES31.glCompileShader(shader);
        return shader;
    }

    public SurfaceTexture getSurfaceTexture(){
        return texture;
    }

    public void setSurfaceTextureListener(TextureView.SurfaceTextureListener listener){
        mSurfaceTextureListener = listener;
    }

    public void setTransform(Matrix transform){
        transformVals = new float[9];
        transform.getValues(transformVals);
    }
}
