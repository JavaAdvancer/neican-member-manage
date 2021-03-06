package com.jiyou.nm.common.utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildTree {

    public static <T> Tree<T> build(List<Tree<T>> nodes) {

        if (nodes == null) {
            return null;
        }
        List<Tree<T>> topNodes=new ArrayList<Tree<T>>();

        for (Tree<T> children : nodes) {

            String pid=children.getParentId();
            if (pid == null || "0".equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (Tree<T> parent : nodes) {
                String id=parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setChildren(true);
                    continue;
                }
            }

        }

        Tree<T> root=new Tree<T>();
        if (topNodes.size() == 1) {
            root=topNodes.get(0);
        } else {
            root.setId("-1");
            root.setParentId("");
            root.setHasParent(false);
            root.setChildren(true);
            root.setChecked(true);
            root.setChildren(topNodes);
            root.setText("顶级节点");
            Map<String, Object> state=new HashMap<>(16);
            state.put("opened", true);
            root.setState(state);
        }

        return root;
    }

    public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes, String idParam) {
        if (nodes == null) {
            return null;
        }


        List<Tree<T>> topNodes=new ArrayList<Tree<T>>();

        for (Tree<T> children : nodes) {

            String pid=children.getParentId();//1
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);

                continue;
            }

            for (Tree<T> parent : nodes) {
                String id=parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setChildren(true);

                    continue;
                }
            }

        }
        return topNodes;
    }

    public static <T> List<Tree<T>> buildList(List<Tree<T>> nodes){

        List<Tree<T>> list = new ArrayList<>();

        for (Tree<T> node : nodes){
            if ("0".equals(node.getParentId())){
                list.add(buildTree(node, nodes));
            }
        }

        return list;
    }

    public static <T> Tree<T> buildTree(Tree<T> node, List<Tree<T>> nodes){

        for (Tree<T> tree : nodes){
            if (node.getId().equals(tree.getParentId())){
                if (node.getChildren() == null)
                    node.setChildren(new ArrayList<>());
                node.getChildren().add(buildTree(tree, nodes));
            }
        }

        return node;
    }

}
