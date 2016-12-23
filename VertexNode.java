package Maze;
/* Vertex Node Class */
/*Programmed By A-Shawni Mitchell, November 23,2016 */

public class VertexNode {
	
	int i;
	VertexNode nextVertex;
	EdgeNode firstEdge;
	int state;
	
	//Constructor 
	public VertexNode(int s) {
		// TODO Auto-generated constructor stub
		i=s;
	}

	//Getter
	public int toInt() {
		return i;
	}

}
