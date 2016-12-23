package Maze;
/* Maze Class */
/*Programmed By A-Shawni Mitchell, November 23,2016 */
import java.util.*;

public class Maze {
    private int N;                  // dimension of maze
    public boolean[][] v_wall;     
    public boolean[][] h_wall;
    private LinkedDigraph g;
    private static int[] S;
    private static Random rand;
	private static Scanner in;
  
	//Constructor
    public Maze(int N) {
        this.N = N;
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        init();
        buildVertex();
    }

    //init Method
    private void init() {
    // initialze all walls as present
    // Vertical wall v_wall[x][y] separates cells (x + N*y) and (x + N*y + 1)
    // Horizontal wall h_wall[x][y] separates cells (x + N*y) and (x + N*(y + 1))

        h_wall = new boolean[N][N-1];        
        v_wall = new boolean[N-1][N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N-1; j++)
                v_wall[j][i] = h_wall[i][j] = true;
    }
 
    //Remove Wall Method
    public void remove_wall(int x, int y, char d){
      if ((d=='N') && (y<N-1))
 	      h_wall[x][y] = false;
      
	   if ((d=='W') && (x<N-1)) 
	      v_wall[x][y] = false;
	 }
    
    public void buildAM(int s1, int s2) {
    	
    	g.insertEdge(s1, s2);
    }
    
    public void buildVertex() {
 	   
 	   g = new LinkedDigraph(); //initialize graph
 	   
 	   for(int i=0;i<(N*N);i++)
 		   g.insertVertex(i);
    }
    
    public void displayAM() {
    	g.display();
    }
    
    public void BFS(){
    	g.bfs(0);
    }
    
    public void DFS() {
    	g.dfs();
    }
     
    //Print Cell Numbers
   @SuppressWarnings("deprecation")
   public void printCellNumbers() { 
      StdDraw.setPenColor(StdDraw.BLUE);
      for (int x = 0; x < N; x++){
         for (int y = 0; y < N; y++){
            StdDraw.text(x+.5,y+.5,Integer.toString(x+N*y));    
         }
      }
      StdDraw.show(0);
   } 
 
   //Draw Method 
   @SuppressWarnings("deprecation")
   public void draw() {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.filledCircle(.5, .5, 0.375);
      StdDraw.setPenColor(StdDraw.GREEN);
      StdDraw.filledCircle(N-0.5, N-0.5, 0.375);
        
      //Draw Periphery. 
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.line(0, 0, N, 0); 
      StdDraw.line(N, 0, N, N); 
      StdDraw.line(N, N, 0, N);          
      StdDraw.line(0, N, 0, 0); 
      
      // Draw remaining walls
      for (int x = 0; x < N; x++)
         for (int y = 0; y < N-1; y++)
            if (h_wall[x][y]) StdDraw.line(x, y+1, x+1, y+1);      
      for (int x = 0; x < N-1; x++)
         for (int y = 0; y < N; y++)
            if (v_wall[x][y]) StdDraw.line(x+1, y, x+1, y+1);
      StdDraw.show(0);
   }
   
   //Initialize Method
   public static void initialize(int[] S) {
	   
	   for(int i =0;i<S.length;i++)
		   S[i] = -1;//initialize path
   }
   
   public static void initializeV(boolean[] v) {
	   for(int i =0;i<v.length;i++)
		   v[i]=false;//initialize visited vertex
   }
   
   //Find Method
   public static int find(int[] S,int x) {
	   
	   if(S[x] < 0)
		   return x;
	   else
		   return find(S,S[x]); //Recursive call on Index to find root
   }
   
   //Union By Height
   public static void unionByHeight(int[] S, int a, int b) {
	   
	   int ra = find(S,a);
	   int rb = find(S,b);
	   
	   if(ra==rb)
		   return;
	   
	   if(S[ra] > S[rb])
		   S[ra] = rb;
	   
	   else {
		   if(S[ra] < S[rb])
			   S[rb] = ra;
		   else {
			   S[rb] = ra;
			   S[S[rb]]--; //Increase Height
		   }
			   
	   }
   }
   
   //Has One Set Method
   public static boolean hasOneSet(int[] S) {
	   
	   int c = 0;
	   
	   for(int i=0;i<S.length;i++) {
		   if(S[i]<0)
			   c++;
	   }
	   if (c==1)
		   return true;
	   else
		   return false;
   }
   
   /*Standard Union Method */
   public static void StdUnionHT(int n, int m) {
	   
	   rand = new Random();
	   int cell = 0;
	   int neighbour = 0;
	   int ran = 0;
	   
	   S = new int[n*n];
	   initialize(S);
	   int N = n*n;
	   int i=0;
	   
	   Maze maze = new Maze(n);
	   
	   if(m>N-1) {
		  
		  while(i<N-1) {
		  cell = rand.nextInt(N-1);
		  ran = rand.nextInt(2);
		  
		  if(ran == 0 && (cell+n)/n <= n-1) { //Remove North
			  neighbour = cell + n;
			  if(find(S,cell) != find(S,neighbour)) { //If not in Same Set 
				 i++;
				 unionByHeight(S,cell,neighbour);
				 maze.remove_wall(neighbour%n, cell/n, 'N');
				 maze.buildAM(neighbour,cell);
				 maze.buildAM(cell, neighbour);
			 }
		  }
		  
		  else if ((cell+n)%n != n-1) { //Remove East
			  neighbour = cell + 1;
			  if(find(S,cell) != find(S,neighbour)) { //If not in Same Set 
				 i++;
				 unionByHeight(S,cell,neighbour);
				 maze.remove_wall(cell%n, neighbour/n, 'W');
				 maze.buildAM(cell,neighbour);
				 maze.buildAM(neighbour,cell);
			  } 
		  }
	   }
	   i=0;
	   	while(i <= (m-N)) {
		   cell = rand.nextInt(N-1);
		   ran = rand.nextInt(2);
		   if(ran == 0 && (cell+n)/n <= n-1){  //Remove North
		          neighbour = cell + n;
		          if(maze.h_wall[neighbour%n][cell/n]){
		        	  i++;
		        	  unionByHeight(S, cell, neighbour);
		        	  maze.remove_wall(neighbour%n, cell/n, 'N'); 
		        	  maze.buildAM(cell,neighbour);
		        	  maze.buildAM(neighbour,cell);
		          }
		   }
		   else if((cell)%n != n-1) { //Remove East
		          neighbour = cell + 1; 
		          if(maze.v_wall[cell%n][neighbour/n]){
		        	  i++;
		        	  unionByHeight(S, cell, neighbour);
		        	  maze.remove_wall(cell%n, neighbour/n, 'W');
		        	  maze.buildAM(cell,neighbour);
		        	  maze.buildAM(neighbour,cell);
		          }  
		   }
	   	}
	   }
	   
	   else{
		      while(i < m){
		        cell = rand.nextInt(N-1);
		        ran = rand.nextInt(2);
		        if(ran == 0 && (cell+n)/n <= n-1){ //Remove North
		          neighbour = cell + n;
		          if(find(S, cell) != find(S, neighbour)){
		        	  i++;
		        	  unionByHeight(S, cell, neighbour);
		        	  maze.remove_wall(neighbour%n, cell/n, 'N');
		        	  maze.buildAM(cell,neighbour);
		        	  maze.buildAM(neighbour,cell);
		          }
		        }
		        else if((cell)%n != n-1) { //Remove East
		        	neighbour = cell + 1;
		          if(find(S, cell) != find(S, neighbour)){
		            i++;
		            unionByHeight(S, cell, neighbour);
		            maze.remove_wall(cell%n, neighbour/n, 'W');
		            maze.buildAM(cell,neighbour);
		        	maze.buildAM(neighbour,cell);
		          }
		        }
		      }
	   }
	   
	   maze.draw();
	   maze.printCellNumbers();
	   
	   System.out.println();
	   maze.displayAM();
	   System.out.println();
	   
	   long startTimeBreadth = 0;
	   long endTimeBreadth = 0;
	   long totalTimeBreadth = 0;
	   
	   long startTimeDepth = 0;
	   long endTimeDepth = 0;
	   long totalTimeDepth = 0;
	   
	   System.out.println("Breadth-First Search Traversal is: ");
	   startTimeBreadth = System.currentTimeMillis();
	   maze.BFS();
	   endTimeBreadth = System.currentTimeMillis();
       totalTimeBreadth = endTimeBreadth - startTimeBreadth;
       System.out.println();
       System.out.println("The total time is for Breadth-First search is: " + totalTimeBreadth);
       System.out.println();
	   
	   
	   System.out.println("Depth-First Search Traversal is: ");
	   startTimeDepth = System.currentTimeMillis();
	   maze.DFS();
	   endTimeDepth = System.currentTimeMillis();
       totalTimeDepth = endTimeDepth - startTimeDepth;
       System.out.println("\n");
       System.out.println("The total time is Depth-First Search is: " + totalTimeDepth);
	   
   }
    
   /* Main Method */
    public static void main(String[] args) {
        
    	in = new Scanner(System.in);
    	int N;
    	int M;
    	
    	System.out.println("************* WELCOME TO LAB 7 *************");
    	System.out.println();
    	
    	System.out.println("Please enter the size maze you want to build");
    	N = in.nextInt();
    	
    	System.out.println("Please enter the number of walls you want to remove");
    	M = in.nextInt();
    	
    	System.out.println("The number of cells are: " + (N*N) + ", and the number of walls to be removed are: " + M);
    	System.out.println();
    	
        if (M < (N*N)-1)
        	System.out.println("A path from source to destination is not guaranteed to exist");
        
        if (M == (N*N)-1)
        	System.out.println("This is a unique path from source to destination.");
        
        if (M > (N*N)-1)
        	System.out.println("There is at least one path from source to destination.");
    	
        System.out.println();
        
        StdUnionHT(N,M);
   }
}
