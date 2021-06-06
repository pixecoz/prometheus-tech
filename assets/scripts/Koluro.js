const Koluro = new Planet("Koluro", Planets.sun, 3, 1.6);

Koluro.hasAtmosphere = false;
Koluro.meshLoader = () => new SunMesh( Koluro, 4, 5, 0.3, 1.7, 1.2, 1, 1.5, 
                                      Color.valueOf("e693ea"), 
                                      Color.valueOf("fbbcff"), 
                                      Color.valueOf("e4aded"), 
                                      Color.valueOf("f9e8f9"),
                                      Color.valueOf("cf9cd3"),
                                      Color.valueOf("cd92ce"));
Koluro.orbitRadius = 27.3;
Koluro.accessible = false;
Koluro.bloom = true;
