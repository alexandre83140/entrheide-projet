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

@WebServlet("/inscription")
public class InscriptionServlet  extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("WEB-INF/templates/");
        templateResolver.setSuffix(".html" + "");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        WebContext context = new WebContext(req, resp, req.getServletContext());

        templateEngine.process("inscription", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //GET PARAMETERS

        String nomUtilisateur = req.getParameter("nomUser");
        String prenomUtilisateur = req.getParameter("prenomUser");
        String telephoneUtilisateur = null;
        String mailUtilisateur = req.getParameter("emailUser");
        String mdpUtilisateur = req.getParameter("pswUser");
        String promoUtilisateur = req.getParameter("select-type");
        Boolean administrateur = false;

        //CREATE NEW USER

        Utilisateur utilisateur = new Utilisateur(null, nomUtilisateur, prenomUtilisateur, telephoneUtilisateur, mailUtilisateur, promoUtilisateur, administrateur, mdpUtilisateur);
        UtilisateurService.getInstance().addUtilisateur(utilisateur);
        req.getSession().setAttribute("mailUtilisateur", mailUtilisateur);
        req.getSession().setAttribute("mdpUtilisateur", mdpUtilisateur);
        resp.sendRedirect("/accueil");
    }
}
