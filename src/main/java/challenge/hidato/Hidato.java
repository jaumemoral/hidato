package challenge.hidato;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hidato {

	Cell cells[][];
	List<Cell> index=new ArrayList<Cell>();
	int height;
	int width;

	int[][] directions={{0,1},{0,-1},{1,1},{1,0},{1,-1},{-1,1},{-1,0},{-1,1}};

	public Hidato(String[] cells) {
		this.height=cells.length;
		this.width=cells[0].split(" ").length;
		this.cells=new Cell[height][];
		for (int i=0;i<height;i++) {
			String[] row=cells[i].split(" ");
			this.cells[i]=new Cell[width];
			for (int j=0;j<width;j++) {
				Cell cell=new Cell(i,j,row[j]);
				this.cells[i][j]=cell;
				index.add(cell);
			}
		}
	}

	public Cell findLowerNumber() {
		Cell min=null;
		for (Cell cell:index) {
			if (min==null || cell.menorQue(min)) min=cell;
		}
		return min;
	}

	public void solve() {
		markPossibles();
		while (fixPossibles()>1);
		//markInitialPossibles();
		//Cell cell=findLowerNumber();
		//int i=cell.getNumber();
		//putNumber(cell,i+1);
	}

	public void markInitialPossibles() {
		int n=getMaxNumber();
		for (Cell cell: index) {
			for (int i=1;i<=n;i++) if (cell.isEmpty()) cell.addAsPossible(i);
		}
	}

	public void markPossibles() {
		for (Cell cell: index) {
			if (cell.isNumber()) {
				for (Cell adjacent:getAdjacents(cell)) {
					adjacent.addAsPossible(cell.getNumber()+1);
					adjacent.addAsPossible(cell.getNumber()-1);
				}
			}
		}
	}

	public int fixPossibles() {
		System.out.println("---");
		System.out.println(toString());
		System.out.println("---");
		int fixed=0;
		for (int i=1;i<getMaxNumber();i++) {
			int possibleCellsForNumber=0;
			Cell lastCell=null;
			for (Cell cell: index) {
				if (cell.isSure(i)) {
					System.out.println("Cell"+cell+" contains "+i);
					possibleCellsForNumber++;
					lastCell=cell;
				}
			}
			if (possibleCellsForNumber==1) {
				fixed++;
				System.out.println(cells);
				lastCell.setNumber(i);
				removePossibles(i);
				markPossibles();
			}
		}
		return fixed;
	}

	public void removePossibles (int value) {
		for (Cell cell: index) cell.removeAsPossible(value);
	}


	public int getMaxNumber() {
		int n=0;
		for (Cell cell: index) if (cell.isValid()) n++;
		return n;
	}



	boolean putNumber(Cell cell,int n) {
		int x=cell.i;
		int y=cell.j;
		for (int[] d:directions) {
			cell=getCell(x+d[0],y+d[1]);
			if (cell.isEmpty()) {
				cell.setNumber(n);
				System.out.println(toString());
				putNumber(cell,n+1);
				return true;
			}
		}
		return false;
	}

	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				sb.append(cells[i][j].value);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public List<Cell> getAdjacents(Cell cell) {
		List<Cell> adjacents=new ArrayList<Cell>();
		for (int[] d:directions) {
			Cell adjacent=getCell(cell.i+d[0],cell.j+d[1]);
			if (adjacent!=null && adjacent.isEmpty()) adjacents.add(adjacent);
		}
		return adjacents;
	}

	public Cell getCell(int i, int j) {
		Cell cell=new Cell(i,j,"x");
		if (i<0 || i>=height) return cell;
		if (j<0 || j>=width) return cell;
		return cells[i][j];
	}

}

class Cell {
	int i,j;
	String value;
	Map<Integer,Integer> possibles=new HashMap<Integer,Integer>();

	Cell (int i, int j, String value) {
		this.i=i;
		this.j=j;
		this.value=value;
	}

	boolean menorQue(Cell other) {
		if (isEmpty()) return false;
		if ("x".equals(value)) return false;
		return value.compareTo(other.value)>0;
	}

	int getNumber() {
		return Integer.parseInt(value);
	}

	void setNumber(int n) {
		value=""+n;
	}

	boolean isEmpty() {
		return ".".equals(value);
	}

	boolean isValid() {
		return !"x".equals(value);
	}

	boolean isNumber() {
		return isValid() && !isEmpty();
	}

	void addAsPossible(int value) {
		possibles.putIfAbsent(value, 0);
		possibles.put(value,possibles.get(value)+1);
	}

	void removeAsPossible(int value) {
		possibles.remove(value);
	}

	boolean isSure(int value) {
		try {
			return possibles.get(value)==2;
		} catch (Exception e) {
			return false;
		}
	}


	public String toString() {
		return i+","+j;
	}

}
