package RedBlackTreeIterator;

public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T> {

    int count =0;
    RedBlackTree(){
        root = new RBTNode<>(null);
    }
    
    @Override
    public void insert(T data) throws NullPointerException{
        count++;
        if(data != null) {
            if(this.root.data == null){
                root = new RBTNode<>(data);
            } else {
                RBTNode<T> newRedNode = new RBTNode<>(data); // default isRed = true.
                BSTNode<T> currentNode = this.root;

                insertHelper(newRedNode, currentNode);

                ((RBTNode<T>) this.root).isRed = false; // ensures that the root node stays black
                ensureRedProperty(newRedNode);
            }
            ((RBTNode<T>) this.root).isRed = false;
        } else {
            throw new NullPointerException("Data attempting to be inserted is NULL!");
        }
    }

    protected void ensureRedProperty(RBTNode<T> child){
        if(child != root && child.getUp() != null) { // as long as child is not root and has a parent
            // initialize parent and the grandparent
            RBTNode<T> parent = child.getUp();
            RBTNode<T> grandParent = parent.getUp();
            RBTNode<T> uncle; // uncle default = null

            // check if both the parent and child are red (red property violation)
            if (parent.isRed() && child.isRed) {
                // identify uncle node
                if (grandParent != null) {
                    if (grandParent.getLeft() == parent) {
                        uncle = grandParent.getRight(); // if parent is the left of grandparent, uncle is the right of grandparent
                    } else {
                        uncle = grandParent.getLeft(); // if parent is the right of grandparent, uncle is the left of grandparent
                    }

                    // if the uncle is null or black
                    if (uncle == null || !uncle.isRed) {
                        /*
                         * if there is a line to get to the grandparent (if), we perform different
                         * commands than if it was not a line (a triangle) (else).
                         */
                        if ((child.data.compareTo(parent.data) > 0 && child.data.compareTo(grandParent.data) > 0)
                        || (child.data.compareTo(parent.data) <= 0 && child.data.compareTo(grandParent.data) <= 0)) {
                            rotate(parent, grandParent); // rotate parent and grandParent
                            parent.flipColor(); // flip the color of the parent
                            grandParent.flipColor(); // flip the color of the grandParent
                        } else {
                            rotate(child, parent); // rotate child and parent
                            rotate(child, grandParent); // rotate parent and grandParent
                            child.flipColor(); // flip the color of the parent
                            grandParent.flipColor(); // flip the color of the grandParent
                        }

                        return; // terminate recursion as the problem has been fixed

                    } else { // if the uncle is red
                        parent.flipColor(); // flip the color of the parent
                        uncle.flipColor(); // flip the color of the uncle
                        grandParent.isRed = true; // set the grandParent to red
                    }
                }
            }
                if(grandParent != root) {
                    ensureRedProperty(parent); // parent is now checked for a red property violation and is the new child
                }
            }
        }
}
