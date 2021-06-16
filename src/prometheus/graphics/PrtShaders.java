package prometheus.graphics;

import arc.scene.ui.layout.Scl;
import arc.util.Time;
import mindustry.graphics.Shaders;

public class PrtShaders {

    public static PlanetaryDestroyerShader destroyerSphere;
    public static PlanetaryDestroyerShader destroyerLights;

    public static void init(){
        destroyerSphere = new PlanetaryDestroyerShader(true);
        destroyerLights = new PlanetaryDestroyerShader(false);
    }

    public static class PlanetaryDestroyerShader extends Shaders.LoadShader{
        boolean sin;

        public PlanetaryDestroyerShader(boolean sin){
            super("planetarydestroyer","screenspace");
            this.sin = sin;
        }

        @Override
        public void apply() {
            setUniformf("u_time", Time.time / Scl.scl(1f));
        }
    }
}
