package test;

import main.service.AuthorManager;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by MATEUSZ on 2015-10-18.
 */
public class AuthorManagerTest
{
    AuthorManager authorManager = new AuthorManager();

    @Test
    public void checkConnection()
    {
        assertNotNull(authorManager.getConnection());
    }
}
