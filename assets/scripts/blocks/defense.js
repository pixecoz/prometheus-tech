///DEFENSE///

//WALLS//

function newRegenWall(name, health, size, item, colich, liCha, liDam, liLength, liColor, insulated, absorbLasers){
  const regWall = extendContent(Wall, name, {});
  
  regWall.buildVisibility = BuildVisibility.shown;
  regWall.category = Category.defense;
  regWall.update = true;
 
  regWall.size = size;
  regWall.health = health;
  regWall.requirements = ItemStack.with(item, colich);
  
  regWall.lightningChance = liCha;
  regWall.lightningDamage = liDam;
  regWall.lightningLength = liLength;
  regWall.lightningColor = Color.value.of(liColor);
  
  regWall.insulated = insulated;//POWER NODES
  regWall.absorbLasers = absorbLasers;//LASERS
  
    return regWall;
};

const platinum = PrtItems.platinum;
const magnetite = PrtItems.magnetite;

const platinumWall = newRegenWall("platinum-wall", 1000, 1, platinum, 6 , 0, 0, 0, null, false, false);

platinumWall.buildType = () => extendContent(Wall.WallBuild, platinumWall, {
    update(){
         if (this.damaged() && timer.get(0, 1800)) {
         heal(maxHealth / 4);
         };
    };
};
                                             
const platinumWallLarge = newRegenWall("platinum-wall-large", 4000, 2, platinum, 24, 0, 0, 0, null, false, false);

platinumWallLarge.buildType = () => extendContent(Wall.WallBuild, platinumWallLarge, {
    update(){
         if (this.damaged() && timer.get(0, 1800)) {
         heal(maxHealth / 4);
         };
    };
};


const magnetiteWall = newRegenWall("magnetite-wall", 300, 1, magnetite, 6, 0.25, 30, 6.1, "FF8D5C", true, true);
magnetiteWall.flashHit = true;
magnetiteWall.chanceDeflect = 12.3;

const magnetiteWallLarge = newRegenWall("magnetite-wall-large", 1200, 2, magnetite, 24, 0.25, 30, 6.3, "FF8D5C", true,true);
magnetiteWallLarge.flashHit = true;
magnetiteWallLarge.chance = 12.3;
