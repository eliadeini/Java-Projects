

public class TestGenericTree {

	public static void main(String[] args) {
		GenericTree<Integer> tree1 = new GenericTree<Integer>(-1, 1);
		GenericTreeNode<Integer> root_node = tree1.getRoot();
		tree1.addSonToNode(root_node, 2, true);
		tree1.addSonToNode(root_node, 3, true);
		GenericTreeNode<Integer> node3 = root_node.getSonByIndex(1);
		tree1.addSonToNode(root_node, 5, true);
		tree1.addSonToNode(root_node, 6, true);
		tree1.addSonToNode(node3,4, true);
		tree1.addSonToNode(root_node, 7, true);
		tree1.addSonToNode(root_node, 8, true); 
		if (tree1.SearchElement(4) == null) {
			System.out.println("4 was not found!");
		}
		else {
			System.out.println("4 was found!");
		}

		tree1.PrintTree();		
	}

}
