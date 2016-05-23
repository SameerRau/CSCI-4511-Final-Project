
public class GridConfig {

	private short[][] grid;
	private int player;
	private int depth;
	private int weight;
	
	public GridConfig(short[][] gridPassed, int play, int deep)
	{
		grid = gridPassed;
		player = play;
		depth = deep;
	}
	
}
