package tw.conference.manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tw.conference.manager.exception.InvalidTalkException;
import tw.conference.manager.service.ConferenceOrganiser;

@SpringBootApplication
public class ConferenceTrackManagerApp implements CommandLineRunner {

    private ConferenceOrganiser conferenceOrganiser = new ConferenceOrganiser();

    public static void main(String[] args) {
        SpringApplication.run(ConferenceTrackManagerApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Path to the input text file as program argument
        String fileName = args[0];

        if (fileName == null)
            throw new InvalidTalkException("Given file path cannot be empty");

        conferenceOrganiser.scheduleTracks(fileName);

    }

}
