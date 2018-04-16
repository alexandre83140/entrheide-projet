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

@WebServlet("/listAnnonceByCategory")
public class ListAnnonceByCategoryServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        String idCategorie = req.getParameter("id");

        context.setVariable("Annonce", AnnonceService.getInstance().listAnnonceByCategorie(Integer.parseInt(idCategorie)));
        context.setVariable("Categorie", CategorieService.getInstance().listCategorie());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        String mailUtilisateur = (String) req.getSession().getAttribute("user");

        if (mailUtilisateur == null){
            templateEngine.process("notConnected", context, resp.getWriter());
        }else{
            Utilisateur utilisateurConnecte = UtilisateurService.getInstance().getUtilisateur(mailUtilisateur);
            if(utilisateurConnecte.getIdUtilisateur() != null){
                templateEngine.process("lesAnnonces", context, resp.getWriter());
            }else {
                templateEngine.process("notConnected", context, resp.getWriter());
            }

        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
