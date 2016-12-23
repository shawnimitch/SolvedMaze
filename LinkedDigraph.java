package Maze;
/* Adjacency Class */
/*Programmed By A-Shawni Mitchell, November 23,2016 */

import java.util.LinkedList;
import java.util.Queue;

public class LinkedDigraph {
	
	VertexNode start;
	int n;
	int e;
	
	
	public int vertices() {
		return n;
	}
	
	public int edges() {
		return e;
	}
	
	/*Insert Vertex */
	public void insertVertex(int s) {
		
		VertexNode temp = new VertexNode(s); //Initialize vertex Node
		if(start==null)
			start=temp;
		else {
			VertexNode p = start;
			while(p.nextVertex!=null) {
				
				if(p.i==s) { //Vertex is present
					System.out.println("Vertex already present.");
					return;
				}
				p=p.nextVertex;
			}
			if(p.i==s) { //Vertex is present
				System.out.println("Vertex already present.");
				return;
			}
			p.nextVertex=temp;
		}
		n++;
	}
	
	/* Find Vertex Method */
	private VertexNode findVertex(int s) {
		
		VertexNode p=start;
		while(p!=null) {
			if(p.i==s)
				return p;
			p=p.nextVertex;
		}
		return null;
	}
	
	/* Insert Edge Method */
	public void insertEdge(int s1, int s2) {
		
		if(s1==s2) {
			System.out.println("Invalid Edge: start and end vertices are the same.");
			return;
		}
		
		VertexNode u = findVertex(s1); //source vertex
		VertexNode v = findVertex(s2); //destination vertex
		
		if(u==null || v==null) { //a vertex is present
			System.out.println("A vertex is not present.");
			return;
		}
		
		EdgeNode temp = new EdgeNode(v);
		
		if(u.firstEdge==null) {
			u.firstEdge=temp;
			e++;
		} else {//insert after first vertex
			EdgeNode p=u.firstEdge;
			while(p.nextEdge!=null) {
				p=p.nextEdge;
			}
			if(p.endVertex.i==s2) {//Edge present
				System.out.println("Edge present.");
				return;
			}
			p.nextEdge=temp;
			e++;
		}
	}
	
	public void display() {
		
		EdgeNode q;
		for(VertexNode p=start;p!=null;p=p.nextVertex) {
			System.out.print(p.i + "->"); //Displays source vertex
			for(q=p.firstEdge;q!=null;q=q.nextEdge)
				System.out.print(" " + q.endVertex.i); //Displays connected vertex 
			System.out.println();
		}
	}
	
	/*Breadth-First Search Method */
	public void bfs(int v) {
		
		int[] path = new int[n];
		boolean[] visited = new boolean[n];
		
		initializeV(visited);
		initialize(path);
		
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(v);
		//vertexList[v].state = WAITING;
		
		while(!q.isEmpty()) {
			
			v=q.remove();
			System.out.print(path[v] + " ");
			visited[v]=true;
			
			for(int i=0; i<n;i++) {
				if(isAdjacent(v,i) && !visited[i]){
					q.add(i);
					visited[i]=true;
				}
			}
		}
		System.out.println();	
	}
	
	/*Is Adjacent Method*/
	public boolean isAdjacent(int s1, int s2) {
		
		if(s1==s2) 
			return false;
		
		VertexNode u = findVertex(s1);
		
		if(u.firstEdge==null)
			return false;
		else {
			EdgeNode p = u.firstEdge;
			while(p!=null) {
				if(p.endVertex.i==s2)
					return true;
				p=p.nextEdge;
			}
		}
		return false;
	}
	
	/*Depth-First Search Method */
	public void dfs() {
		
		boolean[] visited = new boolean[n];
	
		initializeV(visited);
		
		dfs(0,visited);
	}
	
	private void dfs(int v , boolean[] visited) {
		
		System.out.print(v + " ");
		visited[v] = true;
		
		for(int i=0;i<n;i++) {
			if(isAdjacent(v,i) && !visited[i])
				dfs(i,visited);
		}
		visited[v] = true;
	}
	
	//Initialize Methods
	public void initialize(int[] S) {
		   
	   for(int i =0;i<S.length;i++)
		   S[i] = i;
	 }
	   
	public void initializeV(boolean[] v) {
		for(int i =0;i<v.length;i++)
		   v[i]=false;
   }
	
	

}
