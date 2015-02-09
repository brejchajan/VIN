import QtQuick 2.4
import QtQuick.Controls 1.3
import QtQuick.Window 2.2
import QtQuick.Dialogs 1.2
import QtMultimedia 5.4



ApplicationWindow {
    title: qsTr("Hello World")
    width: Screen.width;
    height: Screen.height;
    visible: true

    Camera {
        id: camera;

        focus {
            focusMode: Camera.FocusContinuous;
            focusPointMode: Camera.FocusPointCenter;
        }
    }


    VideoOutput {
        id: viewfinder
        anchors.fill: parent;
        source: camera
    }

    ShaderEffect {
        id: shader
        //property variant source: ShaderEffectSource { sourceItem: viewfinder; hideSource: true; }
        property variant tex: ShaderEffectSource { sourceItem: viewfinder; hideSource: true; }
        anchors.fill: parent;
        //property real wiggleAmount: 0.005
        property point center: Qt.point(0.5, 0.5)
        property real scale: 1.0
        property int iter: 1000
        //Behavior on wiggleAmount { PropertyAnimation { duration: 20000 } }



        fragmentShader: "
        uniform sampler2D tex;
        varying highp vec2 qt_TexCoord0;
        uniform highp vec2 center;
        uniform highp float scale;
        uniform int iter;

        void main() {
            highp vec2 z, c;

            c.x = 1.3333 * (qt_TexCoord0.x - 0.5) * scale - center.x;
            c.y = (qt_TexCoord0.y - 0.5) * scale - center.y;

            int i;
            z = c;
            for(i=0; i<iter; i++) {
                highp float x = (z.x * z.x - z.y * z.y) + c.x;
                highp float y = (z.y * z.x + z.x * z.y) + c.y;

                if((x * x + y * y) > 4.0) break;
                z.x = x;
                z.y = y;
            }
            highp float t1 = (i == iter ? 0.0 : float(i) / 100.0);
            highp vec2 res = vec2(t1, t1);
            //highp vec4 res = vec4((i == iter ? 0.0 : 1.0), 0.0, 0.0, 1.0);
            gl_FragColor = texture2D(tex, res.xy);
        }
        "
//(i == iter ? 0.0 : float(i)) / 100.0, (i == iter ? 0.0 : float(i)) / 100.0
        /*
        varying highp vec2 qt_TexCoord0;
        uniform sampler2D source;
        uniform highp float wiggleAmount;
        void main(void)
        {
            highp vec2 wiggledTexCoord = qt_TexCoord0;
            //wiggledTexCoord.s += sin(4.0 * 3.141592653589 * wiggledTexCoord.t) * wiggleAmount;
            gl_FragColor = texture2D(source, wiggledTexCoord.st);
        }*/

    }

    Component.onCompleted: {
        //shader.wiggleAmount = 0.1;

    }


}
