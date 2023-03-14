package me.whiteship.refactoring._03_long_function._11_decompose_conditional;

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

    public StudyDashboard(int totalNumberOfEvents) {
        this.totalNumberOfEvents = totalNumberOfEvents;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        StudyDashboard studyDashboard = new StudyDashboard(15);
        studyDashboard.print();
    }

    private void print() throws IOException, InterruptedException {
        GitHub gitHub = GitHub.connect();
        GHRepository repository = gitHub.getRepository("whiteship/live-study");
        List<Participant> participants = new CopyOnWriteArrayList<>();

        ExecutorService service = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(totalNumberOfEvents);

        for (int index = 1 ; index <= totalNumberOfEvents ; index++) {
            int eventId = index;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        GHIssue issue = repository.getIssue(eventId);
                        List<GHIssueComment> comments = issue.getComments();

                        for (GHIssueComment comment : comments) {
                            Participant participant = findParticipant(comment.getUserName(), participants);
                            participant.setHomeworkDone(eventId);
                        }

                        latch.countDown();
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
        }

        latch.await();
        service.shutdown();

        new StudyPrinter(this.totalNumberOfEvents, participants).execute();
    }

    private Participant findParticipant(String username, List<Participant> participants) {
        return isNewParticipant(username, participants) ? // 삼항연산자를 쓰는게 좋을까?
            createParticipant(username, participants) :
            findExistsParticipant(username, participants);
    }

    // 참석자를 생성하고 참석자 리스트에 넣는 동작을한다.
    // 생성과 리스트 추가라는 행위를 하고 있지만 참석자가 추가되면 참석자 명단에 들어가는게 자연스럽기 때문에 참석자 생성으로만 메서드명을 짓는다.
    private Participant createParticipant(String username, List<Participant> participants) {
        Participant participant = new Participant(username);
        participants.add(participant);
        return participant;
    }

    // 이름과 일치하는 첫번째 참석자를 찾고 없으면 예외를 던진다.
    // findFirst는 처음 대상을 찾으면 멈축기 위한 목적이므로 첫번째에는 큰 의미가 없다. 없으면 예외를 던지므로 반드시 있어야한다.
    // 따라서 존재하는 참석자 찾기가 적절하다.
    private Participant findExistsParticipant(String username, List<Participant> participants) {
        return participants.stream().filter(p -> p.username().equals(username)).findFirst().orElseThrow();
    }

    //참석자의 이름을판한다는 행위를 하고 있더라도 이 행위를 하는 이유가 신규 참석자인지 판단하기 위함이기 때문에 메서드의 이름을 심규참석자 판단으로 짓는다.
    private boolean isNewParticipant(String username, List<Participant> participants) {
        return participants.stream().noneMatch(p -> p.username().equals(username));
    }

}
