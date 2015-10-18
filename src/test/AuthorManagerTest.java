package test;

import main.domain.Author;
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
    @Test
    public void checkAddingAuthor()
    {
        int size = authorManager.getAllAuthors().size();
        authorManager.addAuthor(new Author("Mateusz", "Zweigert"));
        assertTrue(authorManager.getAllAuthors().size() > size);
    }


}
