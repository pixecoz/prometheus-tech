package prometheus.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.util.Log;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class PrtStatusEffects implements ContentList {
    public static StatusEffect timeEater;
    @Override
    public void load() {
        Log.info("Amogus");
        timeEater = new StatusEffect("time-eating"){{
            color = Color.valueOf("7b2999");
            damage = 0f;
            speedMultiplier = 0f;
            reloadMultiplier = 0.1f;
            buildSpeedMultiplier = 0f;
            effectChance = 1f;

            effect = new Effect(100f, 100f, e->{
                Draw.color(Pal.lighterOrange);
                Lines.circle(e.x, e.y, 20);
            });

            init(()->{
                opposite(StatusEffects.overclock, StatusEffects.overdrive);
            });

        }};
    }
}
