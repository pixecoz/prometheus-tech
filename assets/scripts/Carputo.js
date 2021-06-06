const koluroFile = require("Koluro");
const koluro = koluroFile.Koluro;

const modBlocks = require("blocks/environment");

const simplex = new Packages.arc.util.noise.Simplex();
const rid = new Packages.arc.util.noise.RidgedPerlin(1, 2);

const CarputoGenerator = extend(PlanetGenerator, {
    getColor(position){
        var block = this.getBlock(position);

        Tmp.c1.set(block.mapColor).a = 1 - block.albedo;
        return Tmp.c1
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
    }
});

CarputoGenerator.arr = [
    [modBlocks.purelyWater, Blocks.ice, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, modBlocks.purelyWater, modBlocks.whiteStoneFloor, Blocks.snow],
    [modBlocks.whiteStoneFloor, modBlocks.purelyWater, modBlocks.purelyWater, Blocks.snow, Blocks.ice, modBlocks.purelyWater, Blocks.stone, Blocks.snow, Blocks.snow, modBlocks.purelyWater, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor, Blocks.stone],
    [Blocks.snow, modBlocks.whiteStoneFloor, Blocks.snow, modBlocks.purelyWater, modBlocks.whiteStoneFloor, Blocks.snow, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor, modBlocks.purelyWater, modBlocks.purelyWater, Blocks.ice, modBlocks.whiteStoneFloor],
    [modBlocks.purelyWater, modBlocks.purelyWater, Blocks.snow, modBlocks.purelyWater, modBlocks.whiteStoneFloor, Blocks.ice, Blocks.snow, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor, modBlocks.purelyWater, modBlocks.purelyWater, Blocks.stone],  
    [Blocks.ice, modBlocks.purelyWater, modBlocks.purelyWater, modBlocks.whiteStoneFloor, Blocks.snow, modBlocks.whiteStoneFloor, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow],  
    [Blocks.snow, modBlocks.purelyWater, modBlocks.purelyWater, modBlocks.purelyWater, Blocks.snow, Blocks.snow, modBlocks.purelyWater, Blocks.snow, modBlocks.whiteStoneFloor, Blocks.snow, modBlocks.whiteStoneFloor, modBlocks.purelyWater, Blocks.snow],  
    [modBlocks.purelyWater, modBlocks.purelyWater, Blocks.snow, Blocks.snow, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor, Blocks.snow, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.snow, Blocks.ice, Blocks.ice],  
    [modBlocks.purelyWater, Blocks.snow, Blocks.snow, Blocks.snow, modBlocks.purelyWater, modBlocks.whiteStoneFloor, Blocks.snow, modBlocks.whiteStoneFloor, Blocks.stone, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor, Blocks.iceSnow, Blocks.snow],  
    [modBlocks.purelyWater, modBlocks.purelyWater, Blocks.snow, modBlocks.purelyWater, modBlocks.purelyWater, modBlocks.purelyWater, modBlocks.purelyWater, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor],
    [modBlocks.whiteStoneFloor, Blocks.ice, Blocks.snow, modBlocks.whiteStoneFloor, modBlocks.purelyWater, modBlocks.whiteStoneFloor, modBlocks.purelyWater, modBlocks.purelyWater, Blocks.snow, Blocks.snow, modBlocks.whiteStoneFloor, Blocks.snow, Blocks.ice], 
    [modBlocks.purelyWater, modBlocks.purelyWater, modBlocks.whiteStoneFloor, Blocks.stone, modBlocks.purelyWater, Blocks.ice, modBlocks.purelyWater, modBlocks.purelyWater, Blocks.ice, modBlocks.whiteStoneFloor, Blocks.stone, Blocks.stone, modBlocks.whiteStoneFloor], 
    [modBlocks.purelyWater, modBlocks.purelyWater, Blocks.stone, Blocks.ice, Blocks.ice, Blocks.stone, modBlocks.whiteStoneFloor, modBlocks.purelyWater, modBlocks.whiteStoneFloor, modBlocks.purelyWater, modBlocks.whiteStoneFloor, Blocks.ice, Blocks.snow],
    [Blocks.ice, modBlocks.whiteStoneFloor, Blocks.snow, modBlocks.whiteStoneFloor, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, modBlocks.whiteStoneFloor, modBlocks.whiteStoneFloor, Blocks.snow, Blocks.ice, Blocks.ice]
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
