package game.arenas;

import game.arenas.air.AerialArena;
import game.arenas.land.LandArena;
import game.arenas.naval.NavalArena;

public class FactoryArena {
	public Arena getArena(String arenaType) {
		Arena arena = null;
		if(arenaType.compareTo("AerialArena")==0) {
			arena =  new AerialArena();
		}
		if(arenaType.compareTo("LandArena")==0) {
			arena =  new LandArena();
		}
		if(arenaType.compareTo("NavalArena")==0) {
			arena =  new NavalArena();
		}
		System.out.println(arena);
		return arena;
	}
}
