package ru.netology.repository;

import ru.netology.domain.Issue;
import ru.netology.helpers.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class IssueRepository {
    private final List<Issue> issues = new ArrayList<>();

    public void add(Issue item) {
        issues.add(item);
    }

    public void addAll(List<Issue> items) {
        issues.addAll(items);
    }

    public List<Issue> findAll() {
        return issues;
    }

    public Issue findById(int id) {
        for (Issue item : issues) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void openIssue(int id) {
        if (findById(id) != null) {
            for (Issue item : issues) {
                if (item.getId() == id) {
                    item.setStatus(true);
                }
            }
        } else {
            throw new NotFoundException("Элемент с ID " + id + " не найден!");
        }
    }

    public void closeIssue(int id) {
        if (findById(id) != null) {
            for (Issue item : issues) {
                if (item.getId() == id) {
                    item.setStatus(false);
                }
            }
        } else {
            throw new NotFoundException("Элемент с ID " + id + " не найден!");
        }
    }
}