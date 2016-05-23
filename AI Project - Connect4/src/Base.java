import java.util.Scanner;

public class Base {

	 //Representing the playing field
	
	public static boolean gameOver = false;
	public static int maxDepth = 4;
	public static int columnChoice = 0;
			
	public static void main(String[] args) {
		short[][] grid = new short[6][7];
		
		System.out.println("Number of rows = " + grid.length);
		System.out.println("Number of columns = " + grid[0].length);
		
		
		
		
		int numMoves = 0;
		short player;
		
		while(gameOver == false)
		{
			printGrid(grid);
			player = (short) ((numMoves % 2) + 1);
			
			//Prompt for move
			int row = -1;
			int col = -1;
			while((col < 0 || col > 6) || (row < 0 || row > 5))
			{
				if(player == 1)
				{
					System.out.println("Which column would you like to play in?: ");
					Scanner in = new Scanner(System.in);
					col = in.nextInt();
				
					//Updates grid with new play
					row = play(grid, col, player);

				}
				
				else
				{	
					row = runAI(grid);
				
					if(isFull(grid, columnChoice))
						break;
					col = columnChoice;
					//columnChoice = -1;
				}
			}
			
				System.out.println("Check time!");
			//Performs vertical, horizontal and diagonal checks
			
			if( checkVertical(grid, row, col, player) || checkHorizontal(grid, row, col, player) || checkDiagonal(grid, row, col, player) )
				gameOver = true;
			
			if(gameOver == true)
			{	
				System.out.println("GAME OVER!!!\nPlayer " + player + " wins!");
				printGrid(grid);
			}
				
			
			columnChoice = -1;
			//Updates to next player
			numMoves++;
		}
		
	}
	
	public static void printGrid(short [][] grid)
	{

		for(int row = 0; row < grid.length; row++)
		{
			System.out.print("||");
			for(int col = 0; col < grid[row].length; col++)
				System.out.print("  " + grid[row][col] + "  |");
			System.out.println("|");
		}
		System.out.println("_____________________________________________");
	}
	
	public static boolean isFull(short[][] grid, int column)
	{
		boolean result = (grid[0][column] > 0)? true: false;
		return result;
	}

	
	public static int play(short[][] grid, int column, short player)
	{
		//For a selected column, update the grid to show a player's move
		//Returns value to signify success of move
		if(!isFull(grid, column))
		{
			for(int i = grid.length-1; i >= 0; i--)
			{
				if(grid[i][column] == 0)
					{
						grid[i][column] = player;
						return i;	//If a suitable play is found, break out of the loop after assignment and return row number
					}
			}
			
		}
		
		
			System.out.println("The specified column is full");
			return -1;
		
	}
	
	
	public static short[][] simulatePlay(short[][] tempGrid, int column, short player)
	{
			//For a selected column, update the grid to show a player's move
			short[][] panda = tempGrid.clone();
			if(!isFull(panda, column))
			{
				for(int i = panda.length-1; i >= 0; i--)
				{
					if(panda[i][column] == 0)
					{		
						panda[i][column] = player;
						break;
					}
				}
				
			}
			
			return panda;
			
	}
	
	public static boolean checkVertical(short[][] grid, int row, int col, int player)
	{
		int match = 1; 
		for(int i = 1; i < 4; i++)
		{
			if(row + i <= 5)
			{
				if(grid[row+i][col] == player)
					match++;
			}
		}
		
		if(match == 4)
			return true;
		
		return false;

	}
	
	
	public static boolean checkHorizontal(short[][] grid, int row, int col, int player)
	{
		int match = 1, i = 1;
		while(col-i >=0) 
		{
			if(grid[row][col-i] == player)
			{
				match++;
				i++;
			}
			else
				break;
		}
		
		i = 1;
		
		while(col+i <=6) 
		{
			if(grid[row][col+i] == player)
			{
				match++;
				i++;
			}
			else
				break;
		}
		
		if(match >= 4)
			return true;
		
		return false;

	}

	
	public static boolean checkDiagonal(short[][] grid, int row, int col, int player)
	{
		//This is for the SouthWest-NorthEast diagonal
		int match = 1, i = 1;
		while(col-i >=0 && row+i <=5) 
		{
			if(grid[row+i][col-i] == player)
			{
				match++;
				i++;
			}
			else
				break;
		}
		
		i = 1;
		
		while(col+i <=6 && row-i >= 0) 
		{
			if(grid[row-i][col+i] == player)
			{
				match++;
				i++;
			}
			else
				break;
		}
		
		if(match >= 4)
			return true;

		
		
		
		//This is for the NorthWest-SouthEast diagonal
		match = 1; 
		i = 1;
		while(col-i >=0 && row-i >=0) 
		{
			if(grid[row-i][col-i] == player)
			{
				match++;
				i++;
			}
			else
				break;
		}
		
		i = 1;
		
		while(col+i <=6 && row+i <= 5) 
		{
			if(grid[row+i][col+i] == player)
			{
				match++;
				i++;
			}
			else
				break;
		}
		
		if(match >= 4)
			return true;
		
		return false;
		
	}


	public static int runAI(short[][] grid)
	{
		double utility = generateGameTree(1, grid, (short) 2);

		if(utility == 0)
		{	
			double rand =(int) (Math.random() * 7);
			System.out.println("WE GOT IT!");
			columnChoice = (int) rand;	
		}
		
		
		
		System.out.println("Chosen Juan = " + columnChoice + " Utility: " + utility);
		int row = play(grid, columnChoice, (short) 2);
		return row;
	}
	
	public static double generateGameTree(int depth, short[][] currentGrid, short player)
	{
		double weight = 0;
		double max = -Double.MAX_VALUE;
		double min = Double.MAX_VALUE;
		int col = 0;
		
		for(int i = 0; i < 7; i++)
		{
			//System.out.println("Level: " + depth + ", Player: " + player + ", Column: " + i);
			if(!isFull(currentGrid, i))
			{	
				
				short[][] temp = currentGrid.clone();
				temp = simulatePlay(currentGrid, i, player);
				//System.out.println("Play simulated");
				//printGrid();
				
				int row = checkTopmost(temp, i);		//Double check for logical errors
				
				//System.out.println("Simulated move. Row: " + row + ", Column: " + i + "\n");
				if( !(checkVertical(temp, row, i, player) || checkHorizontal(temp, row, i, player) || checkDiagonal(temp, row, i, player)) )
				{
					if(depth < maxDepth)
						{ 
							weight = generateGameTree((depth+1), temp, (short)((player%2)+1));
							System.out.println("Weight = " + weight);
							//removePiece(temp, row, i);
							if(player == 2)
								{
									System.out.println(weight + "Blink!" + max);
									max = (weight > max)? weight: max;
									col = i;
								}
							else
								{
									//weight = -1 * weight;	//Negates weight to reflect opponents benefit
									min = (weight < min)? weight: min;
									
								}
							
						}
					
					else
					{
						//If Maximum depth is reached, assign weights to leaf nodes
						for(int j = 0; j < 7; j++)
						{	
							if((checkVertical(temp, row, j, player) || checkHorizontal(temp, row, j, player) || checkDiagonal(temp, row, j, player)) )
							{	
								weight = 1/depth;
								break;
							}
							
						}
						
//						if(player == 2)
//						{
//							max = (weight > max)? weight: max;
//							col = i;
//						}
//						
						if(player == 1)
						{
							if(weight != 0)
								weight = -1 * weight;	//Negates weight to reflect opponents benefit
							min = (weight < min)? weight: min;
						}
					
					}
			
				}
				
				else
				{	
					weight = (float) 1/depth;
					if(player == 1)
					{	
						weight = -1 * weight;
						min = weight;
					}
					
					System.out.println("I'm in ... Row: " + row + ", Col: " + i + ", Weight: " + weight + ", Depth: " + depth + ".  Player :" + player);
					removePiece(temp, row, i);
					
					
					if(depth > 1)
						return weight;		//To reflect weight of level
					
//					else if(depth == 1)
//						columnChoice = col;
				}
				
				removePiece(temp, row, i);
				//printGrid();
			}
			
			
			else
				{System.out.println("Full House");}
		}
		//printGrid(currentGrid);
		if(depth == 1)
		{
				System.out.println("Minimum = " + min + ", Maximum = " + max);
				columnChoice = col;
		}
		
		if(player == 2)	
		{
			System.out.println("Player " + player + "s turn at level " + depth + ". Sending max of " + max + "up!!");
			return max;
		}
		
		System.out.println("Player " + player + "s turn at level at level" + depth + ". Sending min of " + min + "up!!");
		return min;
		
	}
	
	public static short[][] removePiece(short[][]grid, int row, int col)
	{
		short[][] temp = grid;
		
		temp[row][col] = 0;
		//System.out.println("Removed piece!");
		//printGrid();
		
		return temp;
	}
	
	
	public static int checkTopmost(short[][] currentGrid, int col)
	{
		int row;
		for(row = 5; row >= 0; row--)
		{	
			if (currentGrid[row][col] == 0)
				return (row+1);
		}
		
		return 0;
		
	}
}
