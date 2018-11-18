
import java.util.ArrayList;

public class HexNode {
	private final int mIndex;
	private final int mWeight;
	private final ArrayList<Integer> mAdjacentNodes;

	public HexNode(int index, int weight, ArrayList<Integer> nodes) {
		mIndex = index;
		mWeight = weight;
		mAdjacentNodes = nodes;	
	}

	public int getIndex() {
		return mIndex;
	}

	public int getWeight() {
		return mWeight;
	}

	public ArrayList<Integer> getAdjacentNodes() {
		return mAdjacentNodes;
	} 
}
