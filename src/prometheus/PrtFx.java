package prometheus;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.*;
import mindustry.graphics.Drawf;

import static arc.graphics.g2d.Draw.rect;


public class PrtFx {
    public static final Effect
            dystopiaTrail = new Effect(37.0F, (e) -> {
        for (int i = 0; i < 2; ++i) {
            Draw.color(i == 0 ? PrtColors.dystopiaBackColor : PrtColors.dystopiaFrontColor);
            float m = i == 0 ? 1.0F : 0.5F;
            float rot = e.rotation + 180.0F;
            float w = 20.0F * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (35.0F + Mathf.randomSeedRange((long) e.id, 19.0F)) * m, rot);
            Drawf.tri(e.x, e.y, w, 15.0F * m, 60);
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
    });

}
