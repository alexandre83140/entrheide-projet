package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import pojos.Utilisateur;
import services.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("WEB-INF/templates/");
        templateResolver.setSuffix(".html" + "");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        WebContext context = new WebContext(req, resp, req.getServletContext());

        //String mailConnecte = (String) req.getSession().getAttribute("mailUtilisateur");
        //String mdpConnecte = (String) req.getSession().getAttribute("mdpUtilisateur");
        //Utilisateur utilisateurConnecte = UtilisateurService.getInstance().getUtilisateur(mailConnecte, mdpConnecte);
        //if ( utilisateurConnecte.getIdUtilisateur() == null){
        //  templateEngine.process("connexion", context, resp.getWriter());
        //}
        //else{
        templateEngine.process("accueil", context, resp.getWriter());
        //}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
