package funcional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.AbstractTest;
import com.fasterxml.jackson.databind.JsonNode;
import models.Meta;
import models.dao.GenericDAO;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;
import play.twirl.api.Content;
import views.html.index;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends AbstractTest{
    private GenericDAO dao = new GenericDAO();

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    @Test
    public void renderTemplate() {
        List<Meta> metas = dao.findAllByClassName(Meta.class.getName());
        int[] metasPorSemana = controllers.Application.getMetasPorSemana();
        int[] metasAlcancadas = controllers.Application.getMetasAlcancadas();
        int[] metasNaoAlcancadas = controllers.Application.getMetasNaoAlcancadas();
        Content html = index.render(metas, metasPorSemana, metasAlcancadas, metasNaoAlcancadas);
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Minhas Metas");
    }



}
