package tw.conference.manager.validator;

import org.testng.Assert;
import org.testng.annotations.Test;
import tw.conference.manager.domain.input.Talk;

public class TalkValidatorTest {

    @Test
    public void shouldReturnFalseForNullTalk() {
        Talk talk = null;

        boolean isValid = TalkValidator.isValid(talk);

        Assert.assertFalse(isValid);
    }

    @Test
    public void shouldReturnFalseForZeroDurationTalk() {
        Talk talk = new Talk("Test Talk", 0);

        boolean isValid = TalkValidator.isValid(talk);

        Assert.assertFalse(isValid);
    }

}
