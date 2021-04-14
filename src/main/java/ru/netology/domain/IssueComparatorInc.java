package ru.netology.domain;

import java.util.Comparator;

public class IssueComparatorInc implements Comparator<Issue> {

    @Override
    public int compare(Issue first, Issue second) {
        return first.getId() - second.getId();
    }
}