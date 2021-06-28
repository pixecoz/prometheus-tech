package prometheus.entities.abilities.active;

import mindustry.gen.Unit;

public abstract class ActiveAbility {
    public void draw(Unit unit){}
    public void update(Unit unit){}
    public void activate(Unit unit){}
}
