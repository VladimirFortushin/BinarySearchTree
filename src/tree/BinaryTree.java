package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Inorder traversal
-First, visit all the nodes in the left subtree
-Then the root node
-Visit all the nodes in the right subtree
inorder(root->left)
display(root->data)
inorder(root->right)

Preorder traversal
-Visit root node
-Visit all the nodes in the left subtree
-Visit all the nodes in the right subtree
display(root->data)
preorder(root->left)
preorder(root->right)

Postorder traversal
-Visit all the nodes in the left subtree
-Visit all the nodes in the right subtree
-Visit the root node
postorder(root->left)
postorder(root->right)
display(root->data)

 */
public class BinaryTree {
    public TreeNode root;

    private List<Integer> list = new ArrayList<>();

    public BinaryTree() {
        root = null;
    }

    public void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);

    }

    public void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }

        System.out.print(node.val + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val + " ");
    }

    public int findKthSmallestElem(TreeNode tree, int k) {
        inOrder(tree);
        Collections.sort(list);
        return list.get(k - 1);
    }

    // Adding new value to BST (Binary search tree)
    private TreeNode addRecursive(TreeNode currentNode, int value) {
        if (currentNode == null) {
            currentNode = new TreeNode(value);
        }

        if (value < currentNode.val) {
            currentNode.left = addRecursive(currentNode.left, value);
        } else if (value > currentNode.val) {
            currentNode.right = addRecursive(currentNode.right, value);
        } else {
            //value already exists
            return currentNode;
        }
        return currentNode;
    }

    // Define if BST contains specified value
    private boolean containsValueRecursive(TreeNode currentNode, int value) {
        if (currentNode == null) {
            return false;
        }

        if (value == currentNode.val) {
            return true;
        }

        return value < currentNode.val ? containsValueRecursive(currentNode.left, value)
                : containsValueRecursive(currentNode.right, value);
    }
    //To find the smallest value we have to go down the left side of the BST
    private int findSmallestNodeRecursive(TreeNode currentNode){
        return currentNode.right == null ? currentNode.val : findSmallestNodeRecursive(currentNode.right);
    }

    private int findGreatestNodeRecursive(TreeNode currentNode){
        return currentNode.left == null ? currentNode.val : findGreatestNodeRecursive(currentNode.left);
    }

    //Deletion of a specified value (node) from BST.
    //There are three scenarios of deletion:
    //1. When node for deletion has no children
    //2. When node has 1 child
    //3. When there are two children

    private TreeNode deleteRecursive(TreeNode currentNode, int value){
        if(currentNode == null){
            return null;
        }

        if(value == currentNode.val){
            //The value for deletion is found here
            //Paste code here depending on scenario

            //No children:
            if (currentNode.left == null && currentNode.right == null) {
                return null;
            }else{
                //One child:
                if (currentNode.right == null) {
                    return currentNode.left;
                }

                if (currentNode.left == null) {
                    return currentNode.right;
                }
            }

            //If there are two children, we need to find the replacement
            //And the replacement will be either the greatest node of the left subtree
            //Or the minimal node from the right subtree

            //Greatest left child:
            int greatestLeftVal = findGreatestNodeRecursive(currentNode);

            //Smallest right child:
            int smallestRightVal = findSmallestNodeRecursive(currentNode);

            //We'll use the smallest right:
            //Make the new link for the current node to be deleted: it will be linked on smallestRight:
            currentNode.val = smallestRightVal;
            //Then delete the node that replaced the deleted node:
            currentNode.right = deleteRecursive(currentNode.right, smallestRightVal);
            return currentNode;
        }

        if(value < currentNode.val){
            currentNode.left = deleteRecursive(currentNode.left, value);
            return currentNode;
        }

        currentNode.right = deleteRecursive(currentNode.right, value);
        return currentNode;
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }



    public static void main(String[] args) {

        BinaryTree tree = new BinaryTree();
        tree.root = new TreeNode(6);
        tree.root.left = new TreeNode(5);
        tree.root.right = new TreeNode(12);
        tree.root.left.left = new TreeNode(1);
        tree.root.left.right = new TreeNode(7);


        tree.inOrder(tree.root);
        tree.addRecursive(tree.root, 20);
        System.out.println();
        tree.inOrder(tree.root);
        tree.deleteRecursive(tree.root, 6);
        System.out.println();
        tree.inOrder(tree.root);


    }

}
