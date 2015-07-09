import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;


public class Doku {
	
	public static Vector<Integer>[] families;
	public static Vector<Integer>[] horizontals;
	public static Vector<Integer>[] verticals;
	
	public Vector[] tempF = new Vector[9];
	public Vector[] tempH = new Vector[9];
	public Vector[] tempV = new Vector[9];
	
	public static Tile[] tiles = new Tile[81];
	
	public static void main(String[] args) throws FileNotFoundException {
		Doku d = new Doku();
	}
	
	public Doku() throws FileNotFoundException{
		
		families = tempF;
		horizontals = tempH;
		verticals = tempV;
		
		for(int i=0;i<9;i++){
			families[i] = new Vector<Integer>();
			horizontals[i] = new Vector<Integer>();
			verticals[i] = new Vector<Integer>();
		}
		for(int i=0;i<81;i++){
			tiles[i] = new Tile();	
		}
		setup();
		
	}
	
	public static void setup() throws FileNotFoundException{
		FileReader fr = new FileReader("Puzzle2.txt");
		Scanner input = new Scanner(fr);
		
		System.out.println("Hello young creature, my name is Doku. ");
		System.out.println("Give me your sudoku riddle and I will solve it.");
		
		int count =0;		
		
		for(int i = 0;i<9;i++){
			String tileInput = input.nextLine();
			System.out.println("Row "+count+": "+tileInput );
			parseInput(tileInput,i);
			count++;	
		}
		
		System.out.println("Excellent. I've constructed the Sudoku playing field.");
		System.out.println("Now I will solve it.");
		
		solve();
	}

	public static boolean unsolved = true;
	
	private static void solve() {
		
		System.out.println("Generating annotations...");
		
		for(Tile t : tiles){
			t.generateAnnotations();
		}
		
		int count = 0;
		
		System.out.println("REDUCTIO AD ABSURDUM: ");
		
		while(unsolved){
			
			int completeTiles = 0;
			
			for(Tile t : tiles){
				t.reductioAdAbsurdum();
				if(t.solved){
					completeTiles++;
				}
			}
			count++;
			if(completeTiles == 81){
				unsolved = false;
				printSolvedPuzzle();
			}
			if(count == 100){
				System.out.println("Didn't solve.");
				break;
			}
			System.out.println("Complete tiles = "+completeTiles);
		}
		System.out.println("I've completed the puzzle.");
	}
	
	private static void printSolvedPuzzle() {
		for (int i = 1; i < tiles.length+1; i++) {
			
				if((i%9==0)&&(i%3==0)){
						System.out.print(tiles[i-1].value+"\n");
				}
				else if((i%3==0)){
					System.out.print(tiles[i-1].value+"\t");
				}
				
				else{
					System.out.print(tiles[i-1].value+" ");
				}
		}
	}

	public static void parseInput(String tileInput, int horizontalIndex){
		int familyIndex;
		int value;
		int tileIndex;
		
		String[] rowValues = tileInput.split(",");
		
		for(int verticalIndex=0;verticalIndex<9;verticalIndex++){
			if(Character.isDigit(rowValues[verticalIndex].charAt(0))){
				horizontals[horizontalIndex].add(Integer.parseInt(rowValues[verticalIndex]));
				verticals[verticalIndex].add(Integer.parseInt(rowValues[verticalIndex]));
				familyIndex = determineFamily(verticalIndex,horizontalIndex);
				families[familyIndex].add(Integer.parseInt(rowValues[verticalIndex]));
				value = Integer.parseInt(rowValues[verticalIndex]);
				tileIndex= determineTileIndex(horizontalIndex,verticalIndex);
				tiles[tileIndex] = new Tile(tileIndex,horizontalIndex,verticalIndex,familyIndex,value,true);
			}
			else{
				tileIndex = determineTileIndex(horizontalIndex,verticalIndex);
				familyIndex = determineFamily(verticalIndex,horizontalIndex);
				Tile t = new Tile(tileIndex, horizontalIndex,verticalIndex,familyIndex,0,false);
				tiles[tileIndex]=t;
			}
		}
	}

	private static int determineTileIndex(int horizontalIndex, int verticalIndex) {
		return ( 9 * horizontalIndex + verticalIndex);
	}

	private static int determineFamily(int vI, int hI) {
		
		if((hI == 0|| hI==1 ||hI==2)&&(vI == 0|| vI==1 ||vI==2)){return 0;}
		else if((hI== 0|| hI==1 ||hI==2)&&(vI==3|| vI==4 ||vI==5)){return 1;}
		else if((hI== 0|| hI==1 ||hI==2)&&(vI==6|| vI==7 ||vI==8)){return 2;}
		else if((hI== 3|| hI==4 ||hI==5)&&(vI==0|| vI==1 ||vI==2)){return 3;}
		else if((hI== 3|| hI==4 ||hI==5)&&(vI==3|| vI==4 ||vI==5)){return 4;}
		else if((hI== 3|| hI==4 ||hI==5)&&(vI==6|| vI==7 ||vI==8)){return 5;}
		else if((hI== 6|| hI==7 ||hI==8)&&(vI==0|| vI==1 ||vI==2)){return 6;}
		else if((hI== 6|| hI==7 ||hI==8)&&(vI==3|| vI==4 ||vI==5)){return 7;}
		else if((hI== 6|| hI==7 ||hI==8)&&(vI==6|| vI==7 ||vI==8)){return 8;}
		return -1;
	}
	
}
