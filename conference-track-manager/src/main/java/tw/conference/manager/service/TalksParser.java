package tw.conference.manager.service;

import tw.conference.manager.domain.input.Talk;
import tw.conference.manager.validator.TalkValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static tw.conference.manager.util.ConferenceUtil.getDuration;

public class TalksParser {

    public List<Talk> getPlannedTalks(String filePath) {
        List<Talk> plannedTalks = new ArrayList<Talk>();

        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

            plannedTalks = stream.map(t -> new Talk(t, getDuration(t))) // Extract title and duration
                    .filter(t -> TalkValidator.isValid(t)) // Filter out invalid entries
                    .sorted((t1, t2) -> t1.getDuration().compareTo(t2.getDuration())) // sort based on descending event duration
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Given path: '" + filePath + "' could not be found");
        }

        /* System.out.println("Loading planned conference talks...");
         plannedTalks.forEach(talk -> System.out.println(talk.toString()));*/

        return plannedTalks;
    }

}
