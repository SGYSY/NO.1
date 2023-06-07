public class AVLNode {
    int bf;
    User data;
    AVLNode lchild;
    AVLNode rchild;
    AVLNode parent;

    AVLNode(User data) {
        bf = 0;
        this.data = data;
        this.lchild = null;
        this.rchild = null;
        this.parent = null;
    }
}