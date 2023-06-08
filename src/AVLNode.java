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

    public int getBf() {
        return bf;
    }

    public User getData() {
        return data;
    }

    public AVLNode getLchild() {
        return lchild;
    }

    public AVLNode getRchild() {
        return rchild;
    }

    public AVLNode getParent() {
        return parent;
    }

    public void setBf(int bf) {
        this.bf = bf;
    }

    public void setData(User data) {
        this.data = data;
    }

    public void setLchild(AVLNode lchild) {
        this.lchild = lchild;
    }

    public void setRchild(AVLNode rchild) {
        this.rchild = rchild;
    }

    public void setParent(AVLNode parent) {
        this.parent = parent;
    }
}