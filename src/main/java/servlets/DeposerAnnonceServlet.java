package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@WebServlet("/deposerAnnonce")
public class DeposerAnnonceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("WEB-INF/templates/global/");
        templateResolver.setSuffix(".html" + "");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        WebContext context = new WebContext(req, resp, req.getServletContext());

        templateEngine.process("deposerAnnonce", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //GET PARAMETERS

        String titreAnnonce = req.getParameter("nomAdd");
        String descriptionAnnonce = req.getParameter("descriptionAdd");
        String motsClefsAnnonce = req.getParameter("keyAdd");
        Date dateAnnonce = Calendar.getInstance().getTime();
        System.out.println(dateAnnonce.toString());

       //Rajouter la date du jour du post
        String categorieAnnonce = req.getParameter("categorieAdd");

        // Rajouter photo
    }
}
