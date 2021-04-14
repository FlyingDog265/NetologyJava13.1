package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.helpers.NotFoundException;
import ru.netology.repository.IssueRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public class IssueManager {
    private final IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        this.repository = repository;
    }

    // Добавление ишью
    public void add(Issue item) {
        repository.add(item);
    }

    // Добавление сразу кучи ишью
    public void addAll(List<Issue> items) {
        repository.addAll(items);
    }

    // Показать все ишью в репозитории
    public List<Issue> getAll() {
        return repository.findAll();
    }

    // Список открытых ишью
    public List openedIssues() {
        List<Issue> opened = new ArrayList<>();
        for (Issue issue : repository.findAll()) {
            if (issue.getStatus()) {
                opened.add(issue);
            }
        }
        if (opened.isEmpty()) throw new NotFoundException("Открытые задачи не найдены!");
        return opened;
    }

    // Список закрытых ишью
    public List closedIssues() {
        List<Issue> closed = new ArrayList<>();
        for (Issue issue : repository.findAll()) {
            if (!issue.getStatus()) {
                closed.add(issue);
            }
        }
        if (closed.isEmpty()) throw new NotFoundException("Закрытые задачи не найдены!");
        return closed;
    }

    // Фильтрация по автору
    public List<Issue> filterByAuthor(Predicate<String> predicate) {
        List<Issue> result = new ArrayList<>();
        for (Issue item : repository.findAll()) {
            if (predicate.test(item.getAuthor())) {
                result.add(item);
            }
        }
        return result;
    }

    // Фильтрация по ярлыкам
    public List<Issue> filterByLabel(Predicate<HashSet> predicate) {
        List<Issue> result = new ArrayList<>();
        for (Issue item : repository.findAll()) {
            if (predicate.test(item.getLabels())) {
                result.add(item);
            }
        }
        return result;
    }

    // Фильтрация по исполнителям
    public List<Issue> filterByAssignee(Predicate<HashSet> predicate) {
        List<Issue> result = new ArrayList<>();
        for (Issue item : repository.findAll()) {
            if (predicate.test(item.getAssignee())) {
                result.add(item);
            }
        }
        return result;
    }

    // Сортировка
    public List<Issue> sortByIdDec(Comparator<Issue> dec) {
        ArrayList<Issue> result = new ArrayList<>(repository.findAll());
        result.sort(dec);
        return result;
    }

    public List<Issue> sortByIdInc(Comparator<Issue> inc) {
        ArrayList<Issue> result = new ArrayList<>(repository.findAll());
        result.sort(inc);
        return result;
    }

    // Открыть ишью
    public void openIssue(int id) {
        repository.openIssue(id);
    }

    // Закрыть ишью
    public void closeIssue(int id) {
        repository.closeIssue(id);
    }

}
