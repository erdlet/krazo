package org.eclipse.krazo.test;

import static org.junit.Assert.assertEquals;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.net.URL;
import java.nio.file.Paths;
import org.eclipse.krazo.test.util.WebArchiveBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BindingIT {

    private static final String WEB_INF_SRC = "src/main/resources/binding/";

    @ArquillianResource
    private URL baseURL;

    private WebClient webClient;

    @Before
    public void setUp() throws Exception {
        webClient = new WebClient();
    }

    @After
    public void tearDown() throws Exception {
        webClient.close();
    }

    @Deployment(testable = false, name = "bindings")
    public static Archive createDeployment() {
        return new WebArchiveBuilder()
            .addPackage("org.eclipse.krazo.test.binding")
            .addView(Paths.get(WEB_INF_SRC).resolve("views/form.jsp").toFile(), "form.jsp")
            .addView(Paths.get(WEB_INF_SRC).resolve("views/ok.jsp").toFile(), "ok.jsp")
            .addBeansXml()
            .build();
    }

    @Test
    public void shouldBindValuesToClassAttributes() throws Exception {
        final HtmlPage formPage = getFormPage();

        final HtmlForm form = setValuesInForm(formPage, "Hello", "World");

        final HtmlPage resultPage = submit(form);

        assertEquals("size must be between 0 and 2",
            resultPage.getElementById("error").getTextContent());
    }

    private HtmlPage getFormPage() throws java.io.IOException {
        return webClient.getPage(baseURL + "resources/binding");
    }

    private HtmlForm setValuesInForm(final HtmlPage page, final String valA, final String valB) {
        final HtmlForm form = (HtmlForm) page.getElementById("form");
        final HtmlTextInput valAInput = (HtmlTextInput) page
            .getElementById("valAInput");
        valAInput.setValueAttribute(valA);

        final HtmlTextInput valBInput = (HtmlTextInput) page
            .getElementById("valBInput");
        valBInput.setValueAttribute(valB);
        return form;
    }

    private HtmlPage submit(HtmlForm form) throws java.io.IOException {
        return form.getInputByName("submit").click();
    }
}
