import java.util.ArrayList;
import java.util.List;
public class AVLTree {
    AVLNode root;

    AVLTree() {
        this.root = null;
    }

    public void insert(User user) {
        if (this.root == null) {
            this.root = new AVLNode(user);
            return;
        }

        AVLNode p = root;
        while (true) {
            if (user.getID().compareTo(p.data.getID()) < 0) {
                if (p.lchild != null) {
                    p = p.lchild;
                } else {
                    p.lchild = new AVLNode(user);
                    p.lchild.parent = p;
                    return;
                }
            } else if (user.getID().compareTo(p.data.getID()) > 0) {
                if (p.rchild != null) {
                    p = p.rchild;
                } else {
                    p.rchild = new AVLNode(user);
                    p.rchild.parent = p;
                    return;
                }
            } else {
                return;
            }
        }
    }

    public AVLNode query(String userID) {
        AVLNode p = root;
        while (p != null) {
            int cmp = p.data.getID().compareTo(userID);
            if (cmp < 0) {
                p = p.rchild;
            } else if (cmp > 0) {
                p = p.lchild;
            } else {
                return p;
            }
        }
        return null;
    }

    public void preOrder(AVLNode root) {
        if (root != null) {
            preOrder(root.lchild);
            preOrder(root.rchild);
        }
    }

    public List<AVLNode> inOrderTraversal() {
        List<AVLNode> nodes = new ArrayList<>();
        inOrder(root, nodes);
        return nodes;
    }

    private void inOrder(AVLNode node, List<AVLNode> nodes) {
        if (node != null) {
            inOrder(node.lchild, nodes);
            nodes.add(node);
            inOrder(node.rchild, nodes);
        }
    }

    public void postOrder(AVLNode root) {
        if (root != null) {
            postOrder(root.lchild);
            postOrder(root.rchild);
        }
    }

    private void removeNode1(AVLNode node) {
        if (node.parent == null) {
            root = null;
        }
        assert node.parent != null;
        if (node == node.parent.lchild) {
            node.parent.lchild = null;
        } else {
            node.parent.rchild = null;
        }
    }

    private void removeNode21(AVLNode node) {
        if (node.parent == null) {
            root = node.lchild;
            node.lchild.parent = null;
        } else if (node == node.parent.lchild) {
            node.parent.lchild = node.lchild;
            node.lchild.parent = node.parent;
        } else {
            node.parent.rchild = node.lchild;
            node.lchild.parent = node.parent;
        }
    }

    private void removeNode22(AVLNode node) {
        if (node.parent == null) {
            root = node.rchild;
            node.rchild.parent = null;
        } else if (node == node.parent.lchild) {
            node.parent.lchild = node.rchild;
            node.rchild.parent = node.parent;
        } else {
            node.parent.rchild = node.rchild;
            node.rchild.parent = node.parent;
        }
    }

    boolean delete(String userID) {
        AVLNode node = query(userID);
        if (node == null) {
            return false;
        }

        if (node.lchild == null && node.rchild == null) {
            removeNode1(node);
        } else if (node.lchild == null) {
            removeNode22(node);
        } else if (node.rchild == null) {
            removeNode21(node);
        } else {
            AVLNode minNode = node.rchild;
            while (minNode.lchild != null) {
                minNode = minNode.lchild;
            }
            node.data = minNode.data;
            if (minNode.rchild != null) {
                removeNode22(minNode);
            } else {
                removeNode1(minNode);
            }
        }

        return true;
    }

    private void rotateLeft(AVLNode p, AVLNode c) {
        AVLNode s2 = c.lchild;
        p.rchild = s2;
        if (s2 != null) {
            s2.parent = p;
        }
        c.lchild = p;
        p.parent = c;

        p.bf = 0;
        c.bf = 0;
    }

    private void rotateRight(AVLNode p, AVLNode c) {
        AVLNode s2 = c.rchild;
        p.lchild = s2;
        if (s2 != null) {
            s2.parent = p;
        }
        c.rchild = p;
        p.parent = c;

        p.bf = 0;
        c.bf = 0;
    }

    private void rotateRightLeft(AVLNode p, AVLNode c) {
        AVLNode g = c.lchild;

        AVLNode s3 = g.rchild;
        c.lchild = s3;
        if (s3 != null) {
            s3.parent = c;
        }
        g.rchild = c;
        c.parent = g;

        AVLNode s2 = g.lchild;
        p.rchild = s2;
        if (s2 != null) {
            s2.parent = p;
        }
        g.lchild = p;
        p.parent = g;

        if (g.bf > 0) {
            p.bf = -1;
            c.bf = 0;
        } else if (g.bf < 0) {
            p.bf = 0;
            c.bf = 1;
        } else {
            p.bf = 0;
            c.bf = 0;
        }
    }

    private void rotateLeftRight(AVLNode p, AVLNode c) {
        AVLNode g = c.rchild;

        AVLNode s2 = g.lchild;
        c.rchild = s2;
        if (s2 != null) {
            s2.parent = c;
        }
        g.lchild = c;
        c.parent = g;

        AVLNode s3 = g.rchild;
        p.lchild = s3;
        if (s3 != null) {
            s3.parent = p;
        }
        g.rchild = p;
        p.parent = g;

        if (g.bf < 0) {
            p.bf = 1;
            c.bf = 0;
        } else if (g.bf > 0) {
            p.bf = 0;
            c.bf = -1;
        } else {
            p.bf = 0;
            c.bf = 0;
        }
    }
}
