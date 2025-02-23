///DEFENSE///

importPackage(Packages.prometheus.content.PrtItems);

//WALLS//

function newRegenWall(name, health, size, liCha, liDam, liLength, liColor, insulated, absorbLasers){
  const regWall = extendContent(Wall, name, {});
  
  regWall.buildVisibility = BuildVisibility.shown;
  regWall.category = Category.defense;
  regWall.update = true;
 
  regWall.size = size;
  regWall.health = health;
  //regWall.requirements = ItemStack.with(item, colich);
  
  regWall.lightningChance = liCha;
  regWall.lightningDamage = liDam;
  regWall.lightningLength = liLength;
  regWall.lightningColor = Color.valueOf(liColor);
  
  regWall.insulated = insulated;//POWER NODES
  regWall.absorbLasers = absorbLasers;//LASERS
  
    return regWall;
};

const platinumWall = newRegenWall("platinum-wall", 1000, 1, 0, 0, 0, "#000000", false, false);

platinumWall.buildType = () => extendContent(Wall.WallBuild, platinumWall, {
    update(){
         if (this.damaged() && timer.get(0, 1800)) {
         heal(maxHealth / 4);
        }
    }
});
                                             
const platinumWallLarge = newRegenWall("platinum-wall-large", 4000, 2, 0, 0, 0, "#000000", false, false);

platinumWallLarge.buildType = () => extendContent(Wall.WallBuild, platinumWallLarge, {
    update(){
         if (this.damaged() && timer.get(0, 1800)) {
         heal(maxHealth / 4);
         }
    }
});


const magnetiteWall = newRegenWall("magnetite-wall", 300, 1, 0.25, 30, 6.1, "#FF8D5C", true, true);
magnetiteWall.flashHit = true;
magnetiteWall.chanceDeflect = 12.3;

const magnetiteWallLarge = newRegenWall("magnetite-wall-large", 1200, 2, 0.25, 30, 6.3, "#FF8D5C", true,true);
magnetiteWallLarge.flashHit = true;
magnetiteWallLarge.chance = 12.3;

//MOD ITEMS

Events.on(ClientLoadEvent, cons(e=>{
    platinumWall.requirements = ItemStack.with(Vars.content.getByName(ContentType.item,"prometheus-platinum"), 6);
    platinumWallLarge.requirements = ItemStack.with(Vars.content.getByName(ContentType.item,"prometheus-platinum"), 24);

    magnetiteWall.requirements = ItemStack.with(Vars.content.getByName(ContentType.item,"prometheus-magnetite"), 6);
    magnetiteWallLarge.requirements = ItemStack.with(Vars.content.getByName(ContentType.item,"prometheus-magnetite"), 24);
}));
