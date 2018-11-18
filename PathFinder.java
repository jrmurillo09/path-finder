
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class PathFinder {
	public static final int MAX_ENTRIES = 233;
	public static final int INF = 9999999;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ArrayList<HexNode> grid = getGrid();
		dijkstra(grid, 225);
		long end = System.currentTimeMillis(); 
		System.out.println("Total Time: " + (end - start)/1000.0);
	}

	public static void dijkstra(ArrayList<HexNode> grid, int start) {
		boolean[] intree = new boolean[MAX_ENTRIES];
		int[] cost = new int[MAX_ENTRIES];
		int[] path = new int[MAX_ENTRIES];
		HexNode currentNode = null;
		int w, v = start;

		for(int i = 0; i < MAX_ENTRIES; i++) {
			cost[i] = INF;
			path[i] = -1;
		}

		currentNode = grid.get(v);
		cost[v] = currentNode.getWeight();

		//for(int i = 0; i < MAX_ENTRIES; i++) {
	  	while(v != 7) {
			currentNode = grid.get(v);
			intree[v] = true;
			//if(v == 7)
			//	break;

			ArrayList<Integer> list = currentNode.getAdjacentNodes();

			for(int j = 0; j < list.size(); j++) {
				w = grid.get(list.get(j)).getWeight();
				if(intree[list.get(j)] == true)
					continue;

				if(cost[list.get(j)] > cost[v] + w) {
					cost[list.get(j)] = cost[v] + w; 
					path[list.get(j)] = grid.get(v).getIndex();
				}
			}
			//go to node with cheapest path.
			v = getMin(cost, intree);
		}
		drawPath(path, cost);
	}

	public static int getMin(int[] dist, boolean[] intree) {
		int min = INF;
		int index = 0;
		for(int i = 0; i < dist.length; i++) {
			if(intree[i] == true)
				continue;
			if(min > dist[i]) {
				min = dist[i];
				index = i;
			}
		}
		return index;
	}

	public static void drawPath(int[] path, int[] cost) {
		int targetNode = path[7];
		ArrayList<Integer> list = new ArrayList<>();

		try {
			PrintWriter writer = new PrintWriter("output.txt");
			
			list.add(0, 7);
			while(targetNode != -1) {
				list.add(0, targetNode);
				targetNode = path[targetNode];
			}
			for(Integer i : list)
				writer.println(i + 1);
			writer.println("MINIMAL-COST PATH COSTS: " + cost[7]);
			writer.close();
			
		} catch(FileNotFoundException e) {
			e.printStackTrace(); 
		}
	}

	public static ArrayList<HexNode> getGrid() {
		ArrayList<HexNode> grid = new ArrayList<>();
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
			while ((line = reader.readLine()) != null) {
				if(line.trim().isEmpty())
					continue;
				String[] nums = line.split("\\s+");
				int index = Integer.parseInt(nums[0]) - 1;
				int weight = Integer.parseInt(nums[1]);
				ArrayList<Integer> adjNodes = getAdjacentNodes(index);
				HexNode h = new HexNode(index, weight, adjNodes);
				grid.add(h);
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return grid;
	}

	public static ArrayList<Integer> getAdjacentNodes(int index) {
		ArrayList<Integer> nodes = new ArrayList<>();
		nodes.add(index - 15);
		nodes.add(index + 15);

		if (index % 15 == 0) {	//first column
			nodes.add(index - 7);
			nodes.add(index + 8);
			return filter(nodes);
		}
		if (index % 15 == 7) {	//last column
			nodes.add(index + 7);
			nodes.add(index - 8);
			return filter(nodes);
		}
		nodes.add(index - 8);
		nodes.add(index - 7);
		nodes.add(index + 7);
		nodes.add(index + 8);
		return filter(nodes);
	}

	public static ArrayList<Integer> filter(ArrayList<Integer> list) {
		Iterator<Integer> iter = list.iterator();
		while (iter.hasNext()) {
			Integer i = iter.next();
			if(i > 232 || i < 1) 
				iter.remove();
		}
		return list;
	}
}
