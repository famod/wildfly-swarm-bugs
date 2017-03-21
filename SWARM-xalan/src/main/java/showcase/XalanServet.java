package showcase;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@WebServlet(urlPatterns = "/test")
public class XalanServet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try (final InputStream xslStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("some.xsl")) {
            final Templates template = TransformerFactory.newInstance().newTemplates(new StreamSource(xslStream));
            final Transformer transformer = template.newTransformer();
            resp.getWriter().write("Transformer: " + transformer);
            transformer.transform(new StreamSource(new StringReader("<?xml version=\"1.0\"?>\n<a></a>")), new StreamResult(resp.getWriter()));
        } catch (TransformerFactoryConfigurationError | TransformerException e) {
            e.printStackTrace(resp.getWriter());
        }
    }
}