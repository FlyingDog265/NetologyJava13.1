package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.domain.IssueComparatorDec;
import ru.netology.domain.IssueComparatorInc;
import ru.netology.helpers.NotFoundException;
import ru.netology.repository.IssueRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings({"ArraysAsListWithZeroOrOneArgument", "rawtypes"})
class IssueManagerTest {

    IssueRepository repository = new IssueRepository();
    IssueManager manager = new IssueManager(repository);
    IssueComparatorDec comparatorDec = new IssueComparatorDec();
    IssueComparatorInc comparatorInc = new IssueComparatorInc();

    private final Issue firstIssue = new Issue(12, true,
            new HashSet<>(Arrays.asList("assignee1", "assignee2")), "Author1",
            new HashSet<>(Arrays.asList("UI", "registration")), "description");
    private final Issue secondIssue = new Issue(34, false,
            new HashSet<>(Arrays.asList("assignee1", "assignee2")), "Author1",
            new HashSet<>(Arrays.asList("UX", "Create order")), "description");
    private final Issue thirdIssue = new Issue(65, true,
            new HashSet<>(Arrays.asList("assignee2")), "Author2",
            new HashSet<>(Arrays.asList("UI")), "description");
    private final Issue forthIssue = new Issue(45, false,
            new HashSet<>(Arrays.asList("assignee3")), "Author1",
            new HashSet<>(Arrays.asList("authorisation")), "description");


    @BeforeEach
    void setUp() {
        repository.addAll(List.of(firstIssue, secondIssue, thirdIssue, forthIssue));
    }

    @Test
    @DisplayName("Вывод всех ишью в репозитории")
    void shouldGetAllIssues() {
        List<Issue> excepted = manager.getAll();
        List<Issue> actual = List.of(firstIssue, secondIssue, thirdIssue, forthIssue);
        assertEquals(excepted, actual, "Ишки в репозитории не совпадают с ожидаемым результатом!");
    }

    @Test
    @DisplayName("Добавление ишью в репозиторий")
    public void shouldAddSomeIssueInRepo() {
        Issue fifthIssue = new Issue(67, true,
                new HashSet<>(Arrays.asList("assignee1")), "Author2",
                new HashSet<>(Arrays.asList("UI")), "description");
        manager.add(fifthIssue);
        List<Issue> excepted = manager.getAll();
        List<Issue> actual = List.of(firstIssue, secondIssue, thirdIssue, forthIssue, fifthIssue);
        assertEquals(excepted, actual, "Содержание репозитория с добавленной ишкой не совпадает с ожидаемым результатом!");
    }

    @Test
    @DisplayName("Отображение открытых ишью в репозитории")
    void shouldShowOpenIssues() {
        List<Issue> expected = List.of(firstIssue, thirdIssue);
        List actual = manager.openedIssues();
        assertEquals(expected, actual, "Открытые ишью не совпадают с ожидаемым результатом");
    }

    @Test
    @DisplayName("Отображение ошибки о пустом списке открытых ишью в репозитории")
    void shouldShowEmptyOpenIssues() {
        manager.closeIssue(12);
        manager.closeIssue(65);
        assertThrows(NotFoundException.class, () -> manager.openedIssues(),
                "Ошибка показа пустого листа открытых ишью поломалась");
    }


    @Test
    @DisplayName("Отображение закрытых ишью в репозитории")
    void shouldShowClosedIssues() {
        List<Issue> expected = List.of(secondIssue, forthIssue);
        List actual = manager.closedIssues();
        assertEquals(expected, actual, "Закрытые ишью не совпадают с ожидаемым результатом");
    }

    @Test
    @DisplayName("Отображение ошибки о пустом списке закрытых ишью в репозитории")
    void shouldShowEmptyClosedIssues() {
        manager.openIssue(34);
        manager.openIssue(45);
        assertThrows(NotFoundException.class, () -> manager.closedIssues(),
                "Ошибка показа пустого листа закрытых ишью поломалась");
    }

    @Test
    @DisplayName("Фильтрация по автору")
    void shouldFilterByAuthor() {
        String author = "Author1";
        Predicate<String> equalAuthor = t -> t.equals(author);
        List<Issue> expected = List.of(firstIssue, secondIssue, forthIssue);
        List<Issue> actual = manager.filterByAuthor(equalAuthor);
        assertEquals(expected, actual, "Фильтрация по автору поломатая!");
    }

    @Test
    @DisplayName("Фильтрация по ярлыкам")
    void shouldFilterByLabel() {
        String label = "UI";
        Predicate<HashSet> equalLabel = t -> t.contains(label);
        List<Issue> expected = List.of(firstIssue, thirdIssue);
        List<Issue> actual = manager.filterByLabel(equalLabel);
        assertEquals(expected, actual, "Фильтрация по ярлыкам поломатая!");
    }

    @Test
    @DisplayName("Фильтрация по назначению")
    void shouldFilterByAssignee() {
        String assignee = "assignee1";
        Predicate<HashSet> equalAssignee = t -> t.contains(assignee);
        List<Issue> expected = List.of(firstIssue, secondIssue);
        List<Issue> actual = manager.filterByAssignee(equalAssignee);
        assertEquals(expected, actual, "Фильтрация по назначению поломатая!");
    }

    @Test
    @DisplayName("Сортировка по ID по убыванию")
    void shouldSortByIdDecrease() {
        List<Issue> expected = List.of(thirdIssue, forthIssue, secondIssue, firstIssue);
        List<Issue> actual = manager.sortByIdDec(comparatorDec);
        assertEquals(expected, actual, "Сортировка по убыванию не соответсвует ожидаемому результату");
    }

    @Test
    @DisplayName("Сортировка по ID по возрастанию")
    void shouldSortByIdIncrease() {
        List<Issue> expected = List.of(firstIssue, secondIssue, forthIssue, thirdIssue);
        List<Issue> actual = manager.sortByIdInc(comparatorInc);
        assertEquals(expected, actual, "Сортировка по возрастанию не соответсвует ожидаемому результату");
    }

    @Test
    @DisplayName("Открытие ишью по id")
    void shouldOpenIssue() {
        manager.openIssue(34);
        List<Issue> expected = List.of(firstIssue, secondIssue, thirdIssue);
        List actual = manager.openedIssues();
        assertEquals(expected, actual, "Открытые ишью не совпадают с ожидаемым результатом");
    }

    @Test
    @DisplayName("Ошибка при открытии ишью")
    void shouldOpenIssueByInvalidId() {
        assertThrows(NotFoundException.class, () -> manager.openIssue(228),
                "Ошибка открытия несуществующего ишью из репо поломалась");
    }

    @Test
    @DisplayName("Закрытие ишью по id")
    void shouldCloseIssue() {
        manager.closeIssue(12);
        List<Issue> expected = List.of(firstIssue, secondIssue, forthIssue);
        List actual = manager.closedIssues();
        assertEquals(expected, actual, "Закрытые ишью не совпадают с ожидаемым результатом");
    }

    @Test
    @DisplayName("Ошибка при закрытии ишью")
    void shouldCloseIssueByInvalidId() {
        assertThrows(NotFoundException.class, () -> manager.closeIssue(228),
                "Ошибка закрытия несуществующего ишью из репо поломалась");
    }
}