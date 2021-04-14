package ru.netology.domain;

import java.util.Comparator;

public class IssueComparatorDec implements Comparator<Issue> {

    @Override
    public int compare(Issue first, Issue second) {
        return second.getId() - first.getId();
    }
}