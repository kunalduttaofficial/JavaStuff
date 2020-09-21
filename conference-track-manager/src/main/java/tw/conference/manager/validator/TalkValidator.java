package tw.conference.manager.validator;

import tw.conference.manager.domain.input.Talk;

public class TalkValidator {

    public static boolean isValid(Talk talk) {
        if (talk == null)
            return false;

        if (talk.getDuration() == 0)
            return false;

        return true;
    }
}
