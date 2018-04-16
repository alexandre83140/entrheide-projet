package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import pojos.Utilisateur;
import services.AnnonceService;
import services.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/evenements")
public class ListEvenementServlet extends GenericServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("Annonce", AnnonceService.getInstance().listEvenement());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        String mailUtilisateur = (String) req.getSession().getAttribute("user");

        if (mailUtilisateur == null){
            templateEngine.process("notConnected", context, resp.getWriter());
        }else{
            Utilisateur utilisateurConnecte = UtilisateurService.getInstance().getUtilisateur(mailUtilisateur);
            if(utilisateurConnecte.getIdUtilisateur() != null){
                templateEngine.process("evenement", context, resp.getWriter());
            }else {
                templateEngine.process("notConnected", context, resp.getWriter());
            }

        }
    }
}
