package prometheus.entities;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.graphics.Color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.math.Angles.randLenVectors;

import arc.util.Interval;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.Call;
import mindustry.graphics.*;
import mindustry.world.Tile;

import mindustry.Vars;
import prometheus.world.blocks.turrets.DroneBase;

public class PodEffects {
    public static void podLaunchEffect(TextureRegion pod, float x, float y){
        Effect effect = new Effect(150, 100f, e->{
        Interval in = new Interval();
        float alpha = e.fout(Interp.pow5Out);
        float scale = (1f - alpha) * 1.3f + 1f;
        float cx = e.x + e.fin(Interp.pow2In) * (12f + Mathf.randomSeedRange(e.id + 3, 4f));
        float cy = e.y + e.fin(Interp.pow5In) * (100f + Mathf.randomSeedRange(e.id + 2, 30f));
        float rotation = e.fin() * (130f + Mathf.randomSeedRange(e.id, 50f));
        float r = 3f;

        if (in.get(4f - e.fin() * 2f)) {
            Fx.rocketSmoke.at(cx + Mathf.range(r), cy + Mathf.range(r), e.fin());
        }

        Draw.z(Layer.effect + 0.001f);

        Draw.color(Pal.engine);

        float rad = 0.2f + e.fslope();

        Fill.light(cx, cy, 10, 25f * (rad + scale - 1f), Tmp.c2.set(Pal.engine).a(alpha), Tmp.c1.set(Pal.engine).a(0f));

        Draw.alpha(alpha);
        for (int i = 0; i < 4; i++) {
            Drawf.tri(cx, cy, 6f, 40f * (rad + scale - 1f), i * 90f + rotation);
        }

        Draw.color();

        Draw.z(Layer.weather - 1);

        float rw = pod.width * Draw.scl * scale, rh = pod.height * Draw.scl * scale;

        Draw.alpha(alpha);
        Draw.rect(pod, cx, cy, rw, rh, rotation);

        Tmp.v1.trns(225f, e.fin(Interp.pow3In) * 250f);

        Draw.z(Layer.flyingUnit + 1);
        Draw.color(0, 0, 0, 0.22f * alpha);
        Draw.rect(pod, cx + Tmp.v1.x, cy + Tmp.v1.y, rw, rh, rotation);

        Draw.reset();
        });
        effect.at(x, y);
    }
    public static void podFallEffect(TextureRegion pod, float x, float y, float fireRadius, float shakeIntensity, float shakeDuration){
        Effect effect = new Effect(150f, 100f, e->{
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
        Effect effect = new Effect(1200, 500f, e->{
            Draw.color(t.floor().mapColor);
            e.scaled(e.lifetime / 15, b->{
                Lines.stroke(2f * e.fout());
                Lines.circle(e.x, e.y, 100f);
                Lines.circle(e.x, e.y, 100f * e.fin());
                Lines.circle(e.x, e.y, 100f + 25f * e.fout());
            });

            float intensity = 6.8f;

            Draw.alpha(0.7f);
            for(int i = 0; i < 4; i++){
                Mathf.rand.setSeed(e.id*2 + i);
                float lenScl = Mathf.random(0.1f, 0.5f);
                int fi = i;
                e.scaled(e.lifetime * lenScl, b -> {
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
    public static void podDust(float ex, float ey, int rotation, Color color){
        Tile t = Vars.world.tileWorld(ex, ey);
        Effect effect = new Effect(1200, 500f, e->{
            Draw.color(t.floor().mapColor);
            e.scaled(e.lifetime / 15, b->{
                Lines.stroke(2f * e.fout());
                Lines.circle(e.x, e.y, 100f);
                Lines.circle(e.x, e.y, 100f * e.fin());
                Lines.circle(e.x, e.y, 100f + 25f * e.fout());
            });

            float intensity = 6.8f;

            Draw.alpha(0.7f);
            for(int i = 0; i < 4; i++){
                Mathf.rand.setSeed(e.id*2 + i);
                float lenScl = Mathf.random(0.1f, 0.5f);
                int fi = i;
                e.scaled(e.lifetime * lenScl, b -> {
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
        Call.effect(effect, ex, ey, rotation, color);
    }
}
