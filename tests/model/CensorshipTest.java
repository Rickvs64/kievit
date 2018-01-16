package model;

import classes.domains.Censorship;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CensorshipTest {
    private Censorship censorship;
    @Before
    public void setup() throws IOException {
        censorship = new Censorship();
    }

    @Test
    public void getBadWords()
    {
        Assert.assertEquals(false,censorship.getBannedTerms().isEmpty());
    }
}
