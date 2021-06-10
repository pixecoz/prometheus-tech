package prometheus.content;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Interval;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.Tex;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import prometheus.world.blocks.turrets.DroneBase;

public class PrtFx {
    public static final Effect
            dystopiaTrail = new Effect(37.0F, (e) -> {
        for (int i = 0; i < 2; ++i) {
            Draw.color(i == 0 ? PrtColors.dystopiaBackColor : PrtColors.dystopiaFrontColor);
            float m = i == 0 ? 1.0F : 0.5F;
            float rot = e.rotation + 180.0F;
            float w = 20.0F * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (35.0F + Mathf.randomSeedRange((long) e.id, 19.0F)) * m, rot);
            Drawf.tri(e.x, e.y, w, 15.0F * m, 90);
        }

    }),

    dystopiaShoot = new Effect(60.0F, (e) -> {
        e.scaled(32.0F, (b) -> {
            Draw.color(Color.white, PrtColors.dystopiaFrontColor, b.fin());
            Lines.stroke(b.fout() * 5.0F + 1.7F);
            Lines.circle(b.x, b.y, b.fin() * 50.0F);
            Lines.square(e.x, e.y, b.fin() * 40.0F, 60);
        });
        Draw.color(PrtColors.dystopiaFrontColor);
        int[] var1 = Mathf.signs;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            int i = var1[var3];
            Drawf.tri(e.x, e.y, 13.0F * e.fout(), 85.0F, e.rotation + 90.0F * (float) i);
            Drawf.tri(e.x, e.y, 11.0F * e.fout(), 50.0F, e.rotation + 20.0F * (float) i);
            Drawf.tri(e.x, e.y, 5.0F * e.fout(), 60.0F, e.rotation + 12.0F * (float) i);
            Drawf.tri(e.x, e.y, 8.0F * e.fout(), 100.0F, e.rotation + 120.0F * (float) i);
        }

    }),

    dystopiaHit = new Effect(20.0F, 200.0F, (e) -> {
        Draw.color(PrtColors.dystopiaColor);

        for (int i = 0; i < 2; ++i) {
            Draw.color(i == 0 ? PrtColors.dystopiaColor : PrtColors.dystopiaFrontColor);
            float m = i == 0 ? 1.0F : 0.5F;

            for (int j = 0; j < 5; ++j) {
                float rot = e.rotation + Mathf.randomSeedRange((long) (e.id + j), 50.0F);
                float w = 23.0F * e.fout() * m;
                Drawf.tri(e.x, e.y, w, (80.0F + Mathf.randomSeedRange((long) (e.id + j), 40.0F)) * m, rot);
                Drawf.tri(e.x, e.y, w, 20.0F * m, rot + 180.0F);
                Drawf.circles(e.x, e.y, 40);
            }
        }

        e.scaled(10.0F, (c) -> {
            Draw.color(PrtColors.dystopiaColor);
            Lines.stroke(c.fout() * 2.0F + 0.2F);
            Lines.circle(e.x, e.y, c.fin() * 30.0F);
        });
        e.scaled(12.0F, (c) -> {
            Draw.color(PrtColors.dystopiaColor);
            Angles.randLenVectors((long) e.id, 25, 5.0F + e.fin() * 80.0F, e.rotation, 60.0F, (x, y) -> {
                Fill.square(e.x + x, e.y + y, c.fout() * 3.0F, 45.0F);
            });
        });
    }),

    orbitalLaserCharge = new Effect(80f, 100f, e -> {
        Draw.color(Pal.lancerLaser);
        Lines.stroke(2f * e.fout());
        Lines.circle(e.x, e.y, 100f);

        Lines.circle(e.x, e.y, 100f * e.fin());
    }),
    orbitalLaserChargePyro = new Effect(80f, 100f, e->{
        Draw.color(Pal.lightPyraFlame);
        Lines.stroke(2f * e.fout());
        Lines.circle(e.x, e.y, 100f);

        Lines.circle(e.x, e.y, 100f * e.fout());
    }),
    orbitalLaserChargeSurge = new Effect(80f, 100f, e->{
        Draw.color(Pal.surge);
        Lines.stroke(2f * e.fout());
        Lines.circle(e.x, e.y, 100f);
        Lines.circle(e.x, e.y, 100f * e.fout());
        Lines.circle(e.x, e.y, 100f + 25f * e.fin());
    }),
    orbitalLaserChargePlast = new Effect(80f, 100f, e->{
        Draw.color(Pal.plastanium);
        Lines.stroke(2f * e.fout());
        Lines.circle(e.x, e.y, 100f);
        Lines.circle(e.x, e.y, 100f * e.fout());
        Lines.circle(e.x, e.y, 100f + 25f * e.fout());
    }),

    launchPodLaunch = new Effect(150f, 100f, e->{
        Interval in = new Interval();
        float alpha = e.fout(Interp.pow5Out);
        float scale = (1f - alpha) * 1.3f + 1f;
        float cx = e.x + e.fin(Interp.pow2In) * (12f + Mathf.randomSeedRange(e.id + 3, 4f));
        float cy = e.y + e.fin(Interp.pow5In) * (100f + Mathf.randomSeedRange(e.id + 2, 30f));
        float rotation = e.fin() * (130f + Mathf.randomSeedRange(e.id, 50f));
        float r = 3f;

        if(in.get(4f - e.fin()*2f)){
            Fx.rocketSmoke.at(cx + Mathf.range(r), cy + Mathf.range(r), e.fin());
        }

        Draw.z(Layer.effect + 0.001f);

        Draw.color(Pal.engine);

        float rad = 0.2f + e.fslope();

        Fill.light(cx, cy, 10, 25f * (rad + scale-1f), Tmp.c2.set(Pal.engine).a(alpha), Tmp.c1.set(Pal.engine).a(0f));

        Draw.alpha(alpha);
        for(int i = 0; i < 4; i++){
            Drawf.tri(cx, cy, 6f, 40f * (rad + scale-1f), i * 90f + rotation);
        }

        Draw.color();

        Draw.z(Layer.weather - 1);

        TextureRegion region = DroneBase.podRegion;
        float rw = region.width * Draw.scl * scale, rh = region.height * Draw.scl * scale;

        Draw.alpha(alpha);
        Draw.rect(region, cx, cy, rw, rh, rotation);

        Tmp.v1.trns(225f, e.fin(Interp.pow3In) * 250f);

        Draw.z(Layer.flyingUnit + 1);
        Draw.color(0, 0, 0, 0.22f * alpha);
        Draw.rect(region, cx + Tmp.v1.x, cy + Tmp.v1.y, rw, rh, rotation);

        Draw.reset();
    }),

    launchPodFall = new Effect(150f, 100f, e->{
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
        if(e.fin() > 0.95){
            Effect.shake(50f, 120f, e.x, e.y);
            for (int i = 0; i < Mathf.random(2, 6); i++)
                Fires.create(Vars.world.tileWorld(e.x + Mathf.random(-3 * 8, 3 * 8), e.y + Mathf.random(-3 * 8, 3 * 8)));
        }
    });
}
