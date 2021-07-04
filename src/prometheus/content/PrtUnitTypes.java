package prometheus.content;

import arc.func.Prov;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.ObjectMap.Entry;

import arc.util.Log;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.Vars;
import mindustry.content.Bullets;
import mindustry.ctype.ContentList;

import prometheus.ModAnnotations;
import prometheus.entities.abilities.active.SelfDestructionAbility;
import prometheus.entities.units.*;
import prometheus.tools.UnitAnnotationProcessor;
import prometheus.type.ArmorRechargeUnitType;
import prometheus.type.PrtUnitType;

public class PrtUnitTypes implements ContentList{

    /**
    Мануал для будущих поколений.
     Вы хотите добавить нового юнита (? extends Entityc)? Тогда...
     1. В unitList добавьте новый
        entry(Юнит.class, Юнит::new);

     2. Перед типом юнита (? extends UnitType или же просто UnitType) пишите:
        EntityMapping.nameMap.put("название-юнита", Юнит::new);
     */

    //naval
    public static UnitType castor, vega, nembus, arcturus, betelgeuse;

    //ground + passive ability
    @ModAnnotations.UnitDef(ArmorRechargeEntity.class)
    public static UnitType berserk;

    @ModAnnotations.UnitDef(RespawnEntity.class)
    public static UnitType timeEater;

    public void load(){
        berserk = new ArmorRechargeUnitType("berserk"){{
            health = 100;
            speed = 0.5f;
            rotateShooting = true;
            constructor = ArmorRechargeEntity::new;
            rotateSpeed = 2f;

            flying = false;
            canBoost = false;
        }};
        castor = new PrtUnitType("castor"){{

            constructor = UnitWaterMove::create;

            health = 90;

            speed = 1.1f;
            drag = 0.13f;
            hitSize = 9f;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            trailLength = 20;
            rotateShooting = false;
            armor = 2f;

            activeAbility = new SelfDestructionAbility(120f,340f,5*Vars.tilesize){{
                incendAmount = 5;
            }};

            weapons.add(new Weapon("castor-weapon"){{
                reload = 50f;
//                x = 5f;
                y = -5f;
                rotate = true;
                mirror = false;
                inaccuracy = 2f;
                rotateSpeed = 2f;
                shake = 1.4f;
                ejectEffect = Fx.casing2;
                shootSound = Sounds.bang;
                bullet = new ArtilleryBulletType(2.5f, 1,"missile"){{
                    keepVelocity = true;
                    width = 8f;
                    height = 8f;
                    splashDamageRadius = 16f;
                    splashDamage = 40f;
                    lifetime = 60f;
                    backColor = Pal.bulletYellowBack;
                    frontColor = Pal.bulletYellow;
                    hitEffect = Fx.blastExplosion;
                    despawnEffect = Fx.blastExplosion;
                }};
            }});

        }};

        vega = new PrtUnitType("vega"){{

            constructor = UnitWaterMove::create;

            health = 340;

            speed = 0.9f;
            drag = 0.15f;
            hitSize = 11f;
            armor = 4f;
            accel = 0.3f;
            rotateSpeed = 2.6f;
            rotateShooting = false;

            trailLength = 20;
            trailX = 5.5f;
            trailY = -4f;
            trailScl = 1.9f;

            activeAbility = new SelfDestructionAbility(8*60f,1200f,6*Vars.tilesize){{
                incendAmount = 8;
                bullets = 6;
                bullet = new ArtilleryBulletType(3,0){{
                    lifetime = 20;
                    splashDamage = 60f;
                    splashDamageRadius = Vars.tilesize*1f;
                }};
            }};

            weapons.add(new Weapon("vega-weapon"){{
                reload = 45f;
                x = 10f;
                y = -5f;
                rotate = true;
                inaccuracy = 5f;
                rotateSpeed = 1.6f;
                shake = 1.1f;
                ejectEffect = Fx.casing2;
                shootSound = Sounds.bang;
                bullet = new ArtilleryBulletType(3.3f, 0,"missile"){{
                    keepVelocity = true;
                    splashDamageRadius = 25f;
                    splashDamage = 25f;
                    lifetime = 60f;
                    backColor = Pal.bulletYellowBack;
                    frontColor = Pal.bulletYellow;
                    hitEffect = Fx.blastExplosion;
                    status = StatusEffects.burning;
                    pierce = true;
                    pierceCap = 2;

                    fragBullets = 2;
                    fragLifeMin = 0f;
                    fragCone = 30f;

                    fragBullet = new MissileBulletType(7f, 8){{
                        width = 5f;
                        height = 5f;
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 3;

                        lifetime = 15f;
                        hitEffect = Fx.hitBulletSmall;
                    }};

                }};
            }});

        }};

        nembus = new PrtUnitType("nembus"){{

            constructor = UnitWaterMove::create;

            health = 690;

            speed = 0.85f;
            accel = 0.2f;
            rotateSpeed = 1.8f;
            drag = 0.17f;
            hitSize = 16f;
            armor = 7f;
            rotateShooting = false;

            trailLength = 22;
            trailX = 7f;
            trailY = -9f;
            trailScl = 1.5f;

            activeAbility = new SelfDestructionAbility(12*60f,3200f,7f*Vars.tilesize){{
                bullets = 9;
                bullet = Bullets.fireball;
                bulletDamage = 70f;
            }};

            weapons.add(new Weapon("nembus-artillery"){{
                reload = 50f;
                x = 0;
                y = -10f;
                rotate = true;
                mirror = false;
                inaccuracy = 2f;
                rotateSpeed = 1.3f;
                shake = 1.9f;
                ejectEffect = Fx.casing2;
                shootSound = Sounds.bang;
                bullet = new ArtilleryBulletType(3.8f, 30f){{
                    keepVelocity = true;
                    splashDamageRadius = 25f;
                    splashDamage = 50f;
                    lifetime = 60f;
                    hitEffect = Fx.blastExplosion;

                    fragBullets = 3;

                    fragBullet = new BasicBulletType(6f, 20f){{
                        shrinkX = 0.08f;
                        width = 6f;
                        height = 6f;
                        lifetime = 11f;
                        hitEffect = Fx.hitBulletSmall;
                    }};

                }};
            }});

            weapons.add(new Weapon("nembus-missile"){{
                reload = 60f;
                x = 15;
                y = -15f;
                rotate = true;
                inaccuracy = 3f;
                rotateSpeed = 1.8f;
                shake = 1.2f;
                shootSound = Sounds.missile;
                bullet = new MissileBulletType(4f, 30f){{
                    keepVelocity = true;
                    splashDamageRadius = 30f;
                    splashDamage = 100f;
                    lifetime = 50f;
                    hitEffect = Fx.blastExplosion;
                    despawnEffect = Fx.blastsmoke;
                }};
            }});

        }};

        arcturus = new PrtUnitType("arcturus"){{

            constructor = UnitWaterMove::create;

            health = 8600;

            armor = 12f;

            speed = 0.73f;
            drag = 0.17f;
            hitSize = 39f;
            accel = 0.2f;
            rotateSpeed = 1.3f;
            rotateShooting = false;

            trailLength = 50;
            trailX = 18f;
            trailY = -21f;
            trailScl = 3f;

            activeAbility = new SelfDestructionAbility(32*60f,18000f,12*Vars.tilesize){{
                incendAmount = 20;

                status = StatusEffects.melting;
                statusDuration = 600f;

                bullets = 18;
                bullet = new ArtilleryBulletType(4,90){{
                    lifetime = 40;
                    splashDamage = 90f;
                    splashDamageRadius = Vars.tilesize*4;
                }};

            }};

        }};

        betelgeuse = new PrtUnitType("betelgeuse"){{

            constructor = UnitWaterMove::create;

            health = 17900;

            speed = 0.62f;
            drag = 0.18f;
            hitSize = 50f;
            armor = 16f;
            accel = 0.19f;
            rotateSpeed = 0.9f;
            rotateShooting = false;

            trailLength = 70;
            trailX = 23f;
            trailY = -32f;
            trailScl = 3.5f;

            activeAbility = new SelfDestructionAbility(120*60f,60000f,16*Vars.tilesize){{
                incendAmount = 20;

                status = StatusEffects.melting;
                statusDuration = 600f;

                bullets = 32;
                bullet = new ArtilleryBulletType(5,90){{
                    lifetime = 56;
                    splashDamage = 90f;
                    splashDamageRadius = Vars.tilesize*3;
                }};

            }};

        }};


        timeEater = new PrtUnitType("time-eater"){{

            constructor = RespawnEntity::new;

            speed = 0.5f;
            hitSize = 26f;
            rotateSpeed = 1.65f;
            health = 200;
            armor = 0f;
            mechStepParticles = true;
            canDrown = false;

            weapons.add(new Weapon("lol"){{
                reload = 60f;
                recoil = 4f;
                shake = 2f;
                ejectEffect = Fx.casing2;
                shootSound = Sounds.artillery;
                bullet = new BasicBulletType(4f, 8){{
                    lifetime = 110f;
                }

                    @Override
                    public void hitEntity(Bullet b, Hitboxc other, float initialHealth) {
                        Log.info("hitEntity1 "+other);
                        if(other instanceof Healthc){
                            Healthc enemy = (Healthc) other;
                            enemy.damage(enemy.maxHealth()*0.9f);
                            Log.info("hitEntity2 "+enemy);
                        }
                    }

                };
            }});

        }};


        try {
            UnitAnnotationProcessor.setMapping(this.getClass());
            Log.info("DONE WORK");
        }catch (IllegalAccessException e){
            Log.err("PIZDEC NACHALSYA");
            Log.err(e);
        }
    }

}
