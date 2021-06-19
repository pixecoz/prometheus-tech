///TURRETS///

//BULLETS//

const greenLightning = extend(LightningBulletType, {});
greenLightning.damage = 36;
greenLightning.lightningLength = 41;
greenLightning.lightningLengthRand = 3;
greenLightning.lightningColor = Color.valueOf("");
greenLightning.collidesAir = false;
greenLightning.ammoMultiplier = 2;

//TURRETS//

const serpentine = extendContent(PowerTurret, "serpentine", {});
serpentine.size = 2;
serpentine.health = 450;
serpentine.Category = Category.turret;
serpentine.buildVisibility = BuildVisibility.shown;

serpentine.shootType = greenLightning;
serpentine.reloadTime = 45;
serpentine.shootCone = 32;
serpentine.rotateSpeed = 7;
serpentine.powerUse = 4.2;
serpentine.targetAir = false;
serpentine.range = 150;
serpentine.shootEffect = Fx.lightningShoot;
serpentine.heatColor = Color.red;
serpentine.recoilAmount = 1;
serpentine.hootSound = Sounds.spark;
