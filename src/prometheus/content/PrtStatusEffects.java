package prometheus.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.StatusEffect;

public class PrtStatusEffects implements ContentList {
    public static StatusEffect timeEater;

    public void load() {
        //this son of a bitch doesn't work. Fuck
        timeEater = new StatusEffect("time-eating"){{
            color = Color.valueOf("7b2999");
            damage = 0.001f;
            speedMultiplier = 0.001f;
            reloadMultiplier = 0.1f;
            buildSpeedMultiplier = 0.001f;
        }};
    }
}
