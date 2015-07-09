import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Vector;


public class Tile {
	
	public int tileIndex;
	public int horizontalIndex;
	public int verticalIndex;
	public int familyIndex;
	public int value = -1;
	public boolean solved = false;
	public Vector<Integer> annotations = new Vector<Integer>();
	Vector<Integer> familyAnnotations;
	Vector<Integer> horizontalAnnotations;
	Vector<Integer> verticalAnnotations;
	
	public Tile() {}

	public Tile(int tileIndex, int h, int v, int f, int value, boolean s) {
		this.tileIndex = tileIndex;
		this.horizontalIndex = h;
		this.verticalIndex = v;
		this.familyIndex = f;
		this.value = value;
		this.solved = s;
	}

	public void printData(){
		System.out.println("[TILE: "+tileIndex+"]");
		System.out.println("[Solved: "+solved+"]");
		System.out.println("[Family: "+Doku.families[familyIndex].toString()+"]");
		System.out.println("[Horizontal:"+Doku.horizontals[horizontalIndex].toString()+"]");
		System.out.println("[Vertical: "+Doku.verticals[verticalIndex].toString()+"]");
		System.out.println("");
		
	}
	
	public void generateAnnotations() {
		if(!solved){
			familyAnnotations = invert(Doku.families[familyIndex]);
			horizontalAnnotations = invert(Doku.horizontals[horizontalIndex]);
			verticalAnnotations = invert(Doku.verticals[verticalIndex]);
			combine(familyAnnotations, horizontalAnnotations,verticalAnnotations);
		}	
	}
	
	public Vector<Integer> invert(Vector<Integer> v){
		
		Vector<Integer> invert = new Vector<Integer>();	
		
		for(int i =1;i<10;i++){invert.add(i);}

		for(int i=10;i>0;i--){
			if(v.contains(i-1)){
				invert.remove(((Integer) (i-1)));
			}
		}
		return invert;
	}
	
	public void combine(Vector<Integer> v1, Vector<Integer> v2, Vector<Integer> v3){
		for(int i: v1){ annotations.add(i); }
		for(int i: v2){ annotations.add(i); }
		for(int i: v3){ annotations.add(i); }
		annotations = new Vector<Integer>(new LinkedHashSet<Integer>(annotations));
		Collections.sort(annotations);
	}

	public void reductioAdAbsurdum() {
		
		Collections.sort(Doku.families[familyIndex]);
		Collections.sort(Doku.horizontals[horizontalIndex]);
		Collections.sort(Doku.verticals[verticalIndex]);
		
		for(int i = Doku.families[familyIndex].size();i>0;i--){
			if(annotations.contains(Doku.families[familyIndex].get(i-1))){
				annotations.remove(Doku.families[familyIndex].get(i-1));
			}	
		}
		for(int i = Doku.horizontals[horizontalIndex].size();i>0;i--){
			if(annotations.contains(Doku.horizontals[horizontalIndex].get(i-1))){
				annotations.remove(Doku.horizontals[horizontalIndex].get(i-1));
			}	
		}
		for(int i = Doku.verticals[verticalIndex].size();i>0;i--){
			if(annotations.contains(Doku.verticals[verticalIndex].get(i-1))){
				annotations.remove(Doku.verticals[verticalIndex].get(i-1));
			}	
		}
		
		if(annotations.size()==1){
			Doku.families[familyIndex].add(annotations.get(0));
			Doku.horizontals[horizontalIndex].add(annotations.get(0));
			Doku.verticals[verticalIndex].add(annotations.get(0));
			value = annotations.get(0);
			System.out.println("SOLVED! TILE "+ tileIndex);
			solved = true;
		}
	}
	
	
	
	
}
