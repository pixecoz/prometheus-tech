package prometheus.graphics;

import arc.Core;
import arc.files.Fi;
import arc.graphics.gl.Shader;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import mindustry.Vars;
import mindustry.graphics.Shaders;

public class PrtShaders {

    public static PlanetaryDestroyerShader destroyerSphere;
    public static PlanetaryDestroyerShader destroyerLights;

    public static void init(){
        destroyerSphere = new PlanetaryDestroyerShader(true);
        destroyerLights = new PlanetaryDestroyerShader(false);
    }

    public static class PlanetaryDestroyerShader extends Shader {
        boolean sin;

        public PlanetaryDestroyerShader(boolean sin){
            super( getShaderFi("screenspace" + ".vert"),  Vars.tree.get("shaders/planetarydestroyer.frag"));
            this.sin = sin;
        }

        @Override
        public void apply() {
            setUniformf("u_time", Time.time / Scl.scl(1f));
        }
    }

    public static Fi getShaderFi(String file){
        return Core.files.internal("shaders/" + file);
    }

}
