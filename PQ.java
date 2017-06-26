//Vladimir Ventura, 6/26/2017
public class PQ {
	private Node root; //not good practice to use class within a separate class (2nd-lvl dependency?)
	//but putting them outside BST 
	private Node end; //used to swap with the root. or to find the next external node.
	
	public PQ(BST bst) throws CloneNotSupportedException
	{
		//root = new Node(bstroot.getKey(), bstroot.getValue());//just kidding, we don't know what the root is going to be yet.
		//if I wanted to heapify the tree, this would work, but no.
		//See, we have two options: 1) heapify O(lgn) the tree n times, i.e. O(nlgn) time complexity. This is the easiest and requires the least amount of code (I need to write the code for heapify anyway for the next portion), but it's not optimal and I'm not lazy o.o
		//2)traverse the BST in-order; add into heap from scratch. O(n) time complexity. And we get our root right away.
		//option #2 for optimization.
		inOrderAdd(bst.getRoot());
	}
	public PQ() {
		root = null;
	}
	
	public Node removeMax() {
		if(end == root) {
			Node temp = root;
			root = end = null;
			return temp;
		}
		Node removal = swapEndRoot(); //swaps end and root.
		updateEnd(); //removes end, updates the end to the new one.
		return removal;
	}
	
	public Node peek() {
		return root;
	}
	public boolean isEmpty() {
		return (root==null);
	}
	public void inOrderAdd(BST.Node root) {
		if(this.root==null) {
			this.root=this.end = new Node(root.getKey(), root.getValue());
			
		}
		else {
			Node node = new Node(root.getKey(), root.getValue());
			addNodeToEnd(node);
			heapifyUp();
		}
		
		
		if(root.getLeftChild()!=null) {
			inOrderAdd(root.getLeftChild());
		}
		if(root.getRightChild()!=null) {
			inOrderAdd(root.getRightChild());
		}
	}
	public void add(int k, int v) {
		Node newnode = new Node(k,v);
		addNodeToEnd(newnode);
		heapifyUp();
	}
	
	
	
	
	class Node implements Cloneable {
		private Node leftChild;
		private Node rightChild;
		private Node parent;
		private int key;
		private int value;
		
		public Node(int key, int value) {
			this.key = key; this.value= value;
		}
		public Node(){
			this.key=-1; //-1 == false, empty, null
			this.value=-1;
		}
		
		public int getKey() { return this.key;}
		public int getValue() { return this.value;}
		public boolean isLeafNode() {
			if(this.leftChild == null && this.rightChild == null)
				return true;
			else
				return false;
		}
		public Node clone() throws CloneNotSupportedException {
                return (Node)super.clone();
        }
	}
	
	
	
	
	
	
	//helper methods//
	private Node swapEndRoot() { //swap end and root
		if(root.leftChild == end) {
			root.leftChild=end;
			end.leftChild=root;
			root.parent=end;
			end.parent=null;
			//don't have to deal with rightChildren since, property of heap, there can't be a right child if the end is a left child.
		}
		else if(root.rightChild==end) {
			end.leftChild = root.leftChild;
			end.rightChild = root;
			root.leftChild = null;
			root.rightChild = null;
			root.parent= end;
			end.parent= null;
		}
		else if(end.parent.leftChild ==end){
			end.parent.leftChild = root;
			root.parent = end.parent;
			end.leftChild = root.leftChild;
			end.rightChild = root.rightChild;
			root.leftChild = root.rightChild = null;
			end.parent =null;
		}
		else {
			end.parent.rightChild = root;
			root.parent = end.parent;
			end.leftChild = root.leftChild;
			end.rightChild = root.rightChild;
			root.leftChild = root.rightChild = null;
			end.parent = null;
		}
		
		Node temp = root;
		root = end;
		end = temp; //temporarily the end. it is still connected (hence it's only a swap), but will be removed soon.
		return temp;
	}
	
	private void updateEnd(){
		if(root.leftChild == end) {
			end = root;
			return;
		}
		
		Node parent = end.parent;
		if(parent.rightChild == end) {//end is a rightChild
			parent.rightChild = null;
			end=parent.leftChild;
		}
		
		else if(parent.leftChild==end) {
			parent.leftChild=null;
			Node curr = parent;
			parent = parent.parent;
			while(parent.rightChild!=curr && parent != root) { //look for when curr is a rightChild; u must search the right-most left-subtree at that point.
				curr = parent;
				parent = parent.parent;
			}
			
			if(parent.rightChild==curr) {
				curr = parent.leftChild;
				while(curr.rightChild!=null) {
					curr = curr.rightChild;
				}
			}
			else { //parent is root; you got the left-most node, since it was never a part of a right subtree.
				curr = parent.rightChild;
				while(curr.rightChild!=null) {
					curr = curr.rightChild;
				}
			}
			
			end.parent = null; //end is still removal at this point.
			end = curr; //end finally updated.
			
		}
	}
	//Note: updateEnd uses the property that all nodes (except 1) will always be part of a right subtree. The only exception is if you're the left-most node (therefore it will ALWAYS be in the left-most subtree).
	private void addNodeToEnd(Node node){
		if(root==end) {
			root.leftChild=node;
			node.parent=root;
			end = node;
			return;
		}
		//opposite of updateEnd() in terms of code. we are trying to find the next, not previous, position.
		Node parent = end.parent;
		
		if(parent.leftChild == end) { //next position is the right child.
			parent.rightChild = node;
			node.parent = parent;
			end = node;
		}
		else { //parent.rightChild== end, so next position is leftmost node of the right subtree.
			Node curr = end;
			while(parent.leftChild!=curr && parent!=root){ //so we keep going up until we find a left subtree. if we don't find one, it's because we are at the right-most node of the right-most tree, so we must go to the left.
				curr = parent;
				parent = parent.parent;
			}
			if(parent.leftChild==curr) { //the left child is curr, meaning the next position is the leftmost of the RIGHT subtree.
				curr = parent.rightChild;
			}
			else { //parent is root; it's the only other condition where the while loop would stop. so we go to the leftmost.
				curr = parent.leftChild;
			}
			while(curr.leftChild!=null) {
				curr = curr.leftChild;
			}
			curr.leftChild = node;
			node.parent = curr;
			end = node;
		}
		
	}
	
	public void heapifyUp() { //enforce PQ definition. perform swaps 
		Node parent = end.parent;
		Node curr = end;
		while(swap(curr, parent)) {//if it does in fact swap, end will go up, and have a new parent.
			curr = parent;
			parent = curr.parent;
		}
	}
	public boolean swap(Node target, Node parent) { //this is a max pq, with priority on values.
		if(target == parent) {
			return false;
		}
		if (target.value>parent.value) { //swap is performed.
			if(parent.rightChild==target) {
				Node rightChild = target.rightChild;
				target.rightChild = parent;
				parent.parent = target;
				parent.rightChild = rightChild;
				rightChild.parent = parent;
				
				Node leftChild = target.leftChild;
				target.leftChild = parent.leftChild;
				parent.leftChild.parent = target;
				parent.leftChild = leftChild;
				leftChild.parent = parent;
			}
			else { //parent.leftchild == target
				Node rightChild = target.rightChild;
				target.rightChild = parent.rightChild;
				parent.parent = target;
				parent.rightChild = rightChild;
				rightChild.parent = parent;
				
				Node leftChild = target.leftChild;
				target.leftChild = parent;
				parent.leftChild.parent = target;
				parent.leftChild = leftChild;
				leftChild.parent = parent;
			}
			return true;
		}
		else
			return false;//no need to do any more than that.
		
	}
}