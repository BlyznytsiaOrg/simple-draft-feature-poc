package com.levik.features.cyclic;

import java.util.*;

public class CyclicDependencyChecker {

    private final Map<String, List<String>> dependencies;

    public CyclicDependencyChecker()
    {
        dependencies = new HashMap<>();
    }

    public boolean addDependency(String dependent, String dependency)
    {
        if (hasCyclicDependency(dependent, dependency))
        {
            return true;
        }

        dependencies.computeIfAbsent(dependent, k -> new ArrayList<>()).add(dependency);
        return false;
    }

    private boolean hasCyclicDependency(String start, String current)
    {
        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push(current);

        while (!stack.isEmpty())
        {
            String node = stack.pop();

            if (!visited.contains(node))
            {
                visited.add(node);
                List<String> dependents = dependencies.getOrDefault(node, Collections.emptyList());

                for (String dependent : dependents)
                {
                    if (dependent.equals(start))
                    {
                        return true;
                    }
                    stack.push(dependent);
                }
            }
        }

        return false;
    }
}
