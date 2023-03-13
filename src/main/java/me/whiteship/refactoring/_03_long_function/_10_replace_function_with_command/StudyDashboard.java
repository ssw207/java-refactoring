package me.whiteship.refactoring._03_long_function._10_replace_function_with_command;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudyDashboard {

    private final int totalNumberOfEvents;
    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    public StudyDashboard(int totalNumberOfEvents) {
        this.totalNumberOfEvents = totalNumberOfEvents;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        StudyDashboard studyDashboard = new StudyDashboard(15);
        studyDashboard.print();
    }

    private void print() throws IOException, InterruptedException {
        GHRepository repository = getGhRepository();

        List<Participant> participants = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(totalNumberOfEvents);

        for (int index = 1 ; index <= totalNumberOfEvents ; index++) {
            int eventId = index;
            executorService.execute(() -> runProcess(repository, participants, latch, eventId));
        }

        latch.await();
        executorService.shutdown();

        new StudyPrinter(totalNumberOfEvents).execute(participants);
    }

    private void runProcess(GHRepository repository, List<Participant> participants, CountDownLatch latch, int eventId) {
        try {
            List<GHIssueComment> comments = getGhIssueComments(repository, eventId);
            setHomeworkDon(participants, eventId, comments);
            latch.countDown();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void setHomeworkDon(List<Participant> participants, int eventId, List<GHIssueComment> comments) {
        for (GHIssueComment comment : comments) {
            Participant participant = findParticipant(comment.getUserName(), participants);
            participant.setHomeworkDone(eventId);
        }
    }

    private static List<GHIssueComment> getGhIssueComments(GHRepository repository, int eventId) throws IOException {
        GHIssue issue = repository.getIssue(eventId);
        List<GHIssueComment> comments = issue.getComments();
        return comments;
    }

    private static GHRepository getGhRepository() throws IOException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        return repository;
    }

    private Participant findParticipant(String username, List<Participant> participants) {
        Participant participant = null;
        if (participants.stream().noneMatch(p -> p.username().equals(username))) {
            participant = new Participant(username);
            participants.add(participant);
        } else {
            participant = participants.stream().filter(p -> p.username().equals(username)).findFirst().orElseThrow();
        }
        return participant;
    }







}
