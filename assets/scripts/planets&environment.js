///ENVIRONMENT///

//FUNCTIONS//

function newFloor(name, variants, speedMultiplier, emitLight, lightRadius){
  const floor = extendContent(Floor, name, {});
  floor.variants = variants;
  floor.speedMultiplier = speedMultiplier;
  floor.emitLight = emitLight;
  floor.lightRadius = lightRadius;
  
  return floor;
};

function newLiquidFloor(name, variants, speedMultiplier){
  const liqFloor = extendContent(Floor, name, {});
  liqFloor.isLiquid = true;
  liqFloor.variants = variants;
  liqFloor.speedMultiplier = speedMultiplier;
  liqFloor.cacheLayer = CacheLayer.water;
  liqFloor.albedo = 0.5;
  
  return liqFloor;
};

function newBlock(name, variants, solid){
  const StaticBlock = extendContent(StaticWall, name, {});
  StaticBlock.variants = variants;
  //Solid is bad!
  
  return StaticBlock;
};

function newBoulder(name, variants){
  const boulder = extendContent(StaticTree, name, {});//Why not work type "Prop"???
  boulder.variants = variants;
  
  return boulder;
};

function newPine(name, variants){
  const pine = extendContent(StaticTree, name, {});
  pine.variants = variants;
  
  return pine;
};

//ATTRIBUTES and OTHER write separately!!!

///CONTENT///

//WHITE STONE//

const whiteStoneWall = newBlock("white-stone-block", 2, true);
const whiteStoneFloor = newFloor("white-stone-floor", 3, 1.0, false, 0.0);

const whiteStoneBoulder = newBoulder("white-stone-boulder", 1);

//GRANITE//

const graniteWall = newBlock("granite-block", 2, true);
const graniteFloor = newFloor("granite-floor", 3, 1.0, false, 0.0);

const graniteBoulder = newBoulder("granite-boulder", 1);

//ANDESITE//

const andesiteWall = newBlock("andesite-wall", 2, true);
const andesiteFloor = newFloor("andesite floor", 3, 1.0, false, 0.0);

const andesiteBoulder = newBoulder("andesite-boulder", 1);

//PURIFIED BLOCKS//

const purifiedShrubs = newBlock("purified-shrubs", 2, true);
const purifiedGrass = newFloor("purified-grass-floor", 3, 1.0, false, 0.0);

const purifiedPine = newPine("purified-pine", 0);

//LIQUIDS//

const purelyWater = newLiquidFloor("purely-water-floor", 0, 0.2);
purelyWater.liquidDrop = Liquids.water;
purelyWater.status = StatusEffects.wet;
purelyWater.statusDuration = 90;

const purelySandWater = newLiquidFloor("purely-sand-water-floor", 0, 0.2);
purelySandWater.liquidDrop = Liquids.water;
purelySandWater.status = StatusEffects.wet;
purelySandWater.statusDuration = 90;

///PLANETS///

//STAR "KOLURO"//

const koluro = new JavaAdapter(Planet, {}, "Koluro", Planets.sun, 3, 1.6);
koluro.meshLoader = () => new SunMesh( 
    koluro, 4, 5, 0.3, 1.7, 1.2, 1, 1.1, 
    Color.valueOf("e693ea"), 
    Color.valueOf("fbbcff"), 
    Color.valueOf("e4aded"), 
    Color.valueOf("f9e8f9"),
    Color.valueOf("cf9cd3"),
    Color.valueOf("cd92ce")
);
koluro.orbitRadius = 27.3;
koluro.accessible = false;
koluro.bloom = true;

//PLANET "Carputo"//

const simplex = new Packages.arc.util.noise.Simplex();
const rid = new Packages.arc.util.noise.RidgedPerlin(1, 2);

const CarputoGenerator = extend(PlanetGenerator, {
    getColor(position){
        var block = this.getBlock(position);

        Tmp.c1.set(block.mapColor).a = 1 - block.albedo;
        return Tmp.c1;
    },
    
    getBlock(pos){
	    var height = this.rawHeight(pos);
	
	    Tmp.v31.set(pos);
	    pos = Tmp.v33.set(pos).scl(CarputoGenerator.scl);
	    var rad = CarputoGenerator.scl;
	    var temp = Mathf.clamp(Math.abs(pos.y * 2) / rad);
	    var tnoise = simplex.octaveNoise3D(7, 0.56, 1 / 3, pos.x, pos.y + 999, pos.z);
	    temp = Mathf.lerp(temp, tnoise, 0.5);
	    height *= 1.2;
	    height = Mathf.clamp(height);
	
	    var tar = simplex.octaveNoise3D(4, 0.55, 0.5, pos.x, pos.y + 999, pos.z) * 0.3 + Tmp.v31.dst(0, 0, 1) * 0.2;
	    var res = CarputoGenerator.arr[
	       Mathf.clamp(Mathf.floor(temp * CarputoGenerator.arr.length), 0, CarputoGenerator.arr[0].length - 1)][ Mathf.clamp(Mathf.floor(height * CarputoGenerator.arr[0].length), 0, CarputoGenerator.arr[0].length - 1)
	    ];
	
	    if (tar > 0.5){
	        return CarputoGenerator.tars.get(res, res);
	    } else {
	        return res;
	    };
    },
    
    rawHeight(pos){
		var pos = Tmp.v33.set(pos);
		pos.scl(CarputoGenerator.scl);
		
		return (Mathf.pow(simplex.octaveNoise3D(7, 0.5, 1 / 3, pos.x, pos.y, pos.z), 2.3) + CarputoGenerator.waterOffset) / (1 + CarputoGenerator.waterOffset);  
    },
    
    getHeight(position){
        var height = this.rawHeight(position);
        return Math.max(height, CarputoGenerator.water);
    },
    
    genTile(position, tile){
        tile.floor = this.getBlock(position);
        tile.block = tile.floor.asFloor().wall;

        if(rid.getValue(position.x, position.y, position.z, 22) > 0.32){
            tile.block = Blocks.air;
        }
    },
	
    generate(){
	Schematics.placeLaunchLoadout(spawn.x, spawn.y);
	///loading...
    }
});

CarputoGenerator.arr = [
    [purelyWater, Blocks.ice, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, purelyWater, whiteStoneFloor, Blocks.snow],
    [whiteStoneFloor, purelyWater, purelyWater, Blocks.snow, Blocks.ice, purelyWater, Blocks.stone, Blocks.snow, Blocks.snow, purelyWater, whiteStoneFloor, whiteStoneFloor, Blocks.stone],
    [Blocks.snow, whiteStoneFloor, Blocks.snow, purelyWater, whiteStoneFloor, Blocks.snow, whiteStoneFloor, whiteStoneFloor, whiteStoneFloor, purelyWater, purelyWater, Blocks.ice, whiteStoneFloor],
    [purelyWater, purelyWater, Blocks.snow, purelyWater, whiteStoneFloor, Blocks.ice, Blocks.snow, whiteStoneFloor, whiteStoneFloor, whiteStoneFloor, purelyWater, purelyWater, Blocks.stone],  
    [Blocks.ice, purelyWater, purelyWater, whiteStoneFloor, Blocks.snow, whiteStoneFloor, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow],  
    [Blocks.snow, purelyWater, purelyWater, purelyWater, Blocks.snow, Blocks.snow, purelyWater, Blocks.snow, whiteStoneFloor, Blocks.snow, whiteStoneFloor, purelyWater, Blocks.snow],  
    [purelyWater, purelyWater, Blocks.snow, Blocks.snow, whiteStoneFloor, whiteStoneFloor, Blocks.snow, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.snow, Blocks.ice, Blocks.ice],  
    [purelyWater, Blocks.snow, Blocks.snow, Blocks.snow, purelyWater, whiteStoneFloor, Blocks.snow, whiteStoneFloor, Blocks.stone, whiteStoneFloor, whiteStoneFloor, Blocks.iceSnow, Blocks.snow],  
    [purelyWater, purelyWater, Blocks.snow, purelyWater, purelyWater, purelyWater, purelyWater, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, whiteStoneFloor, whiteStoneFloor],
    [whiteStoneFloor, Blocks.ice, Blocks.snow, whiteStoneFloor, purelyWater, whiteStoneFloor, purelyWater, purelyWater, Blocks.snow, Blocks.snow, whiteStoneFloor, Blocks.snow, Blocks.ice], 
    [purelyWater, purelyWater, whiteStoneFloor, Blocks.stone, purelyWater, Blocks.ice, purelyWater, purelyWater, Blocks.ice, whiteStoneFloor, Blocks.stone, Blocks.stone, whiteStoneFloor], 
    [purelyWater, purelyWater, Blocks.stone, Blocks.ice, Blocks.ice, Blocks.stone, whiteStoneFloor, purelyWater, whiteStoneFloor, purelyWater, whiteStoneFloor, Blocks.ice, Blocks.snow],
    [Blocks.ice, whiteStoneFloor, Blocks.snow, whiteStoneFloor, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, whiteStoneFloor, whiteStoneFloor, Blocks.snow, Blocks.ice, Blocks.ice]
];

CarputoGenerator.scl = 6.281931105;
CarputoGenerator.waterOffset = 0.02;
CarputoGenerator.basegen = new BaseGenerator();
CarputoGenerator.water = 0.07;//normal is 0.06

CarputoGenerator.dec = new ObjectMap().of(
    Blocks.water, Blocks.ice,
    Blocks.dacite, Blocks.stone,
    Blocks.water, Blocks.ice,
    Blocks.water, Blocks.snow
);

CarputoGenerator.tars = new ObjectMap().of(
    Blocks.water, Blocks.snow,
    Blocks.dacite, Blocks.ice
);

const CarputoPlanet = new JavaAdapter(Planet, {}, "Carputo", koluro, 3, 1.0);

CarputoPlanet.generator = CarputoGenerator;
CarputoPlanet.startSector = 47;

CarputoPlanet.hasAtmosphere = true;
CarputoPlanet.atmosphereRadIn = 0.019;
CarputoPlanet.atmosphereRadOut = 0.29;
CarputoPlanet.atmosphereColor = Color.valueOf("7B5959FF");
	
CarputoPlanet.meshLoader = prov(() => new HexMesh(CarputoPlanet, 6));
	
CarputoPlanet.orbitRadius = 5.8;
CarputoPlanet.rotateTime = 10800;
CarputoPlanet.orbitTime = Mathf.pow((1.0 + 10.0 + 0.66), 1.5) * 90;

CarputoPlanet.accessible = true;//In tech tree normal is false			

//SECTORS//

const testSector = new SectorPreset("test-sector", CarputoPlanet, 78);
testSector.alwaysUnlocked = true;
testSector.difficulty = 6;
testSector.captureWave = 20;

