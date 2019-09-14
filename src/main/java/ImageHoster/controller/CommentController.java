package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@RequestParam("comment") String comment, @PathVariable("imageId") Integer imageId,
                             @PathVariable("imageTitle") String imageTitle, Model model, HttpSession session,
                             RedirectAttributes redirectAttributes) {
        Image image = imageService.getImage(imageId);
        User loggedInuser = (User) session.getAttribute("loggeduser");
        Comment userComment = new Comment();
        userComment.setImage(image);
        userComment.setUser(loggedInuser);
        userComment.setText(comment);
        userComment.setCreatedDate(new Date());
        commentService.createComment(userComment);

        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }

}
