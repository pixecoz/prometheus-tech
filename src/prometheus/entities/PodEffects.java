package prometheus.entities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.entities.Effect;
import mindustry.entities.Fires;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Tile;

import static arc.graphics.g2d.Lines.lineAngle;
import static arc.math.Angles.randLenVectors;

public class PodEffects {
    public static void podFallEffect(float x, float y, float fireRadius, float shakeIntensity, float shakeDuration){
        Effect effect = new Effect(150f, 100f, e->{
            TextureRegion pod = Core.atlas.find("launchpod");
            float alpha = 1f - e.fout(Interp.pow5Out);
            float scale =  (1f - alpha) * 2f + 1f;
            float cx = e.x, cy = e.y;
            float rotation = e.fin() * 145f;
            float rw = pod.width * Draw.scl * scale, ry = pod.height * Draw.scl * scale;
            Draw.color(Color.clear, Color.red, e.fin());
            Draw.z(Layer.flyingUnit + 1);
            Draw.alpha(alpha);
            Draw.rect(pod, cx, cy, rw, ry, rotation);
            if(e.fin() > 0.95) {
                if (shakeDuration != 0 && shakeIntensity != 0)
                    Effect.shake(50f, 120f, e.x, e.y);
                if (fireRadius != 0) {
                    for (int i = 0; i < Mathf.random(2, 6); i++)
                        Fires.create(Vars.world.tileWorld(e.x + Mathf.random(-3 * 8, 3 * 8), e.y + Mathf.random(-3 * 8, 3 * 8)));
                }
            }
        });
        effect.at(x, y);
    }

    public static void podDust(float ex, float ey){
        Tile t = Vars.world.tileWorld(ex, ey);
        Effect effect = new Effect(80, 500f, e->{
            Draw.color(t.floor().mapColor);

            Lines.stroke(2f * e.fout());
            Lines.circle(e.x, e.y, 100f);
            Lines.circle(e.x, e.y, 100f * e.fin());
            Lines.circle(e.x, e.y, 100f + 25f * e.fout());

            float intensity = 6.8f;

            Draw.alpha(0.7f);
            for(int i = 0; i < 4; i++){
                Mathf.rand.setSeed(e.id*2 + i);
                float lenScl = 0.4f;
                int fi = i;
                e.scaled(e.lifetime * 0.9f, b -> {
                    randLenVectors(b.id + fi - 1, b.fin(Interp.pow5Out), (int)(1.5f * intensity), 22f * intensity, (x, y, in, out) -> {
                        float fout = b.fout(Interp.pow5Out) * Mathf.rand.random(0.5f, 1f);
                        float rad = fout * ((2f + intensity) * 2.35f);
                        Fill.circle(b.x + x, b.y + y, rad);
                        Drawf.light(b.x + x, b.y + y, rad * 2.5f, Pal.plastanium, 0.5f);
                    });
                    Draw.z(Layer.effect + 0.001f);
                    randLenVectors(b.id + 1, b.finpow() + 0.001f, (int)(8 * intensity), 28f * intensity, (x, y, in, out) -> {
                        lineAngle(b.x + x, b.y + y, Mathf.angle(x, y), 1f + out * (4f + intensity));
                        Drawf.light(b.x + x, b.y + y, (out * 4 * (3f + intensity)) * 3.5f, Draw.getColor(), 0.8f);
                    });
                });
            }
        });
        effect.at(ex, ey);
    }
}
