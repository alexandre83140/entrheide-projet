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
public class InscriptionServlet  extends GenericServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        templateEngine.process("inscription", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //GET PARAMETERS

        String nomUtilisateur = req.getParameter("nomUser");
        String prenomUtilisateur = req.getParameter("prenomUser");
        String telephoneUtilisateur = req.getParameter("telephoneUser");
        String mailUtilisateur = req.getParameter("emailUser");
        String mdpUtilisateur = req.getParameter("pswUser");
        String promoUtilisateur = req.getParameter("select-type");
        Integer administrateur = 0;

        //CREATE NEW USER

        Utilisateur utilisateurInscrit = new Utilisateur(null, nomUtilisateur, prenomUtilisateur, telephoneUtilisateur, mailUtilisateur, promoUtilisateur, administrateur, mdpUtilisateur);
        UtilisateurService.getInstance().addUtilisateur(utilisateurInscrit);
        resp.sendRedirect("/accueil");
        //}


    }
}
