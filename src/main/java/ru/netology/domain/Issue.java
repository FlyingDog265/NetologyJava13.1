package ru.netology.domain;

import java.util.HashSet;

@SuppressWarnings("rawtypes")
public class Issue {
    private int id; // айдишник
    private HashSet assignee; // исполнитель
    private boolean status; // статус
    private HashSet labels; // ярлычки
    private String author; // автор
    private String description; // описание

    public Issue() {
    }

    public Issue(int id, boolean status, HashSet<String> assignee, String author, HashSet<String> labels, String description) {
        this.id = id;
        this.status = status;
        this.labels = labels;
        this.author = author;
        this.assignee = assignee;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public HashSet getLabels() {
        return labels;
    }

    public String getAuthor() {
        return author;
    }

    public HashSet getAssignee() {
        return assignee;
    }

    public String getDescription() {
        return description;
    }
}
