package prometheus.entities;

import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.geom.Vec2;
import arc.util.Tmp;

import jdk.nashorn.internal.ir.Block;
import mindustry.entities.Effect;
import mindustry.gen.BlockUnitc;
import mindustry.gen.Posc;
import mindustry.graphics.Pal;

public class TurretEffects {

    public static void beamsCharge(float x, float y, Posc t, float rotation){
        Effect effect = new Effect(100f,100f, e-> {
            Tmp.v1.set(0, 10);
            Tmp.v2.set(0, -10);
            Tmp.v3.set(10, 0);

            float len1 = Tmp.v1.set(Angles.trnsx(rotation + 90, Tmp.v1.x, Tmp.v1.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v1.x, Tmp.v1.y) + e.y).dst(t.x(), t.y());
            float len2 = Tmp.v2.set(Angles.trnsx(rotation + 90, Tmp.v2.x, Tmp.v2.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v2.x, Tmp.v2.y) + e.y).dst(t.x(), t.y());
            float len3 = Tmp.v3.set(Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y) + e.y).dst(t.x(), t.y());

            float ang1 = (Tmp.v3.angleTo(t.x(), t.y())) - 15 * e.fout();
            float ang2 = (Tmp.v3.angleTo(t.x(), t.y())) + 15 * e.fout();
            float ang3 = (Tmp.v1.angleTo(t.x(), t.y())) + 30 * e.fout();
            float ang4 = (Tmp.v2.angleTo(t.x(), t.y())) - 30 * e.fout();

            Tmp.v1.set(0, 10);
            Tmp.v2.set(0, -10);
            Tmp.v3.set(10, 0);

            Draw.color(Pal.surge);

            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v1.x, Tmp.v1.y), e.y + Angles.trnsy(rotation + 90, Tmp.v1.x, Tmp.v1.y), ang3, len1);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v2.x, Tmp.v2.y), e.y + Angles.trnsy(rotation + 90, Tmp.v2.x, Tmp.v2.y), ang4, len2);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y), e.y + Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y), ang1, len3);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y), e.y + Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y), ang2, len3);
        });

        effect.at(x, y, rotation);
    }
    public static void beamsCharge(float x, float y, BlockUnitc t, float rotation){
        Effect effect = new Effect(100f,100f, e-> {
            Tmp.v1.set(0, 10);
            Tmp.v2.set(0, -10);
            Tmp.v3.set(10, 0);

            float len1 = Tmp.v1.set(Angles.trnsx(rotation + 90, Tmp.v1.x, Tmp.v1.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v1.x, Tmp.v1.y) + e.y).dst(t.aimX(), t.aimY());
            float len2 = Tmp.v2.set(Angles.trnsx(rotation + 90, Tmp.v2.x, Tmp.v2.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v2.x, Tmp.v2.y) + e.y).dst(t.aimX(), t.aimY());
            float len3 = Tmp.v3.set(Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y) + e.y).dst(t.aimX(), t.aimY());

            float ang1 = (Tmp.v3.angleTo(t.aimX(), t.aimY())) - 15 * e.fout();
            float ang2 = (Tmp.v3.angleTo(t.aimX(), t.aimY())) + 15 * e.fout();
            float ang3 = (Tmp.v1.angleTo(t.aimX(), t.aimY())) + 30 * e.fout();
            float ang4 = (Tmp.v2.angleTo(t.aimX(), t.aimY())) - 30 * e.fout();

            Tmp.v1.set(0, 10);
            Tmp.v2.set(0, -10);
            Tmp.v3.set(10, 0);

            Draw.color(Pal.surge);

            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v1.x, Tmp.v1.y), e.y + Angles.trnsy(rotation + 90, Tmp.v1.x, Tmp.v1.y), ang3, len1);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v2.x, Tmp.v2.y), e.y + Angles.trnsy(rotation + 90, Tmp.v2.x, Tmp.v2.y), ang4, len2);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y), e.y + Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y), ang1, len3);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y), e.y + Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y), ang2, len3);
        });

        effect.at(x, y, rotation);
    }

    public static void beamsCharge(float x, float y, Vec2 t, float rotation){
        Effect effect = new Effect(100f,100f, e-> {
            Tmp.v1.set(0, 10);
            Tmp.v2.set(0, -10);
            Tmp.v3.set(10, 0);

            float len1 = Tmp.v1.set(Angles.trnsx(rotation + 90, Tmp.v1.x, Tmp.v1.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v1.x, Tmp.v1.y) + e.y).dst(t.x, t.y);
            float len2 = Tmp.v2.set(Angles.trnsx(rotation + 90, Tmp.v2.x, Tmp.v2.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v2.x, Tmp.v2.y) + e.y).dst(t.x, t.y);
            float len3 = Tmp.v3.set(Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y) + e.x, Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y) + e.y).dst(t.x, t.y);

            float ang1 = (Tmp.v3.angleTo(t.x, t.y)) - 15 * e.fout();
            float ang2 = (Tmp.v3.angleTo(t.x, t.y)) + 15 * e.fout();
            float ang3 = (Tmp.v1.angleTo(t.x, t.y)) + 30 * e.fout();
            float ang4 = (Tmp.v2.angleTo(t.x, t.y)) - 30 * e.fout();

            Tmp.v1.set(0, 10);
            Tmp.v2.set(0, -10);
            Tmp.v3.set(10, 0);

            Draw.color(Pal.surge);

            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v1.x, Tmp.v1.y), e.y + Angles.trnsy(rotation + 90, Tmp.v1.x, Tmp.v1.y), ang3, len1);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v2.x, Tmp.v2.y), e.y + Angles.trnsy(rotation + 90, Tmp.v2.x, Tmp.v2.y), ang4, len2);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y), e.y + Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y), ang1, len3);
            Lines.lineAngle(e.x + Angles.trnsx(rotation + 90, Tmp.v3.x, Tmp.v3.y), e.y + Angles.trnsy(rotation + 90, Tmp.v3.x, Tmp.v3.y), ang2, len3);
        });

        effect.at(x, y, rotation);
    }

}
