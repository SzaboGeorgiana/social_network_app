package com.example.lab6_fx_15_11.ui;

import com.example.lab6_fx_15_11.domain.Entity;
import com.example.lab6_fx_15_11.service.PrietenieSrv;
import com.example.lab6_fx_15_11.service.UtilizatorSrv;

import java.util.*;

public class Retele <ID, E extends Entity<ID>> {
    UtilizatorSrv srv;
    PrietenieSrv srv1;

    public Retele(UtilizatorSrv srv, PrietenieSrv srv1) {
        this.srv = srv;
        this.srv1=srv1;

    }


    //    static Map<Integer, List<Integer>> friendships = new HashMap<>();

    public List<E> dfs(Iterable<E> fr, E start, Set<E> visited) {
        visited.add(start);
        Stack<E> stack = new Stack<>();
        List<E> component = new ArrayList<>();
        Iterable<E> ut=srv.get_all_srv();
        stack.push(start);

        while (!stack.isEmpty()) {
            E vertex = stack.pop();
            component.add(vertex);

            for (E neighbor : ut)
                if(srv1.findPrietenie((Long) vertex.getId(), (Long) neighbor.getId()))
                {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        visited.add(neighbor);
                    }
                }
        }

        return component;
    }

    public List<List<E>> findConnectedComponents() {
        Iterable<E> fr= srv1.get_all_srv();
        Iterable<E> ut= srv.get_all_srv();

        Set<E> visited = new HashSet<>();
        List<List<E>> components = new ArrayList<>();


        for (E vertex : ut) {
            if (!visited.contains(vertex)) {
                List<E> component = dfs(fr, vertex, visited);
                components.add(component);
            }
        }

        return components;
    }

    public List<E> findLongestPathComponent(List<List<E>> components) {
        List<E> longestComponent = Collections.emptyList();

        for (List<E> component : components) {
            if (component.size() > longestComponent.size()) {
                longestComponent = component;
            }
        }

        return longestComponent;
    }

    public void main1() {
//            friendships.put(1234, Arrays.asList(1235, 1236));
//            friendships.put(1235, Collections.singletonList(1234));
//            friendships.put(1236, Arrays.asList(1234, 1237));
//            friendships.put(1237, Collections.singletonList(1236));

        List<List<E>> connectedComponents = findConnectedComponents();
        int numarComunitati = connectedComponents.size();
        List<E> ceaMaiSociabilaComunitate = findLongestPathComponent(connectedComponents);

        System.out.println("Numarul de comunitati: " + numarComunitati);
        System.out.println("Cea mai sociabila comunitate: " + ceaMaiSociabilaComunitate);
    }
}
