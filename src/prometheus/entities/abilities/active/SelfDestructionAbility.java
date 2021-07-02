package prometheus.entities.abilities.active;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Call;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import prometheus.content.PrtFx;

public class SelfDestructionAbility extends ActiveAbility {

    public float delay;
    public float damage;
    public float radius;
    public int incendAmount;

    public BulletType bullet;
    public int bullets;
    public float minBulletVel = 0.6f;
    public float maxBulletVel = 1.1f;
    public float bulletDamage = -1;

    public StatusEffect status;
    public float statusDuration;

    public Effect lightsEffect = PrtFx.destroyLights;
    public Effect countEffect = PrtFx.destroyCount;

    public SelfDestructionAbility(float delay, float damage, float radius) {
        this.delay = delay;
        this.damage = damage;
        this.radius = radius;
        countEffect = destroyCountEffect(delay);
    }

    @Override
    public void activate(Unit unit) {
        lightsEffect.at(unit.x, unit.y, 0, unit);
        countEffect.at(unit.x, unit.y, 0, unit);

        Time.run(delay, () -> {

            Damage.damage(unit.team, unit.x, unit.y, radius, damage);

            if (bullet != null) {
                for (int i = 0; i < bullets; i++) {
                    Call.createBullet(bullet, unit.team, unit.x, unit.y, Mathf.random(360f), bulletDamage, Mathf.random(minBulletVel, maxBulletVel), 1);
                }
            }

            Damage.createIncend(unit.x, unit.y, radius, incendAmount);

            if (status != null) Damage.status(unit.team, unit.x, unit.y, radius, status, statusDuration, true, true);

            unit.kill();
        });


    }

    //TODO может сделать шейдер, который будет отрисовывать окружность не так криво?
    public static Effect destroyCountEffect(float delay) {
        return new Effect(delay, e -> {
            if (e.data instanceof Unit) {
                Unit u = (Unit) e.data;
                e.x = u.x;
                e.y = u.y;
                Draw.color(Color.valueOf("ff0000"));
                Lines.stroke(1f + u.hitSize() / 10f);
                Lines.swirl(e.x, e.y, u.hitSize() * 1.5f, e.fout());
            }
        });
    }

}
