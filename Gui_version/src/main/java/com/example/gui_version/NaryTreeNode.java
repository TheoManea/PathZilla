package com.example.gui_version;

import java.util.ArrayList;
import java.util.List;

public class NaryTreeNode<T> {
    private T data;
    private List<NaryTreeNode<T>> children;
    private boolean isTerminal;
    public NaryTreeNode<T> father;

    public NaryTreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setFather(NaryTreeNode<T> father) {
        this.father = father;
    }

    public List<NaryTreeNode<T>> getChildren() {
        return children;
    }

    public void addChild(NaryTreeNode<T> child) {
        children.add(child);
    }
    public void clearChildren(){
        this.children.clear();
    }
    public boolean getIsTerminal(){
        return isTerminal;
    }
    public void setTerminal(boolean terminal) {
        isTerminal = terminal;
    }

}

