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
    color: "black";

    Rectangle{
        width: parent.width;
        height: parent.height;

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
            property variant tex: ShaderEffectSource { sourceItem: viewfinder; hideSource: true; }
			anchors.fill: parent;
            property point kernel: Qt.point(-1.0, 1.0);
            property real texWidth: viewfinder.width;
            property real texHeight: viewfinder.height;

			fragmentShader: "
			uniform sampler2D tex;
			varying highp vec2 qt_TexCoord0;
            uniform highp vec2 kernel;
            uniform highp float texWidth;
            uniform highp float texHeight;
            void main() {
                highp float dx = 1.0 / texWidth;
                highp float dy = 1.0 / texHeight;

                //left -> right derivative
                highp vec3 xy = texture2D(tex, qt_TexCoord0.xy).rgb;
                highp vec3 x1y = texture2D(tex, vec2(qt_TexCoord0.x + dx, qt_TexCoord0.y)).rgb;
                highp vec3 xm1y = texture2D(tex, vec2(qt_TexCoord0.x - dx, qt_TexCoord0.y)).rgb;
                highp vec3 xy1 = texture2D(tex, vec2(qt_TexCoord0.x, qt_TexCoord0.y + dy)).rgb;
                highp vec3 xym1 = texture2D(tex, vec2(qt_TexCoord0.x, qt_TexCoord0.y - dy)).rgb;
                highp vec3 res = xm1y * kernel.x + xy * kernel.y;
                res += xy * kernel.x + x1y * kernel.y;
                res += xym1 * kernel.x + xy * kernel.y;
                res += xy * kernel.x + xy1 * kernel.y;

                gl_FragColor = vec4(res + 0.5, 1.0);
            }
			"
		}

		Component.onCompleted: {
            //setup after animation effect is completed.
		}

    }
}
