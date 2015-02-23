package com.example.janbrejcha.computationalcamera;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by janbrejcha on 10.02.15.
 * Use this class to catch UIEvents like touches and send them to mRenderer to handle the visual
 * response for these events. See http://developer.android.com/training/graphics/opengl/touch.html.
 */
public class CameraGLSurfaceView extends GLSurfaceView{

    private final GLSurfaceView.Renderer mRenderer;

    public CameraGLSurfaceView(Context context, GLSurfaceView.Renderer renderer){
        super(context);
        mRenderer = renderer;
        setEGLContextClientVersion(3);
        setRenderer(renderer);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
}
