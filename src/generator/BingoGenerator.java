package generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BingoGenerator {
	
	public static List<String> pokemon = new ArrayList<>();
	public static List<String> rarePokemon = new ArrayList<>();
	public static List<String> dontGenerate = new ArrayList<>();
	public static List<String> npcs = new ArrayList<>();
	public static List<String> craftables = new ArrayList<>();
	public static List<String> nobles = new ArrayList<>();
	public static List<String> types = new ArrayList<>();
	public static List<String> diggables = new ArrayList<>();
	public static Random rand = new Random();

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("src/Pokemon list");
		Scanner scanner = new Scanner(file);
		while(scanner.hasNext()) {
			pokemon.add(scanner.nextLine());
		}
		file = new File("src/Craftables");
		scanner = new Scanner(file);
		while(scanner.hasNext()) {
			craftables.add(scanner.nextLine());
		}
		file = new File("src/Don'tGenerate");
		scanner = new Scanner(file);
		while(scanner.hasNext()) {
			dontGenerate.add(scanner.nextLine());
		}
		file = new File("src/RareList");
		scanner = new Scanner(file);
		while(scanner.hasNext()) {
			rarePokemon.add(scanner.nextLine());
		}
		file = new File("src/NPCs");
		scanner = new Scanner(file);
		while(scanner.hasNext()) {
			npcs.add(scanner.nextLine());
		}
		file = new File("src/Types");
		scanner = new Scanner(file);
		while(scanner.hasNext()) {
			types.add(scanner.nextLine());
		}
		file = new File("src/Nobles");
		scanner = new Scanner(file);
		while(scanner.hasNext()) {
			nobles.add(scanner.nextLine());
		}
		file = new File("src/Diggables");
		scanner = new Scanner(file);
		while(scanner.hasNext()) {
			diggables.add(scanner.nextLine());
		}
		
		List<String> tasks = new ArrayList<String>();
		tasks.add(generateRarePokemon());
		tasks.add(generateSpecificPokemon());
		tasks.add(generateType());
		tasks.add(generateAlphaPokemon());
		tasks.add(generateCraftable());
		tasks.add(generateNoble());
		tasks.add(generateRefights());
		tasks.add(generateDiggable());
		tasks.add(generateWord());
		
		while(tasks.size()<50) {
			int i = rand.nextInt(100);
			if(i<30) {
				generateTask("specific", tasks);
			}else if(i<40) {
				generateTask("alpha", tasks);
			}else if(i<50) {
				generateTask("rare", tasks);
			}else if(i<65) {
				generateTask("type", tasks);
			}else if(i<80) {
				generateTask("craft", tasks);
			}else if(i<85) {
				generateTask("noble", tasks);
			}else if(i<90) {
				generateTask("refight", tasks);
			}else {
				generateTask("dig", tasks);
			}
		}
		
		generateJson(tasks);
	}
	
	public static void generateJson(List<String> tasks) {
		System.out.print("[");
		for(int i = 0; i<tasks.size()-1;i++) {
			System.out.println("{\"name\": \""+tasks.get(i)+"\"},");
		}
		System.out.println("{\"name\": \""+tasks.get(tasks.size()-1)+"\"} ]");
	}
	
	public static void generateTask(String typeOfTask, List<String> tasks) {
		String toAdd = null;
		while(toAdd == null || tasks.contains(toAdd)) {
			if(typeOfTask.equals("rare")) {
				toAdd = generateRarePokemon();
			}else if(typeOfTask.equals("specific")) {
				toAdd = generateSpecificPokemon();
			}else if(typeOfTask.equals("alpha")) {
				toAdd = generateAlphaPokemon();
			}else if(typeOfTask.equals("craft")) {
				toAdd = generateCraftable();
			}else if(typeOfTask.equals("noble")) {
				if(nobles.isEmpty()) return;
				toAdd = generateNoble();
			}else if(typeOfTask.equals("refight")) {
				if(npcs.isEmpty()) return;
				toAdd = generateRefights();
			}else if(typeOfTask.equals("type")) {
				toAdd = generateType();
			}else if(typeOfTask.equals("dig")) {
				if(diggables.isEmpty()) return;
				toAdd = generateDiggable();
			}
		}

		tasks.add(toAdd);
	}
	
	public static String generateRarePokemon() {
		return "Catch a "+rarePokemon.get(rand.nextInt(rarePokemon.size()));
	}
	
	public static String generateSpecificPokemon() {
		String poke = pokemon.get(rand.nextInt(pokemon.size()));
		while(dontGenerate.contains(poke)||rarePokemon.contains(poke)) {
			poke = pokemon.get(rand.nextInt(pokemon.size()));
		}
		return "Catch/Evolve "+generateNumber()+" "+poke;
	}
	
	public static String generateType() {
		return "Catch " + generateLargeNumber()+" "+types.get(rand.nextInt(types.size())) + " type pokemon";
	}
	
	public static String generateAlphaPokemon() {
		String poke = pokemon.get(rand.nextInt(pokemon.size()));
		while(dontGenerate.contains(poke)||rarePokemon.contains(poke)) {
			poke = pokemon.get(rand.nextInt(pokemon.size()));
		}
		return "Catch/Evolve an Alpha "+poke;
	}
	
	public static String generateNumber() {
		if(rand.nextBoolean()) {
			return generateSmallNumber();
		}
		if(rand.nextBoolean()) {
			return generateMediumNumber();
		}
		return generateLargeNumber();
	}
	
	public static String generateSmallNumber() {
		return (rand.nextInt(4)+1)+"";
	}
	
	public static String generateMediumNumber() {
		return (rand.nextInt(10)+5)+"";
	}
	
	public static String generateLargeNumber() {
		return (rand.nextInt(20)+10)+"";
	}
	
	public static String generateNoble() {
		return "Beat Noble "+nobles.remove(rand.nextInt(nobles.size()));
	}
	
	public static String generateCraftable() {
		String item = craftables.get(rand.nextInt(craftables.size()));
		if(item.equals("Star Piece")){
			return "Craft 1 "+item;
		}
		return "Craft "+generateNumber()+" "+item;
	}
	
	public static String generateWord() {
		return "Spell a "+(rand.nextInt(3)+4)+" letter word";
	}
	
	public static String generateRefights() {
		return "Beat " + npcs.remove(rand.nextInt(npcs.size()));
	}
	
	public static String generateDiggable() {
		return "Dig up "+diggables.remove(rand.nextInt(diggables.size()));
	}

}
/**
 * Catch {rare pokemon}
Catch {number} {type} type pokemon
Find {rare item from time distortion}
Catch alpha {non-rare pokemon}
Find a pokemon with {move} 
Evolve {number} pokemon
Dig{rare item up}
Craft {number} {item}
Catch a shiny
Stun {pokemon or number of poekemon}
spell {word}
fight {character}
Refight Noble
 */