package prometheus.content;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.gen.Sounds;
import mindustry.world.blocks.power.*;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.production.GenericSmelter;
import mindustry.world.meta.BuildVisibility;
import prometheus.world.blocks.turrets.DroneBase;
import prometheus.world.meta.PodStat;

public class PrtBlocks implements ContentList {

    public static Block

            platinumForge, plutoniumForge, magnetiteKiln,
            darkFlare, dystopia,
            plutoniumReactor,
            platinumWall, platinumWalLarge, magnetiteWall, magnetiteWallLarge,
            droneBase;

    public void load() {

        /*platinumForge = new GenericSmelter("platinum-forge") {
            {
                this.localizedName = "Platinum Forge";
                this.description = "Make Platinum from Titanium and Lead.";
                this.health = 220;
                this.liquidCapacity = 0;
                this.size = 3;
                this.hasPower = true;
                this.hasLiquids = false;
                this.hasItems = true;
                this.craftTime = 90;
                this.updateEffect = Fx.plasticburn;
                this.consumes.power(2.5f);
                this.consumes.items(ItemStack.with(Items.lead, 2, Items.titanium, 1));
                this.requirements(Category.crafting, ItemStack.with(Items.plastanium, 80, Items.titanium, 100, Items.metaglass, 120, Items.silicon, 200, Items.graphite, 200));
                this.outputItem = new ItemStack(PrtItems.platinum, 1);
            }
        };*/

        plutoniumForge = new GenericSmelter("plutonium-forge") {{
            localizedName = "Plutonium Forge";
            description = "Make Plutonium from Plastanium and Lead.";
            health = 360;
            liquidCapacity = 0;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            hasItems = true;
            craftTime = 120;
            updateEffect = Fx.plasticburn;
            consumes.power(3.5f);
            consumes.items(ItemStack.with(Items.lead, 3, Items.plastanium, 1));
            requirements(Category.crafting, ItemStack.with(Items.copper, 10));
            outputItem = new ItemStack(PrtItems.plutonium, 1);
        }};

        magnetiteKiln = new GenericSmelter("magnetite-kiln") {{
            localizedName = "Magnetite Kiln";
            description = "Make Magnetite from Platinum, Copper and Surge Alloy.";
            health = 360;
            liquidCapacity = 0;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            hasItems = true;
            craftTime = 180;
            updateEffect = Fx.generatespark;
            consumes.power(7f);
            consumes.items(ItemStack.with(Items.surgeAlloy, 1, PrtItems.platinum, 1, Items.copper, 5));
            requirements(Category.crafting, ItemStack.with(Items.copper, 10));
            outputItem = new ItemStack(PrtItems.magnetite, 1);
        }};

        darkFlare  = new ItemTurret("dark-flare") {{
            localizedName = "Dark Flare";
            description = "Fires a beam of death at enemies. Total destruction.";
            requirements(Category.turret, ItemStack.with(PrtItems.magnetite, 470, Items.silicon, 500, Items.surgeAlloy, 220, Items.thorium, 420, Items.phaseFabric, 230, PrtItems.platinum, 250));
            ammo(
                    Items.surgeAlloy, new LaserBulletType() {
                        {
                            length = 110;
                            damage = 300;
                            width = 60;
                            lifetime = 30;
                            lightningSpacing = 34;
                            lightningLength = 6;
                            lightningDelay = 1;
                            lightningLengthRand = 21;
                            lightningDamage = 45;
                            lightningAngleRand = 30;
                            largeHit = true;
                            shootEffect = Fx.lancerLaserShoot;
                            collidesTeam = true;
                            sideAngle = 15;
                            sideWidth = 0;
                            sideLength = 0;
                            lightningColor = Color.valueOf("161616");
                            colors = new Color[]{Color.valueOf("161616"), Color.valueOf("262626"), Color.valueOf("333333")};
                        }
                    }
                    /*Items.plastic, new LaserBulletType() {
                        {
                            length = 240;
                            damage = 180;
                            width = 60;
                            lifetime = 30;
                            largeHit = true;
                            shootEffect = ModFx.purpleLaserChargeSmall;
                            collidesTeam = true;
                            sideAngle = 15;
                            sideWidth = 0;
                            sideLength = 0;
                            lightningColor = Color.valueOf("d5b2ed");
                            colors = new Color[]{Color.valueOf("d5b2ed"), Color.valueOf("9671b1"), Color.valueOf("a17dcd")};
                        }
                    }*/
            );
            reloadTime = 160;
            shots = 5;
            burstSpacing = 2;
            inaccuracy = 5;
            shootSound = Sounds.laser;
            range = 220;
            size = 5;
            health = 2500;
        }};
        dystopia = new ItemTurret("dystopia"){{
            localizedName = "Dystopia";
            description = "";
            float brange = range = 500f;

            requirements(Category.turret, ItemStack.with(Items.copper, 1000, Items.metaglass, 600, Items.surgeAlloy, 300, Items.plastanium, 200, Items.silicon, 600));
            ammo(
                    Items.surgeAlloy, new PointBulletType(){{
                        shootEffect = PrtFx.dystopiaShoot;
                        hitEffect = PrtFx.dystopiaHit;
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = PrtFx.dystopiaTrail;
                        despawnEffect = PrtFx.dystopiaHit;
                        trailSpacing = 15f;
                        damage = 1350;
                        buildingDamageMultiplier = 0.25f;
                        speed = brange;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                    }}
            );

            maxAmmo = 40;
            ammoPerShot = 5;
            rotateSpeed = 2f;
            reloadTime = 235f;
            ammoUseEffect = Fx.casing3Double;
            recoilAmount = 5f;
            restitution = 0.009f;
            cooldown = 0.009f;
            shootShake = 4f;
            shots = 1;
            size = 5;
            shootCone = 2f;
            shootSound = Sounds.railgun;
            unitSort = (u, x, y) -> -u.armor + Mathf.dst2(u.x, u.y, x, y) / 6400f;

            coolantMultiplier = 0.4f;

            health = 170 * size * size;
            coolantUsage = 1f;

            consumes.powerCond(10f, TurretBuild::isActive);
        }};

        plutoniumReactor = new ImpactReactor("plutonium-reactor") {{
            localizedName = "Plutonium Reactor";
            description = "powerful reactor which take based on nuclear decay of plutonium";
            size = 5;
            hasPower = true;
            hasLiquids = true;
            hasItems = true;
            itemCapacity = 20;
            liquidCapacity = 20;
            itemDuration = 90;
            powerProduction = 120;
            consumes.power(9f);
            consumes.liquid(Liquids.cryofluid, 0.1f);
            consumes.items(ItemStack.with(PrtItems.plutonium, 1));
            requirements(Category.power, ItemStack.with(Items.metaglass, 500, PrtItems.platinum, 300, Items.silicon, 400, Items.plastanium, 200));
        }};

        droneBase = new DroneBase("Syraphim"){{
            size = 4;
            BuildTime = 600;
            requirements(Category.turret, ItemStack.with(Items.copper, 65, Items.lead, 40, Items.titanium, 115));
            itemCapacity = 20;
            buildVisibility = BuildVisibility.sandboxOnly;
            shootEffect = PrtFx.orbitalLaserCharge;
            ammo(
                    Items.surgeAlloy, new PodStat(){{
                        damage = 1000f;
                        range = 10f;
                        effect = StatusEffects.shocked;
                        hitEffect = PrtFx.orbitalLaserChargeSurge;
                        itemCap = 10;
                        maxShots = 3;
                        speedScale = 0.5f;
                    }},
                    Items.plastanium, new PodStat(){{
                        damage = 300f;
                        range = 150f;
                        effect = StatusEffects.slow;
                        hitEffect = PrtFx.orbitalLaserChargePlast;
                        itemCap = 20;
                        maxShots = 3;
                        speedScale = 0.2f;
                    }},
                    Items.pyratite, new PodStat(){{
                        damage = 150f;
                        range = 100f;
                        effect = StatusEffects.burning;
                        hitEffect = PrtFx.orbitalLaserChargePyro;
                        itemCap = 15;
                        maxShots = 6;
                    }},
                    Items.silicon, new PodStat(){{
                        damage = 400f;
                        range = 50f;
                        effect = StatusEffects.none;
                        hitEffect = PrtFx.orbitalLaserCharge;
                        itemCap = 10;
                        maxShots = 5;
                        speedScale = 1.2f;
                    }}
            );
        }};
            droneBase = new DroneBase("Adam"){{
            size = 7;
            BuildTime = 3600;
            requirements(Category.turret, ItemStack.with(Items.copper, 1500, Items.lead, 2000, Items.metaglass, 500, PrtItems.platinum, 400, Items.silicon, 800, Items.plastanium, 400));
            itemCapacity = 1000;
            buildVisibility = BuildVisibility.sandboxOnly;
            shootEffect = PrtFx.orbitalLaserCharge;
            ammo(
                    Items.surgeAlloy, new PodStat(){{
                        damage = 3000f;
                        range = 1f;
                        effect = StatusEffects.shocked;
                        hitEffect = PrtFx.orbitalLaserChargeSurge;
                        itemCap = 250;
                        maxShots = 10;
                        speedScale = 2f;
                    }},
                    Items.plastanium, new PodStat(){{
                        damage = 400f;
                        range = 150f;
                        effect = StatusEffects.slow;
                        hitEffect = PrtFx.orbitalLaserChargePlast;
                        itemCap = 200;
                        maxShots = 50;
                        speedScale = 0.5f;
                    }},
                    Items.pyratite, new PodStat(){{
                        damage = 150f;
                        range = 100f;
                        effect = StatusEffects.burning;
                        hitEffect = PrtFx.orbitalLaserChargePyro;
                        itemCap = 400;
                        maxShots = 100;
                        speedScale = 0.8f;
                    }},
                    Items.lead, new PodStat(){{
                        damage = 500f;
                        range = 4f;
                        effect = StatusEffects.none;
                        hitEffect = PrtFx.orbitalLaserCharge;
                        itemCap = 1000;
                        maxShots = 70;
                        speedScale = 10f;
                    }}
            );
        }};
    }
}
