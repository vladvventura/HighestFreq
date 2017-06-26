//Vladimir Ventura 6/26/2017
import java.lang.Exception;

public class BST {
		private Node root;
		//constructor//
		public BST(int key, int value) {
			root = new Node(key, value);
		}
		public BST() {
			root = null;
		}
		
		//methods//
		public Node getRoot() {return root;}
		public void add(int k, int v) throws Exception{
			if(root == null) {
				root = new Node(k,v);
				return;
			}
			//search for the #. if it finds a key, it fails and outputs an error.
			//if it doesn't find a key, it adds it to the end of the tree.
			Node exists= search(k);
			if(exists.getKey()==k){ //key exists
				throw new Exception("Key already exists");
			}
			else {
				Node newNode = new Node(k,v);
				if(k>exists.getKey()) {
					exists.rightChild =newNode;
					newNode.parent=exists;
				}
				else {
					exists.leftChild=newNode;
					newNode.parent=exists;
				}
			}
		}
		
		public Node search(int key) throws Exception{
			if(root==null){
				throw new Exception("No nodes exist yet.");
			}
			Node curr = root;
			Node next = root;
			
			while(next!=null) {
				if(next.key==key)
					return next;
				curr = next;
				if(key>curr.key) {
					next = curr.rightChild;
				}
				else
					next = curr.leftChild;
			}
			return curr;
		}
		public boolean remove(int key) throws Exception{
			Node exists = search(key);
			if(exists.key!=key){//didn't find it
				throw new Exception("Node not found");
			}
			else { //it does exist.
				if(exists.isLeafNode()){ //it's a leaf node
					removeLeaf(exists);
				}
				else if (exists.rightChild==null){
					removeLeft(exists);
				}
				else if (exists.leftChild==null) {
					removeRight(exists);
				}
				else {
					removeInner(exists);
				}
				return true;
			}
		}
		public void getValues(Node root){
			if(root.leftChild == null)
				System.out.println(root.key + ",\t" + root.value);
			else{
				getValues(root.leftChild);
				System.out.println(root.key + ",\t" + root.value);
			}
			if(root.rightChild!=null)
				getValues(root.rightChild);
			
		}
		
		public void replaceValue(int k, int v) throws Exception{
			Node exists =search(k);
			if(exists.getKey()==k){ //key exists
				exists.value = v;
			}
			else {
				add(k,v);
			}
		}
		public void addWithValues(int k, int v) throws Exception{
			if(root == null) {
				root = new Node(k,v);
				return;
			}
			//search for the #. if it finds a key, it fails and outputs an error.
			//if it doesn't find a key, it adds it to the end of the tree.
			Node exists= search(k);
			if(exists.getKey()==k){ //key exists
				exists.value+=v;
				return;
			}
			else {
				Node newNode = new Node(k,v);
				if(k>exists.getKey()) {
					exists.rightChild =newNode;
					newNode.parent=exists;
				}
				else {
					exists.leftChild=newNode;
					newNode.parent=exists;
				}
			}
		}
	
		//Removal Methods//
		
		public void removeLeaf(Node node){
			Node parent = node.parent;
			if(parent.leftChild == node)
				parent.leftChild=null;
			else
				parent.rightChild=null;
		}
		public void removeLeft(Node node) {
			Node parent = node.parent;
			if(parent.leftChild == node) {
				parent.leftChild = node.leftChild;
			}
			else {
				parent.rightChild = node.leftChild;
			}
			node.leftChild.parent = parent;

		}
		public void removeRight(Node node) {
			Node parent = node.parent;
			if(parent.leftChild == node) {
				parent.leftChild = node.rightChild;
			}
			else {
				parent.rightChild = node.rightChild;
			}
			node.rightChild.parent = parent;

		}
		
		public void removeInner(Node node) {
			//find the leftMost node in the right subtree. swap, perform removeRight (leftmost should have a leftChild null
			Node parent = node.parent;
			Node risingNode;
			
			risingNode = findLeftMostNode(node.rightChild);
			
			removeRight(risingNode);
			risingNode.parent = parent;
			if(parent.leftChild == node) {
				parent.leftChild= risingNode;
			}
			else {
				parent.rightChild = risingNode;
			}
			risingNode.leftChild=node.leftChild;
			risingNode.rightChild=node.rightChild;
		}
		
		public Node findLeftMostNode(Node root) { //find leftmost node in this subtree
			if(root.leftChild==null)
				return root;
			else
				return findLeftMostNode(root.leftChild); //recursively go down the tree until you get the left-most child.
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
		public Node getLeftChild() { return this.leftChild;}
		public Node getRightChild() { return this.rightChild;}
		public Node getParent() { return this.parent;}
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
}