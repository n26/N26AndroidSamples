package de.n26.n26androidsamples.base.common.preconditions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class PreconditionsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkNotNull_doesNotThrowException_whenNonNull() {

        Preconditions.checkNotNull(new Object());
    }

    @Test
    public void checkNotNull_throwsNullPointerException_whenNull() {
        thrown.expect(NullPointerException.class);

        Preconditions.checkNotNull(null);
    }

    @Test
    public void checkNotNullWithMessage_doesNotThrowException_whenNonNull() {
        Preconditions.checkNotNull(new Object(), "Unused message");
    }

    @Test
    public void checkNotNullWithMessage_throwsNullPointerExceptionWithMessage_whenNull() {
        final String message = "message";
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(message);

        Preconditions.checkNotNull(null, message);
    }

    @Test
    public void get_returnsParameter_whenNonNull() {
        Object obj = new Object();

        assertThat(Preconditions.get(obj)).isEqualTo(obj);
    }

    @Test
    public void get_throwsNullPointerException_whenNull() {
        thrown.expect(NullPointerException.class);

        Preconditions.get(null);
    }

}