import java.util.ArrayList;

/**
 * @author Eliad Eini
 * Node for the GenericTree that can be used with various types.
 * @param <T> - The type or class that will be the data in the Tree's node.
 */
public class GenericTreeNode<T> {
	
	/**
	 * By using this constructor, we creating a node that can have an unlimited number
	 * of sons in a list of sons. in this case, empty elements are not possible.
	 * @param element - The data that the node should hold.
	 * @param ancestor - The new node's ancestor. Can be 'null'.
	 */
	public GenericTreeNode(T element, GenericTreeNode<T> parent) {
		setData(element);
		sons = new ArrayList<GenericTreeNode<T>>();
		currIndex = 0;
		fixedSize = -1;
		numOfSons = 0;
		setParent(parent);
	}
	
	
	/**
	 * By using this constructor, we creating a node that has a fixed number of sons,
	 * actually an array. That means that the user treats the sons list as an array and 
	 * empty elements are possible.
	 * @param element - The data that the node should hold.
	 * @param sonsSize - Size of the array of sons to this node. If it is smaller than 1,
	 * 					 the number of sons will be unlimited (like using the previous
	 * 					 Constructor).
	 */
	public GenericTreeNode(T element, GenericTreeNode<T> parent, Integer sonsSize) {
		setData(element);
		sons = new ArrayList<GenericTreeNode<T>>(sonsSize);
		currIndex = 0;
		setParent(parent);
		fixedSize = (sonsSize > 0) ? sonsSize : -1;
		numOfSons = 0;
	}
	
	
	/* Adds a son to a specific index in the array. The action fails if that index is 
	 * occupied and returns 'false', otherwise returns 'true' 
	 */
	public boolean addSon(GenericTreeNode<T> newSon, Integer index) {
		if (((fixedSize != -1) && ((index >= fixedSize) || (sons.get(index) != null))) 
			|| (index < 0) || ((fixedSize == -1) && (index > sons.size()))) {
			return false;
		}
		if (fixedSize == -1) {
			sons.add(index, newSon);
		}
		else {
			sons.set(index, newSon);
		}
		numOfSons++;
		return true;
	}
	
	/**
	 * Removes son and its descendants from current node.
	 * Note: In case of 'fixedSize == -1' (unlimited number of sons), the sons that 
	 * 		 comes after the removed son will shift their indexes. 
	 * @param index - Index of son to remove.
	 */
	public void removeSon(int index) {
		if (index < 0) {
			return;
		}
		if (fixedSize != -1) {
			if (index >= fixedSize) {
				return;
			}
			sons.set(index, null);
		}
		else {
			if (index >= numOfSons) {
				return;
			}
			sons.remove(index);
		}
		numOfSons--;
	}
	

	public T getData() {
		return data;
	}

	
	public void setData(T data) {
		this.data = data;
	}
	
	
	public GenericTreeNode<T> getFirstSon() {
		currIndex = 0;
		if (sons.size() == 0) {
			return null;
		}
		return sons.get(currIndex);
	}
	
	
	public GenericTreeNode<T> getNextSon() {
		if (currIndex >= sons.size() - 1) {
			return null;
		}
		currIndex++;
		return sons.get(currIndex);
	}
	
	
	public GenericTreeNode<T> getSonByIndex(int index) {
		/* In case of invalid index */
		if ((index < 0) || ((fixedSize == -1) && (index >= numOfSons))
			|| ((fixedSize > 0) && (index >= fixedSize))) {
			return null;	
		}
		
		return sons.get(index);
	}
	
	
	public boolean isLastSon() {
		return (currIndex == sons.size() - 1);
	}
	
	
	public boolean isALeaf() {
		return (numOfSons == 0);
	}
	
	
	public Integer getNumOfSons() {
		return numOfSons;
	}
	
	
	public GenericTreeNode<T> getParent() {
		return Parent;
	}


	public void setParent(GenericTreeNode<T> parent) {
		Parent = parent;
	}

	/**
	 * 				Private Fields
	 */
	private int currIndex;

	private T data;
	
	private ArrayList<GenericTreeNode<T> > sons;
	
	private Integer numOfSons;
	
	/* If fixedSize == -1 then the node can have an unlimited number of sons. 
	 * If fixedSize > 0 then the maximum number of sons will be fixedSize.
	 */
	private Integer fixedSize; 
	
	/* The parent node of this node. */
	private GenericTreeNode<T> Parent;
}
