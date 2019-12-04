//package by.epam.receptionenrollee.tag;
//
//import javax.servlet.jsp.JspException;
//import javax.servlet.jsp.JspTagException;
//import javax.servlet.jsp.JspWriter;
//import javax.servlet.jsp.tagext.TagSupport;
//import java.io.IOException;
//import java.util.Locale;
//
//public class EnrolleeScoreTag extends TagSupport {
//    @Override
//    public int doStartTag() throws JspException {
//        try  {
//            JspWriter out = pageContext.getOut();
//            out.write("<div class=\"div-score\"><p class=\"text-score\">");
//            out.write();
//            out.write("</p>");
//            out.write("<p class=\"score\">");
//            out.write();
//            out.write("</p>");
//            out.write("</div>");
//        } catch (IOException e) {
//            throw new JspTagException(e.getMessage());
//        }
//        return EVAL_BODY_INCLUDE;
//    }
//
//    @Override
//    public int doEndTag() throws JspException {
//        return super.doEndTag();
//    }
//
//    @Override
//    public int doAfterBody() throws JspException {
//        return super.doAfterBody();
//    }
//}
