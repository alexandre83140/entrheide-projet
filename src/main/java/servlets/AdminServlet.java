package servlets;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import pojos.Annonce;
import pojos.Categorie;
import pojos.Utilisateur;
import services.AnnonceService;
import services.CategorieService;
import services.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet("/admin")
public class AdminServlet extends GenericServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());

        context.setVariable("Categorie", CategorieService.getInstance().listCategorie());
        context.setVariable("Annonce", AnnonceService.getInstance().listEvenement());
        context.setVariable("Signal" , AnnonceService.getInstance().listAnnoncesSignalees());


        String mailUtilisateur = (String) req.getSession().getAttribute("user");

        if (mailUtilisateur == null){
            templateEngine.process("notConnected", context, resp.getWriter());
        }else{
            Utilisateur utilisateurConnecte = UtilisateurService.getInstance().getUtilisateur(mailUtilisateur);
            if(utilisateurConnecte.getIdUtilisateur() != null){
                if(utilisateurConnecte.getAdministrateur() == 1){
                    templateEngine.process("admin", context, resp.getWriter());
                }else{
                    templateEngine.process("notAdmin", context, resp.getWriter());
                }
            }else {
                templateEngine.process("notConnected", context, resp.getWriter());
            }

        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //GET PARAMETERS
        String titreEvent = req.getParameter("nomEvent");

        LocalDate dateEvent ;
        String dateEventAsString = req.getParameter("dateEvent");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //Date input = new Date();
        //LocalDate dateAnnonce = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Boolean evenement = true;
        Boolean signalee = false;
        Categorie categorieEvent = CategorieService.getInstance().getCategorie("Evenement");
        Utilisateur utilisateurAnnonce = UtilisateurService.getInstance().getUtilisateur(req.getSession().getAttribute("user").toString());

        String titreEventDeleted = req.getParameter("titreEvent");

        if(titreEvent != null || dateEventAsString != null){
            //CREATE NEW EVENT
            Annonce nouvelEvent = new Annonce(null,titreEvent, "", "",LocalDate.parse(dateEventAsString, dateFormat), evenement, signalee, categorieEvent, utilisateurAnnonce);
            AnnonceService.getInstance().addAnnonce(nouvelEvent);
        }

        if(titreEventDeleted != null){
            //DELETE SELECTED EVENT
            AnnonceService.getInstance().deleteAnnonce(titreEventDeleted);
        }
        resp.sendRedirect("/admin");
    }
}
