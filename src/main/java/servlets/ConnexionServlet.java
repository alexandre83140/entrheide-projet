package servlets;

import daos.UtilisateurDao;
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
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/connexion")
public class ConnexionServlet extends GenericServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("connexion", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mailUtilisateur = req.getParameter("mailConnexion");
        String mdpUtilisateur = req.getParameter("pswConnexion");
        req.getSession().setAttribute("user", mailUtilisateur);
        Utilisateur utilisateurConnecte = UtilisateurService.getInstance().getUtilisateur(mailUtilisateur);
            if ( utilisateurConnecte.getIdUtilisateur() != null) {
                req.getSession().setAttribute("id", utilisateurConnecte.getIdUtilisateur());
                resp.sendRedirect("/accueil");
            }else{
              resp.sendRedirect("/connexion");
            }
    }
}
