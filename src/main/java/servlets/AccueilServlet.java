package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import pojos.Utilisateur;
import services.AnnonceService;
import services.CategorieService;
import services.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

@WebServlet("/accueil")
public class AccueilServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer nombreAnnonces = AnnonceService.getInstance().listAnnonce().size();
        Random r = new Random();
        int idAléatoire = 0 + r.nextInt(nombreAnnonces - 0);


        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("AnnonceDuJour", AnnonceService.getInstance().listAnnonceDuJour());
        context.setVariable("Categorie", CategorieService.getInstance().listCategorieWithoutEvent());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());

        String mailUtilisateur = (String) req.getSession().getAttribute("user");

        //TESTS DE SECURITE
        if (mailUtilisateur == null){
                templateEngine.process("notConnected", context, resp.getWriter());
        }else{
                Utilisateur utilisateurConnecte = UtilisateurService.getInstance().getUtilisateur(mailUtilisateur);
                if(utilisateurConnecte.getIdUtilisateur() != null){
                    templateEngine.process("accueil", context, resp.getWriter());
                }else {
                    templateEngine.process("notConnected", context, resp.getWriter());
                }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET PARAMETERS

        String motsClefsRecherche = req.getParameter("recherche");
        //SET ATTRIBUTE
        req.getSession().setAttribute("rechercheAnnonce", motsClefsRecherche);
        resp.sendRedirect("/recherche");
    }
}

