///ENVIRONMENT///

//FUNCTIONS//

function newFloor(name, variants, speedMultiplier){
  const floor = extendContent(Floor, name, {});
  floor.variants = variants;
  floor.speedMultiplier = speedMultiplier;
  
  
  
  return floor;
};

function newLiquidFloor(name, variants, speedMultiplier,){
  const liqFloor = extendContent(Floor, name, {});
  liqFloor.isLiquid = true;
  liqFloor.variants = variants;
  liqFloor.speedMultiplier = speedMultiplier;
  //SuperJaba2000 НАПИШЕТ!
  
  return liqFloor
};
//ATTEIBUTES and OTHER write separately!!!

