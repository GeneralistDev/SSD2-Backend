import models.AMLComponents.App;
import models.AMLComponents.Screen;
import org.junit.*;
import static org.fest.assertions.Assertions.*;

/**
 * Created by danielparker on 26/05/2014.
 */
public class AMLTests {

    public Screen screen;

    @Before
    public void setup() {
        this.screen = new Screen(1);
    }

    @Test
    public void testScreen() {
        String render = screen.toAMLString();
        System.out.println(render);
        assertThat(render).isEqualTo(
                "screen Screen1 \"Screen1\" {\n" +
                "   view MainLayout {\n" +
                "       label labelId \"Screen Screen1\"\n" +
                "   }\n" +
                "}\n"
        );
    }

    @Test
    public void testApp() {
        App app = new App(screen);
        String render = app.toAMLString();
        System.out.println(render);
        assertThat(render).isEqualTo(
            "app (Screen1) {\n" +
            "   android-sdk: \"/Users/danielparker/Applications/android-sdk\"\n" +
            "}\n"
        );
    }
}
