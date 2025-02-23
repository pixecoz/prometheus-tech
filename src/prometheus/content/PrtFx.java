package prometheus.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;

import static arc.math.Angles.randLenVectors;

import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.gen.EffectState;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
import mindustry.graphics.*;

import prometheus.world.blocks.turrets.DroneBase;

public class PrtFx {
    public static final Effect
            dystopiaTrail = new Effect(37.0F, (e) -> {
        for (int i = 0; i < 2; ++i) {
            Draw.color(i == 0 ? PrtColors.dystopiaBackColor : PrtColors.dystopiaFrontColor);
            float m = i == 0 ? 1.0F : 0.5F;
            float rot = e.rotation + 180.0F;
            float w = 20.0F * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (35.0F + Mathf.randomSeedRange(e.id, 19.0F)) * m, rot);
            Drawf.tri(e.x, e.y, w, 15.0F * m, 90);
        }

    }),

    dystopiaShoot = new Effect(60.0F, (e) -> {
        e.scaled(32.0F, (b) -> {
            Draw.color(Color.white, PrtColors.dystopiaFrontColor, b.fin());
            Lines.stroke(b.fout() * 6.5F);
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
                float rot = e.rotation + Mathf.randomSeedRange(e.id + j, 50.0F);
                float w = 23.0F * e.fout() * m;
                Drawf.tri(e.x, e.y, w, (80.0F + Mathf.randomSeedRange(e.id + j, 40.0F)) * m, rot);
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
            randLenVectors(e.id, 25, 5.0F + e.fin() * 80.0F, e.rotation, 60.0F, (x, y) -> {
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
    orbitalLaserChargePyro = new Effect(80f, 100f, e -> {
        Draw.color(Pal.lightPyraFlame);
        Lines.stroke(2f * e.fout());
        Lines.circle(e.x, e.y, 100f);

        Lines.circle(e.x, e.y, 100f * e.fout());
    }),
    orbitalLaserChargeSurge = new Effect(80f, 100f, e -> {
        Draw.color(Pal.surge);
        Lines.stroke(2f * e.fout());
        Lines.circle(e.x, e.y, 100f);
        Lines.circle(e.x, e.y, 100f * e.fout());
        Lines.circle(e.x, e.y, 100f + 25f * e.fin());
    }),
    orbitalLaserChargePlast = new Effect(80, 100f, e -> {
        Draw.color(Pal.plastanium);
        Lines.stroke(2f * e.fout());
        Lines.circle(e.x, e.y, 100f);
        Lines.circle(e.x, e.y, 100f * e.fin());
        Lines.circle(e.x, e.y, 100f + 25f * e.fout());
    }),
    podSmoke = new Effect(120, e->{
        Draw.color(Pal.lighterOrange);
        Draw.alpha(Mathf.clamp(e.fout()*1.6f - Interp.pow3In.apply(e.rotation)*1.2f));
        Fill.circle(e.x, e.y, (1f + 6f * e.rotation) - e.fin()*4f);
    }),

    shootGreen = new Effect(30f, 100f, e -> {
        Draw.color(Pal.heal);
        Angles.randLenVectors((long) e.id * 2, Mathf.random(10, 15), e.fout(), (x, y) -> {
            Lines.lineAngle(e.x, e.y, Mathf.angle(x, y), e.fout() * 20f);
        });
    }),

    destroyLights = new Effect(60f, e -> {

    }),

    destroyCount = new Effect(120f, e -> {
        if (e.data instanceof Unit) {
            Unit u = (Unit) e.data;
            e.x = u.x;
            e.y = u.y;
            Draw.color(Color.valueOf("ff0000"));
            Lines.stroke(1f + u.hitSize() / 10f);
            Lines.swirl(e.x, e.y, u.hitSize() * 1.5f, e.fout());
        }
    }),

    empShoot = new Effect(100f, e -> {
        Draw.color(Color.white, Pal.lancerLaser, e.fin());
        float amount = 7f;

        Lines.stroke(e.fout() / 1.5f + 0.33f);
        Lines.circle(e.x, e.y, 100f);

        for (int i = 0; i < amount; i++) {
            float angle = i / amount * 360f;

            Drawf.tri(e.x + Angles.trnsx(angle, 100f), e.y + Angles.trnsy(angle, 100f), 6f, 50f * e.fout(), angle - 180);
        }
    });

}
