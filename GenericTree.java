/**
 * @author Eliad Eini
 * A generic tree that can be used with various types.
 * @param <T> - The type or class that will be the data in the Tree's node. Must be 
 * 				Comparable and have a proper 'toString()' method in order to be 
 * 				printed properly.
 */
public class GenericTree<T extends Comparable<T>> {

	/**
	 * 								Constructors
	 */
	/** 
	 * Default Constructor: Creates an empty Tree with an unlimited number of sons. 
	 */
	public GenericTree() {
		Root = null;
		numOfSons = -1;
	}
	
	
	/**
	 * This Constructor builds a Tree with a fixed number of sons per node (unless 
	 * 'numberOfSons' is smaller than 1) and adds a root with the given element
	 * @param numberOfSons - Fixed number of sons per node, unlimited in case that 
	 * 						 'numberOfSons' < 1.
	 * @param rootElement - The element that will be contained in the root.
	 */
	public GenericTree(int numberOfSons, T rootElement) {
		Root = new GenericTreeNode<T>(rootElement, null);
		numOfSons = numberOfSons;
	}
	
	
	public GenericTree(int numberOfSons) {
		Root = null; 
		numOfSons = numberOfSons;
	}
	
	
	/**
	 * 									Methods
	 */
	/**
	 * This function gets a node and adds a new son to it that contains 'element' as
	 * the data. It puts the new son at the end of the sons list.
	 * Note: This function fits only for nodes WITHOUT a fixed number of sons. Therefore
	 * 		 it will return 'False' in case of using with a fixed number of sons.
	 * @param node - The node that we want to add a son to it.
	 * @param element - The element that the new son should contain.
	 * @param keepSorted - In case that we keep the order of sons sorted and we want to
	 * 					   keep the sons sorted (T is a comparable type).
	 * 					   It is the user's responsibility to use 'keepSorted' in any use
	 * 					   of this function if he wants to keep his tree sorted.
	 * @return True if succeeded, False in a case of failure.
	 */
	public boolean addSonToNode(GenericTreeNode<T> node, T element, boolean keepSorted) {
		if (numOfSons != -1) {
			/* An index is missing, 'addSonToNodeWithIndex' should be used.  */
			return false;
		}
		GenericTreeNode<T> newNode = new GenericTreeNode<T>(element, node);
		boolean res = false;
		if (keepSorted) {
			GenericTreeNode<T> currNode = node.getFirstSon();
			int index = 0;
			while ((currNode != null) && (element.compareTo(currNode.getData()) > 0)) {
				currNode = node.getNextSon();
				index++;
			}
			res = node.addSon(newNode, index);
			return res;
		}
		else {
			res = node.addSon(newNode, node.getNumOfSons());
		}
		return res;
	}
	
	
	public boolean addSonToNodeWithIndex(GenericTreeNode<T> node, T element, int index) {
		if ((node == null) || (index < 0) || ((numOfSons == -1) && (index > node.getNumOfSons())) 
			|| ((numOfSons != -1) && (index > numOfSons))) {
			/* Invalid index */
			return false;
		}
		return node.addSon(node, index);
	}
	
	
	/* Search element in tree using DFS algorithm */
	public GenericTreeNode<T> SearchElement(T elemToFind) {
		return searchElementRecursiveDFS(Root, elemToFind);
	}
	
	/**
	 * Prints the tree using indentations for descendants.
	 * Note: In order to be printed, the generic type needs to override its 'toString()'
	 * 		 method if necessary.
	 */
	void PrintTree() {
		printTreeRecursiveDFS(Root, 0);
	}
	
	
	GenericTreeNode<T> getRoot() {
		return Root;
	}


	/**
	 * 				Private Fields and Functions
	 */
	/** A recursive function that search the tree with DFS algorithm.       
	 * @param fromNode - A node (which is a root of a sub-Tree) to start from. 
	 * @param elemToFind - Element that we want to find.
	 * @return - A node that contains this element or null in case that it doesn't 
	 * 			 exists.
	 */
	private GenericTreeNode<T> searchElementRecursiveDFS(GenericTreeNode<T> fromNode, T elemToFind) {
		if (fromNode == null) {
			return null;
		}
		
		if (fromNode.getData().equals(elemToFind)) {
			return fromNode;
		}
		
		GenericTreeNode<T> currSon = fromNode.getFirstSon();
		do {
			GenericTreeNode<T> res = searchElementRecursiveDFS(currSon, elemToFind);
			if (res != null) {
				return res;
			}
			currSon = currSon.getNextSon();
		} while ((currSon != null) && (!currSon.isLastSon()));
		
		return null;
	}
	
	
	/** 
	 * This function navigates the Tree by using DFS algorithm and prints it by 
	 * indenting sons of a node. 
	 * @param root - The root of the sub-Tree to be printed.
	 * @param numOfIndentations - How many indentations should we start printing with.
	 */
	private void printTreeRecursiveDFS(GenericTreeNode<T> root, int numOfIndentations) {
		/* First of all, indentation is needed */
		for (int i = 0; i < numOfIndentations; i++) {
			System.out.print("   ");
		}
		
		if (root == null) {
			System.out.println("null");
			return;
		}
		
		System.out.println(root.getData().toString());
		
		if (root.getNumOfSons() == 0) {
			return;
		}
		
		GenericTreeNode<T> currNode = root.getFirstSon();
		while (currNode != null) {
			printTreeRecursiveDFS(currNode, numOfIndentations + 1);
			currNode = root.getNextSon();
		}
	}
	
	
	private GenericTreeNode<T> Root;
	
	/**
	 * The value of this field determines if the nodes of the tree will have a fixed 
	 * number of sons. For example: If (numOfSons == 2), it will be a binary tree, if
	 * (numOfSons == 1) it will be a linked list.
	 */
	private int numOfSons;
}
